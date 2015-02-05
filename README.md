# Required


* bson4jackson
* jackson-core
* jackson-annotations
* jackson-databind
* netty 4.x
* java 1.7+
* 
# HowTo

* Download and install all required libs, take care that they are in your PATH
* Define the port you want to use in config.props
* Create client commands (= commands which are handled by the client) by inherit from BaseClientCmd (see ClientCmdMessageException) and create similar commands in the client's code
* Create server commands (= commands which are handled by the server) by inherit from BaseServerCmd (see ServerCmdLogout) and create similar commands in the client's code
* Every client connected to server is associated with a Client and CommandManager object. The CommandManager is directly connected to the netty layer and handles incoming/outgoing commands, while Client represents the connected client
* run with <b>java TcpBsonServer config.props</b>
