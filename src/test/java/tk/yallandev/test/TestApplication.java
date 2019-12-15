package tk.yallandev.test;

import java.util.UUID;

import tk.yallandev.CommonGeneral;
import tk.yallandev.client.Client;
import tk.yallandev.client.ClientConnection;
import tk.yallandev.common.entity.type.Player;
import tk.yallandev.server.Server;
import tk.yallandev.server.ServerGeneral;

public class TestApplication {

    public static void main(String[] args) {
        CommonGeneral common = new CommonGeneral();
        String hostName = "localhost";
        
        Server server = new Server(hostName);
        
        Client client = new Client(UUID.randomUUID(), "yandv");
        
        try {
            client.setClientConnection(new ClientConnection(hostName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
