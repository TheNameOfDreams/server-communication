package tk.yallandev.common.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import tk.yallandev.common.entity.Entity;
import tk.yallandev.common.entity.EntityType;

public class EntityManager {
    
    private HashMap<EntityType, List<Entity>> entity;
    
    public EntityManager() {
        entity = new HashMap<>();
    }
    
}
