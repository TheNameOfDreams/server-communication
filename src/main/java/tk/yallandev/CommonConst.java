package tk.yallandev;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class CommonConst {
    
    public static final Gson GSON = new Gson();
    
    public static final JsonParser PARSER = new JsonParser();
    
    public static final long TIMEOUT_TIME = 30000l;
    
    public static final int PORT = 57966;

}
