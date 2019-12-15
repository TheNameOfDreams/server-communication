package tk.yallandev.common.utils.string;

public class StringUtils {
    
    public static String format(String string, FormatType formatType) {
        switch (formatType) {
        case UPPER_NORMAL_LOWER: {
            return string.substring(0, 1).toUpperCase() + string.substring(1, string.length()).toLowerCase();
        }
        default: {
            return string;
        }
        }
    }
    
    public enum FormatType {
        
        UPPER_NORMAL_LOWER;
        
    }

}
