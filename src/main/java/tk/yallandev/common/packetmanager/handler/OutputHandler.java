package tk.yallandev.common.packetmanager.handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import tk.yallandev.common.connection.Connection;

public class OutputHandler extends Thread {
    
    private Connection connection;
    private DataOutputStream data;
    
    private final Object LOCK = new Object();
    private final List<JsonElement> packetQueue = new ArrayList<>();
    
    private boolean run;

    public OutputHandler(Connection connection, DataOutputStream data) {
        this.connection = connection;
        this.data = data;
    }
    
    @Override
    public void run() {
        run = true;
        while (run) {
            try {
                synchronized (packetQueue) {
                    while (packetQueue.size() > 0) {
                        JsonElement jsonElement = packetQueue.get(0);

                        if (jsonElement != null)
                            data.writeUTF(jsonElement.toString());
                        
                        packetQueue.remove(0);
                    }
                }
                synchronized (LOCK) {
                    LOCK.wait(3250);
                }
            } catch (IOException e) {
                e.printStackTrace();
                packetQueue.remove(0);
                connection.disconnect();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                packetQueue.remove(0);
                e.printStackTrace();
            }
        }
    }
    
    public void sendPacket(JsonElement jsonElement) {
        synchronized (packetQueue) {
            packetQueue.add(jsonElement);
        }
        synchronized (LOCK) {
            LOCK.notifyAll();
        }
    }
    
    public void stopThread() {
        run = false;
    }
    
    public void close() throws IOException {
        data.close();
    }

}
