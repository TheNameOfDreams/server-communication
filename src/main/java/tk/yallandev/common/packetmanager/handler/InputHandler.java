package tk.yallandev.common.packetmanager.handler;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;

import com.google.gson.JsonElement;

import tk.yallandev.CommonConst;
import tk.yallandev.CommonGeneral;
import tk.yallandev.common.connection.Connection;

public class InputHandler extends Thread {
    
    private Connection connection;
    private DataInputStream data;
    
    private boolean run;
    
    public InputHandler(Connection connection, DataInputStream data) {
        this.connection = connection;
        this.data = data;
    }
    
    @Override
    public void run() {
        run = true;
        while (run) {
            try {
                
                String jsonElementToString = data.readUTF();
                
                if (jsonElementToString != null) {
                    JsonElement jsonElement = CommonConst.PARSER.parse(jsonElementToString);
                    
                    CommonGeneral.getInstance().getPacketListener().call(jsonElement, connection.getSocket());
                }
            } catch (EOFException e) {
                run = false;
            } catch (SocketException e) {
                //TODO nothing to do
            } catch (IOException e) {
                e.printStackTrace();
                connection.disconnect();
            }
        }
    }
    
    public void stopThread() {
        run = false;
    }
    
    public void close() throws IOException {
        data.close();
    }

}
