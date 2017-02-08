package hmap.core.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import freemarker.cache.URLTemplateLoader;

/**
 * Created by hand on 2016/12/16.
 */
public class RemoteTemplateLoader extends URLTemplateLoader {
    // 远程模板文件的存储路径（目录）
    private String remotePath;

    private List<String> includePaths;

    private String paths;

    public RemoteTemplateLoader(String remotePath) {
        if (remotePath == null) {
            throw new IllegalArgumentException("remotePath is null");
        }
        this.remotePath = canonicalizePrefix(remotePath);
        if (this.remotePath.indexOf('/') == 0) {
            this.remotePath = this.remotePath.substring(this.remotePath.indexOf('/') + 1);
        }
    }

    @Override
    public Object findTemplateSource(String name) throws IOException {
        if(this.includePaths!=null&&this.includePaths.contains(name)){
            return super.findTemplateSource(name);
        }
        return null;

    }

    @Override
    protected URL getURL(String name) {
        // name = name.replace("_zh", "");
        String fullPath = this.remotePath + name;
        if ((this.remotePath.equals("/")) && (!isSchemeless(fullPath))) {
            return null;
        }

        URL url = null;
        try {
            url = new URL(fullPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static boolean isSchemeless(String fullPath) {
        int i = 0;
        int ln = fullPath.length();

        if ((i < ln) && (fullPath.charAt(i) == '/'))
            i++;

        while (i < ln) {
            char c = fullPath.charAt(i);
            if (c == '/')
                return true;
            if (c == ':')
                return false;
            i++;
        }
        return true;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public void setPaths(String paths) {
        this.paths = paths;
        if (StringUtils.isNotEmpty(this.paths)) {
            String [] s = this.paths.split(";");
            this.includePaths = Arrays.asList(s);
        }
    }
}
