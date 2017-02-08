/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.distribution.service 
 * Date:2017/1/12 0012
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.distribution.service;

import java.io.IOException;
import java.io.InputStream;

import hmap.core.hms.distribution.dto.HmsAppDistributionDTO;

public interface IAnalyseAppService {
    public HmsAppDistributionDTO analyseIpa(InputStream is);
    public HmsAppDistributionDTO analyseApk(String apkPath) throws IOException;
}
