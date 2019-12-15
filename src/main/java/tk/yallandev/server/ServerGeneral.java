package tk.yallandev.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import tk.yallandev.CommonConst;
import tk.yallandev.CommonGeneral;
import tk.yallandev.common.connection.Connection;
import tk.yallandev.common.packetmanager.ConnectionListen;
import tk.yallandev.server.listener.PacketListener;

public class ServerGeneral extends Thread implements Connection {
    
    private String hostName;
    private int port;
    
    private ServerSocket serverSocket;
    
    private Map<UUID, ConnectionListen> connectionMap;
    private Map<Socket, ConnectionListen> socketToRegister;
    private List<JsonElement> packetQueue;
    
    private boolean running;

    public ServerGeneral(String hostName) throws Exception {
        this.hostName = hostName;
        this.port = CommonConst.PORT;
        
        this.serverSocket = new ServerSocket(port);
        this.connectionMap = new HashMap<>();
        this.socketToRegister = new HashMap<>();
        this.packetQueue = new ArrayList<>();
        
        debug("Connection to " + hostName + ":" + port + " established!");
    }

    @Override
    public void run() {
        running = true;
        
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                
                String hostName = socket.getInetAddress().getHostName();
                int port = socket.getPort();
                
                initRegister(socket, new ConnectionListen(socket));
                debug("Connection from " + socket.getInetAddress().getHostName() + ":" + socket.getPort() + " accepted!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Use to send a packet to client
     * 
     * @param jsonElement
     */

    public void initRegister(Socket socket, ConnectionListen connectionListen) {
        socketToRegister.put(socket, connectionListen);
        
        new Timer().schedule(new TimerTask() {
            
            @Override
            public void run() {
                if (socketToRegister.containsKey(socket)) {
                    JsonObject jsonObject = new JsonObject();
                    
                    jsonObject.addProperty("type", "timeout");
                    
                    connectionListen.sendPacket(jsonObject);
                    
                    new Timer().schedule(new TimerTask() {
                        
                        @Override
                        public void run() {
                            connectionListen.disconnect();
                            debug("Connection " + connectionListen.getAddress() + " dropped!");
                        }
                    }, 5000L);
                    
                    socketToRegister.remove(socket);
                    debug("Connection " + connectionListen.getAddress() + " timeout!");
                }
            }
        }, CommonConst.TIMEOUT_TIME);
    }
    
    /**
     * Use to send a packet to client
     * 
     * @param jsonElement
     */

    public void sendPacket(JsonElement jsonElement, UUID uniqueId) {
        if (jsonElement == null)
            return;

        synchronized (packetQueue) {
            packetQueue.add(jsonElement);
        }
    }
    
    public ConnectionListen getConnectionListenByPort(int port) {
        for (ConnectionListen connectionListen : socketToRegister.values()) {
            if (connectionListen.getSocket().getPort() == port)
                return connectionListen;
        }
        
        return null;
    }
    
    public Socket getSocketByPort(int port) {
        for (Socket socket : socketToRegister.keySet()) {
            if (socket.getPort() == port)
                return socket;
        }
        
        return null;
    }
    
    public void register(ConnectionListen connectionListen) {
        socketToRegister.remove(connectionListen.getSocket());
        connectionMap.put(connectionListen.getUniqueId(), connectionListen);
    }
    
    public void unregister(UUID uniqueId) {
        connectionMap.remove(uniqueId);
    }

    public void debug(String string) {
        System.out.println("[SERVER - " + hostName + "] " + string);
    }
    
    @Override
    public String getAddress() {
        return hostName + ":" + port;
    }

    @Override
    public void disconnect() {
        
    }

    @Override
    public Socket getSocket() {
        return null;
    }

}
