package tk.yallandev.client;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Client {
    
    @Getter
    private static Client instance;
    
    private final UUID uniqueId;
    private String playerName;
    
    @Setter
    private ClientConnection clientConnection;
    
    public Client(UUID uniqueId, String playerName) {
        instance = this;
        
        this.uniqueId = uniqueId;
        this.playerName = playerName;
    }
    
    public static boolean isInstanced() {
        return instance != null;
    }

}
