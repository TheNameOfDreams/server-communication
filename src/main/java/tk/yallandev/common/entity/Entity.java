package tk.yallandev.common.entity;

import java.util.UUID;

import tk.yallandev.common.location.Location;

public interface Entity {
    
    UUID getUniqueId();
    
    String getName();
    
    Location getLocation();
    
    void setLocation(Location location);
    
    void setLocation(double x, double y);
    
    EntityType getEntityType();
    
}
