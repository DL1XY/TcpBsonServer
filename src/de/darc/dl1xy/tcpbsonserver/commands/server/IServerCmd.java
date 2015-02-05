package de.darc.dl1xy.tcpbsonserver.commands.server;

import de.darc.dl1xy.tcpbsonserver.commands.CommandManager;
import de.darc.dl1xy.tcpbsonserver.gameplay.Gameplay;

/**
 * Interface for all commands sent to the server
 * @author Arne
 *
 */
public interface IServerCmd 
{
	
	public void execute( Gameplay gameplay, CommandManager cmdMgr);
	public int getServerCmdId();
}
