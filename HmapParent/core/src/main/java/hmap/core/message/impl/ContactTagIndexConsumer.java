/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.message.impl Date:2016/11/19 0019 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.message.impl;

import com.hand.hap.message.IMessageConsumer;
import com.hand.hap.message.TopicMonitor;
import hmap.core.hms.contact.domain.HmsContactTag;
import hmap.core.search.TagSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;

@TopicMonitor(channel = {"public.es.contactTag.syn.insert", "public.es.contactTag.syn.update",
    "public.es.contactTag.syn.delete"})
public class ContactTagIndexConsumer implements IMessageConsumer<HmsContactTag> {
  @Autowired
  private TagSearchRepository tagSearchRepository;

  @Override
  public void onMessage(HmsContactTag hmsContactTag, String pattern) {
    if (pattern.equals("public.es.contactTag.syn.insert")) {
      tagSearchRepository.save(hmsContactTag);
    } else if (pattern.equals("public.es.contactTag.syn.update")) {
      if (tagSearchRepository.exists(hmsContactTag.getId())) {
        tagSearchRepository.save(hmsContactTag);
      }
    } else if (pattern.equals("public.es.contactTag.syn.delete")) {
      tagSearchRepository.delete(hmsContactTag.getId());
    }
  }
}
