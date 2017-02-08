/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/8/12 0012 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.api.service;

import hmap.core.hms.api.dto.HeaderAndLineDTO;
import net.sf.json.JSONObject;

public interface IApiService {
  public JSONObject invoke(HeaderAndLineDTO headerAndLineDTO,JSONObject inbound);
}
