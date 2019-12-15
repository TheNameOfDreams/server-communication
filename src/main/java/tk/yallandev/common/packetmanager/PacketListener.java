package tk.yallandev.common.packetmanager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

public class PacketListener {
    
    private List<PacketEvent> packetMap;
    
    public PacketListener() {
        packetMap = new ArrayList<>();
    }
    
    public void register(PacketEvent packetEvent) {
        this.packetMap.add(packetEvent);
    }
    
    public void unregister(PacketEvent packetEvent) {
        this.packetMap.remove(packetEvent);
    }
    
    public void call(JsonElement jsonElement, Socket socket) {
        this.packetMap.forEach(packetEvent -> packetEvent.onPacketRecieve(jsonElement, socket));
    }
    
    public static interface PacketEvent {
        
        void onPacketRecieve(JsonElement jsonElement, Socket socket);
        
    }

}
