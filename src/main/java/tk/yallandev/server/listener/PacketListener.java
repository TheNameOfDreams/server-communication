package tk.yallandev.server.listener;

import java.net.Socket;
import java.util.UUID;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import tk.yallandev.common.packetmanager.ConnectionListen;
import tk.yallandev.common.packetmanager.PacketListener.PacketEvent;
import tk.yallandev.server.Server;
import tk.yallandev.server.ServerGeneral;

public class PacketListener implements PacketEvent {

    @Override
    public void onPacketRecieve(JsonElement jsonElement, Socket socket) {
        if (jsonElement instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject) jsonElement;
            
            if (jsonObject.has("type")) {
                switch (jsonObject.get("type").getAsString().toLowerCase()) {
                case "handshake": {
                    
                    UUID uniqueId = UUID.fromString(jsonObject.get("uniqueId").getAsString());
                    String playerName = jsonObject.get("playerName").getAsString();
                    
                    if (socket == null) {
                        System.out.println("null");
                    }
                    
                    ConnectionListen connection = Server.getInstance().getServerGeneral().getConnectionListenByPort(socket.getPort());
                    
                    if (connection == null) {
                        System.out.println("Couldn't register " + socket.getInetAddress().getHostName() + ":" + socket.getPort() + "!");
                        return;
                    }
                    
                    connection.setUniqueId(uniqueId);
                    connection.setPlayerName(playerName);
                    
                    Server.getInstance().getServerGeneral().register(connection);
                    Server.getInstance().getServerGeneral().debug("Connection from " + connection.getAddress() + " (" + connection.getUniqueId() + ") has been registred!");
                    break;
                }
                }
            }
        }
    }

}
