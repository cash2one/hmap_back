/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service
 * Date:2016/8/3
 * Create By:lei.chen03@hand-china.com
 */
package hmap.core.hms.edition.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.edition.domain.HmsAppEditionLine;

import java.util.List;

public interface IHmsAppEditionLineService extends
    IBaseService<HmsAppEditionLine>,ProxySelf<IHmsAppEditionLineService> {
    public  List<HmsAppEditionLine> selectByAppId(String appId, int page, int pagesize);
    public HmsAppEditionLine updateAppEditionAppLine(IRequest iRequest,HmsAppEditionLine appEditionAppLine);
    public HmsAppEditionLine insertAppEditionAppLine(IRequest iRequest,HmsAppEditionLine appEditionAppLine);
}
