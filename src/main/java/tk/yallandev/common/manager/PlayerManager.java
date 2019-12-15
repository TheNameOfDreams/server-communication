package tk.yallandev.common.manager;

import java.util.HashMap;
import java.util.UUID;

import com.google.gson.JsonObject;

import tk.yallandev.CommonConst;
import tk.yallandev.common.entity.type.Player;

public class PlayerManager {
    
    private HashMap<UUID, Player> playerMap;
    
    public PlayerManager() {
        this.playerMap = new HashMap<>();
    }
    
    public Player getPlayer(UUID uniqueId) {
        return playerMap.get(uniqueId);
    }
    
    public void registerPlayer(Player player) {
        playerMap.put(player.getUniqueId(), player);
    }
    
    public void unregisterPlayer(Player player) {
        playerMap.remove(player.getUniqueId());
    }
    
    public UUID getRandomUuid() {
        UUID uniqueId = UUID.randomUUID();
        
        while (playerMap.containsKey(uniqueId))
            uniqueId = UUID.randomUUID();
        
        return uniqueId;
    }
    
    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        playerMap.forEach((uuid, player) -> jsonObject.addProperty(uuid.toString(), CommonConst.GSON.toJson(player)));
        return jsonObject.toString();
    }
}
