package tk.yallandev.common.packetmanager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import com.google.gson.JsonElement;

import lombok.Getter;
import lombok.Setter;
import tk.yallandev.common.connection.Connection;
import tk.yallandev.common.packetmanager.handler.InputHandler;
import tk.yallandev.common.packetmanager.handler.OutputHandler;

@Getter
public class ConnectionListen implements Connection {
    
    private Socket socket;
    
    private InputHandler inputHandler;
    private OutputHandler outputHandler;
    
    @Setter
    private UUID uniqueId;
    @Setter
    private String playerName;
    
    public ConnectionListen(Socket socket) throws IOException {
        this.socket = socket;
        inputHandler = new InputHandler(this, new DataInputStream(socket.getInputStream()));
        outputHandler = new OutputHandler(this, new DataOutputStream(socket.getOutputStream()));
        inputHandler.start();
        outputHandler.start();
    }
    
    public void sendPacket(JsonElement jsonElement) {
        outputHandler.sendPacket(jsonElement);
    }
    
    public void disconnect() {
        if (!socket.isClosed()) {
            try {
                inputHandler.close();
                outputHandler.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        inputHandler.stopThread();
        outputHandler.stopThread();
    }
    
    public String getAddress() {
        return socket.getInetAddress().getHostName() + ":" + socket.getPort();
    }
    
    public boolean isRegistred() {
        return uniqueId != null && playerName != null;
    }
    
}
