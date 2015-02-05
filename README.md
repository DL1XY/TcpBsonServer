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
* Create client commands (= commands which are handled by the client) by inherit from BaseClientCmd (see ClientCmdMessageException)
* Create server commands (= commands which are handled by the server) by inherit from BaseServerCmd (see ServerCmdLogout)
* Every client connected to server is associated with a Gameplay and CommandManager object. The CommandManager is directly connected to the netty layer and handles incoming/outgoing commands, while Gameplay represents the coonnected user
* run with <b>java TcpBsonServer configprops</b>
