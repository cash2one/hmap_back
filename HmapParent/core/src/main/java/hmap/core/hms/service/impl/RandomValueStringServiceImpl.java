/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/11/21 0021 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.service.impl;

import hmap.core.hms.service.IRandomValueStringService;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Service;

@Service
public class RandomValueStringServiceImpl implements IRandomValueStringService {

  @Override
  public String getRandomString(int length) {
    RandomValueStringGenerator generator = new RandomValueStringGenerator(length);
    return generator.generate();
  }
}
