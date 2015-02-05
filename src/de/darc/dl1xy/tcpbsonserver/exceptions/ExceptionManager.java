package de.darc.dl1xy.tcpbsonserver.exceptions;

import de.darc.dl1xy.tcpbsonserver.commands.client.ClientCmdMessageException;
import de.darc.dl1xy.tcpbsonserver.commands.client.IClientCmd;

public class ExceptionManager 
{
	int userId = -1;
	public ExceptionManager()
	{
	
	}
	
	public IClientCmd getErrorCmd(Exception e)
	{
		long millis = System.currentTimeMillis();
		
		if (e instanceof MessageException)
		{
			String msg = "uid: "+userId+" time:"+millis+" msg:"+((MessageException)e).getMessage();
			return new ClientCmdMessageException(msg);
		}
		return null;
	}

	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
