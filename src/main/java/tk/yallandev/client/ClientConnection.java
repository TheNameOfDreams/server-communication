package tk.yallandev.client;

import java.io.IOException;
import java.net.Socket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import tk.yallandev.CommonConst;
import tk.yallandev.CommonGeneral;
import tk.yallandev.client.listener.PacketListener;
import tk.yallandev.common.connection.Connection;
import tk.yallandev.common.packetmanager.ConnectionListen;
import tk.yallandev.common.utils.string.StringUtils;
import tk.yallandev.common.utils.string.StringUtils.FormatType;

public class ClientConnection implements Connection {

    private String hostName;
    private int port;
    
    private Socket socket;
    private ConnectionListen connectionListen;

    public ClientConnection(String hostName) throws Exception {
        if (!Client.isInstanced())
            throw new Exception("Client not instanced!");

        this.hostName = hostName;
        this.port = CommonConst.PORT;
        
        socket = new Socket(hostName, CommonConst.PORT);
        connectionListen = new ConnectionListen(socket);
        
        CommonGeneral.getInstance().getPacketListener().register(new PacketListener());
        
        debug("Connected to " + hostName + ":" + port + "!");
        handshake();
    }
    
    public void sendPacket(JsonElement jsonElement) {
        if (jsonElement == null)
            return;
        
        if (jsonElement instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject) jsonElement;
            
            if (jsonObject.has("type"))
                debug("Sending " + StringUtils.format(jsonObject.get("type").getAsString(), FormatType.UPPER_NORMAL_LOWER) + " packet to " + getAddress());
        }
        
        connectionListen.sendPacket(jsonElement);
    }
    
    private void handshake() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("type", "handshake");
        jsonObject.addProperty("uniqueId", Client.getInstance().getUniqueId().toString());
        jsonObject.addProperty("playerName", Client.getInstance().getPlayerName());
        
        sendPacket(jsonObject);
    }

    public void debug(String string) {
        System.out.println("[CLIENT - " + Client.getInstance().getPlayerName() + "] " + string);
    }
    
    @Override
    public String getAddress() {
        return hostName + ":" + port;
    }

    @Override
    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

}
