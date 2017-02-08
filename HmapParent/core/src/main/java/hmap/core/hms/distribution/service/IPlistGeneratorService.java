/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.distribution.service Date:2017/1/23 0023 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.distribution.service;

import java.io.IOException;

import hmap.core.hms.distribution.dto.HmsAppDistributionDTO;

public interface IPlistGeneratorService {
  public String createPlist(HmsAppDistributionDTO dto) throws IOException;
}
