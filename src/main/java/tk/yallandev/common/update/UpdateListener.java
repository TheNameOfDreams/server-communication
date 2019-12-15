package tk.yallandev.common.update;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yandv
 */

public class UpdateListener implements Runnable {

    private long currentTick;
    private List<UpdateEvent> list;

    public UpdateListener() {
        list = new ArrayList<>();
    }

    public void register(UpdateEvent updateEvent) {
        list.add(updateEvent);
    }

    public void unregister(UpdateEvent updateEvent) {
        list.remove(updateEvent);
    }

    @Override
    public void run() {

        currentTick++;

        if (currentTick % 50 == 0)
            call(TickTime.TICK);

        if (currentTick % 1000 == 0)
            call(TickTime.SECOND);

        if (currentTick % 60000 == 0)
            call(TickTime.MINUTE);
    }

    private void call(TickTime tickTime) {
        list.forEach(updateEvent -> updateEvent.tick(tickTime));
    }

    public static interface UpdateEvent {

        void tick(TickTime tickTime);

    }

    public static enum TickTime {

        TICK, SECOND, MINUTE;

    }

}
