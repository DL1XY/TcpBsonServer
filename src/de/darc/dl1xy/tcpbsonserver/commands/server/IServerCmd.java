package de.darc.dl1xy.tcpbsonserver.commands.server;

import de.darc.dl1xy.tcpbsonserver.client.Client;
import de.darc.dl1xy.tcpbsonserver.commands.CommandManager;

/**
 * Interface for all commands sent to the server
 * @author Arne
 *
 */
public interface IServerCmd 
{
	
	public void execute( Client gameplay, CommandManager cmdMgr);
	public int getServerCmdId();
}
