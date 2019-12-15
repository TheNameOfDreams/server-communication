package tk.yallandev.server;

import lombok.Getter;
import lombok.Setter;
import tk.yallandev.CommonGeneral;
import tk.yallandev.server.listener.PacketListener;

@Getter
public class Server {
    
    @Getter
    private static Server instance;
    
    @Setter
    private ServerGeneral serverGeneral;
    
    public Server(String hostName) {
        instance = this;
        
        try {
            serverGeneral = new ServerGeneral(hostName);
            serverGeneral.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        CommonGeneral.getInstance().getPacketListener().register(new PacketListener());
    }
    
}
