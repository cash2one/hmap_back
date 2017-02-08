package hmap.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enlline on 11/30/16.
 */
public class SystemJudgeUtil {
    private static List localtions = new ArrayList<>();
    private static String osName = System.getProperty("os.name").toLowerCase();

    public static List getLocaltions() {
        localtions.add("classpath:config.properties");
        if(osName.indexOf("linux")>=0){
            localtions.add("classpath:config_elasticsearch_linux.properties");
        }
        if(osName.indexOf("windows")>=0){
            localtions.add("classpath:config_elasticsearch_windows.properties");
        }
        return localtions;
    }

    public  void setLocaltions(List localtions) {
        this.localtions = localtions;
    }
}
