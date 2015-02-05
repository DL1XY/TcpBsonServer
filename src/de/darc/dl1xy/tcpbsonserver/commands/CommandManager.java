package de.darc.dl1xy.tcpbsonserver.commands;

import de.darc.dl1xy.tcpbsonserver.commands.client.BaseClientCmd;
import de.darc.dl1xy.tcpbsonserver.commands.client.IClientCmd;
import de.darc.dl1xy.tcpbsonserver.exceptions.ExceptionManager;
import de.darc.dl1xy.tcpbsonserver.netty.TcpBsonServerHandler;

/**
 * CommandManager handles the outgoing commands and delegates them to the netty handler 
 * It also receives exceptions from game states to create proper commands with error descriptions
 * @author Arne
 *
 */
public class CommandManager 
{

	private int _clientCmdCounter = 0;
	private int _serverCmdCounter = 0;

	private ExceptionManager _excMgr;
	private int currentServerCmdId;
	private TcpBsonServerHandler _handler;
	
	public CommandManager(final TcpBsonServerHandler handler)
	{
		this._handler = handler;	
		this._excMgr = new ExceptionManager();
	}
	
	/**
	 * add a new client command
	 * @param cmd command to add
	 * @return true, if command was added successfully
	 */
	public void addClientCommand(IClientCmd cmd)
	{
		
		((BaseClientCmd)cmd).id = ++_clientCmdCounter;
		((BaseClientCmd)cmd).meta = ((BaseClientCmd)cmd).getClass().getSimpleName(); 
		((BaseClientCmd)cmd).serverCmdId = currentServerCmdId;
		//LogUtil.getLogger().debug("CommandManager addClientCommand cmd:"+cmd.toString()+" counter:"+_clientCmdCounter+" meta:"+((BaseClientCmd)cmd).meta+" serverCmdId:"+((BaseClientCmd)cmd).serverCmdId);
		_handler.write(cmd);
	
	}
	
	/**
	 * Receives exceptions and calls ExceptionManager
	 * @param e
	 */
	public void handleException (Exception e)
	{
		IClientCmd cmd = _excMgr.getErrorCmd(e);
		this.addClientCommand(cmd);
	}
	
	public int getClientCmdCounter() 
	{
		return _clientCmdCounter;
	}

	public void setClientCmdCounter(final int clientCmdCounter) 
	{
		this._clientCmdCounter = clientCmdCounter;
	}

	public int getServerCmdCounter() 
	{
		return _serverCmdCounter;
	}

	public void setServerCmdCounter(final int serverCmdCounter)
	{
		this._serverCmdCounter = serverCmdCounter;
	}
	
	public void logout()
	{
		_handler.logout(null);
	}
}
