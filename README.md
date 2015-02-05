# Required


* bson4jackson
* jackson-core
* jackson-annotations
* jackson-databind
* netty 4.x
* java 1.7+
* 
# HowTo

* Download and install all required libs, take care that they are in your <code>CLASSPATH</code>
* Define the port you want to use in <code>config.props</code>
* Create client commands (= commands which are handled by the client) by inherit from <code>BaseClientCmd</code> (see <code>ClientCmdMessageException)</code> and create similar commands in the client's code
* Create server commands (= commands which are handled by the server) by inherit from <code>BaseServerCmd</code> (see <code>ServerCmdLogout</code>) and create similar commands in the client's code
* Every client connected to server is associated with a <code>Client</code> and <code>CommandManager</code> object. The <code>CommandManager</code> is directly connected to the Netty layer and handles incoming/outgoing commands, while <code>Client</code> represents the connected client
* run with <code>java TcpBsonServer config.props</code>
