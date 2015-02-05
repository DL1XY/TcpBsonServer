package de.darc.dl1xy.tcpbsonserver.commands.client;



public abstract class BaseClientCmd implements IClientCmd
{
	// cmd id
	public int id;
	public String meta;
	public int serverCmdId;
	
	public BaseClientCmd()
	{
		
	}

	//public abstract void serialize (ObjectMapper mapper, ByteArrayOutputStream stream);
}
