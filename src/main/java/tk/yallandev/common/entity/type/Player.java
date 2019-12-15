package tk.yallandev.common.entity.type;

import java.util.UUID;

import tk.yallandev.common.entity.Entity;
import tk.yallandev.common.entity.EntityType;
import tk.yallandev.common.location.Location;

public class Player implements Entity {
    
    private UUID uniqueId;
    private String playerName;
    
    private Location location = new Location(0, 0);
    
    public Player(UUID uniqueId, String playerName) {
        this.uniqueId = uniqueId;
        this.playerName = playerName;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return playerName;
    }

    public Location getLocation() {
        return null;
    }

    public void setLocation(Location location) {
        
    }

    public void setLocation(double x, double y) {
        
    }

    public EntityType getEntityType() {
        return EntityType.PLAYER;
    }

}
