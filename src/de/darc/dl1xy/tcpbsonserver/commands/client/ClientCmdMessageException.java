package de.darc.dl1xy.tcpbsonserver.commands.client;


public class ClientCmdMessageException extends BaseClientCmd{

	public String message;
	
	public ClientCmdMessageException(String message) {
		this.message = message;
	}

}
