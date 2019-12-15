package tk.yallandev;

import lombok.Getter;
import tk.yallandev.common.manager.EntityManager;
import tk.yallandev.common.manager.PlayerManager;
import tk.yallandev.common.packetmanager.PacketListener;
import tk.yallandev.common.update.UpdateListener;
import tk.yallandev.common.update.UpdateListener.TickTime;
import tk.yallandev.common.update.UpdateListener.UpdateEvent;

@Getter
public class CommonGeneral {
    
    @Getter
    private static CommonGeneral instance;
    
    /**
     * Subject to Observer
     */
    
    private UpdateListener updateListener;
    private PacketListener packetListener;
    
    /**
     * Entity Manager General
     */
    
    private PlayerManager playerManager;
    private EntityManager entityManager;
    
    public CommonGeneral() {
        instance = this;
        
        new Thread(this.updateListener = new UpdateListener()).start();
        this.packetListener = new PacketListener();
        this.playerManager = new PlayerManager();
        this.entityManager = new EntityManager();
        
        updateListener.register(new UpdateEvent() {
            
            @Override
            public void tick(TickTime tickTime) {
                System.out.println(tickTime);
            }
        });
    }
    
    public void debug(String string, String prefix) {
        if (prefix == null)
            System.out.println("[DEBUG] " + string);
        else 
            System.out.println("[" + prefix + "] " + string);
    }
    
}
