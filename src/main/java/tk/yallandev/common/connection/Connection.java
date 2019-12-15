package tk.yallandev.common.connection;

import java.net.Socket;

public interface Connection {
    
    String getAddress();
    
    void disconnect();
    
    Socket getSocket();

}
