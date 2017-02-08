package hmap.core.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.util
 * Date:2016/8/18
 * Create By:xincai.zhang@hand-china.com
 */
public class FileHelper {
    public static Map fileNameFormat(String fileName) {
        Date current = new Date();
        String dateString = String.valueOf(current.getTime());
        int index = fileName.lastIndexOf(".");
        String extName = fileName.substring(index + 1);
        String prefixName = fileName.substring(0, index);
        //String replaceStr = prefixName.replace('@', 'a').replace('#', 'b').replace('$', 'c').replace('&','d');
        fileName = dateString.concat(".").concat(extName);
        Map<String, String> map = new HashMap<String, String>();
        map.put("dateString", dateString);
        map.put("extName", extName);
        map.put("prefixName", prefixName);
//        map.put("replaceStr", replaceStr);
        map.put("fileName", fileName);
        return map;
    }
}
