/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.search Date:2016/11/19 0019 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.search;

import hmap.core.hms.contact.domain.HmsDept;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DeptSearchRepository extends ElasticsearchRepository<HmsDept, String> {
  @Query(value = "{\"query\":{\"dis_max\":{\"queries\":["
      + "{\"wildcard\":{\"deptName\":{\"wildcard\":\"*?0*\",\"boost\":2}}},"
      + "{\"wildcard\":{\"deptPinyin\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"deptPinyin\":{\"wildcard\":\"*|?0|*\",\"boost\":3}}},"
      + "{\"wildcard\":{\"deptPinyinCapital\":{\"wildcard\":\"*?0*\",\"boost\":3}}}],"
      + "\"tie_breaker\":0.3},"
      + "\"sort\":[{\"_score\":{\"order\":\"desc\"}},{\"id\":{\"order\":\"desc\"}}}}")
  List<HmsDept> search(String key);
}
