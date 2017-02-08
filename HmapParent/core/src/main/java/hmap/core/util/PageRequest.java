/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.util 
 * Date:2016/8/4 0004
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.util;

public class PageRequest {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private int page=DEFAULT_PAGE;
    private int pagesize=DEFAULT_PAGE_SIZE;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}
