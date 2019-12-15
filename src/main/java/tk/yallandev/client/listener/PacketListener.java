package tk.yallandev.client.listener;

import java.net.Socket;
import java.util.UUID;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import tk.yallandev.client.Client;
import tk.yallandev.common.packetmanager.ConnectionListen;
import tk.yallandev.common.packetmanager.PacketListener.PacketEvent;
import tk.yallandev.server.Server;

public class PacketListener implements PacketEvent {

    @Override
    public void onPacketRecieve(JsonElement jsonElement, Socket socket) {
        if (jsonElement instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject) jsonElement;
            
            if (jsonObject.has("type")) {
                switch (jsonObject.get("type").getAsString().toLowerCase()) {
                case "timeout": {
                    
                    Client.getInstance().getClientConnection().debug("Disconnected from " + socket.getInetAddress().getHostName() + "");
                    break;
                }
                }
            }
        }
    }
    
}
