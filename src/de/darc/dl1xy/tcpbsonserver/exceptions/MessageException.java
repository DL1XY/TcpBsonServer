package de.darc.dl1xy.tcpbsonserver.exceptions;

public class MessageException  extends Exception {
	

	private static final long serialVersionUID = -453144494599437634L;

	public MessageException(Object o, String s)
	{
		super(o.toString() + "#" +s);
	}

}
