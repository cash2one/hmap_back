/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/11/11 0011 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.contact.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.message.IMessagePublisher;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hmap.core.hms.contact.domain.HmsContactTag;
import hmap.core.hms.contact.exception.HmsContactTagException;
import hmap.core.hms.contact.mapper.HmsContactTagMapper;
import hmap.core.hms.contact.service.IHmsContactTagService;
import hmap.core.search.TagSearchRepository;
import hmap.core.security.SecurityUtils;
import hmap.core.util.PinyinUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class HmsContactTagServiceImpl extends BaseServiceImpl<HmsContactTag>
    implements IHmsContactTagService {
  private Logger logger = LoggerFactory.getLogger(HmsContactTagServiceImpl.class);
  @Autowired
  HmsContactTagMapper hmsContactTagMapper;
  @Autowired
  IMessagePublisher messagePublisher;
  @Autowired
  TagSearchRepository tagSearchRepository;

  @Override
  public List<HmsContactTag> selectAllTags(String userName) {
    List<HmsContactTag> tags = hmsContactTagMapper.selectAllTags(userName);
    return tags;
  }

  public HmsContactTag insertOrUpdate(IRequest request, HmsContactTag hmsContactTag)
      throws HmsContactTagException {
    String userName = SecurityUtils.getCurrentUserLogin();
    hmsContactTag.setTagPinyin(PinyinUtils.getPinyin(hmsContactTag.getTagName()));
    hmsContactTag.setTagPinyinCapital(PinyinUtils.getPinyinCapital(hmsContactTag.getTagName()));
    if (StringUtils.isNotEmpty(hmsContactTag.getId())) {
      if (!hmsContactTag.getTagOwner().equals(userName)
          && !"unlock".equals(hmsContactTag.getTagStatus())) {
        // 如果编辑的数据不是属于标签所有人则爆出异常
        throw new HmsContactTagException(HmsContactTagException.EXCEPTION_CODE,
            HmsContactTagException.TAG_LOCKED);
      }
      self().updateByPrimaryKeySelective(request, hmsContactTag);
      messagePublisher.publish("public.es.contactTag.syn.update", hmsContactTag);
    } else {
      hmsContactTag.setTagOwner(userName);
      hmsContactTag.setTagStatus("lock");
      self().insert(request, hmsContactTag);
      messagePublisher.publish("public.es.contactTag.syn.insert", hmsContactTag);
    }
    return hmsContactTag;
  }

  @Override
  public List<HmsContactTag> search(String key) {
    return tagSearchRepository.search(key);
  }

  @PostConstruct
  public void init() {
    if (tagSearchRepository != null && tagSearchRepository.count() < 1) {
      List<HmsContactTag> allTags = this.selectAll();
      if (allTags != null && allTags.size() > 0) {
        tagSearchRepository.save(allTags);
      }
    }
  }
}
