package de.darc.dl1xy.tcpbsonserver.commands.server;

import de.darc.dl1xy.tcpbsonserver.commands.CommandManager;
import de.darc.dl1xy.tcpbsonserver.gameplay.Gameplay;


public abstract class BaseServerCmd implements IServerCmd {

	public int id = 0;
	public String meta;
	protected boolean isExecutable = false;
	protected  CommandManager cmdMgr = null;
	
	
	public void execute(final Gameplay gameplay, final CommandManager cmdMgr)
	{			
		this.cmdMgr = cmdMgr;
				
		if (this.id >= this.cmdMgr.getServerCmdCounter())
		{
			this.isExecutable = true;
			this.cmdMgr.setServerCmdCounter(this.id);
		}		
	}	

	public int getServerCmdId()
	{
		return id;
	}
}
