package hmap.core.hms.dto;

import hmap.core.hms.domain.HmsContent;

import java.util.List;

/**
 * Created by hand on 2016/12/14.
 */
public class HmsContentDTO extends HmsContent{
    private List<String> userName;

    public List<String> getUserName() {
        return userName;
    }

    public void setUserName(List<String> userName) {
        this.userName = userName;
    }
}
