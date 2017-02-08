/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.search Date:2016/8/22 0022 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.search;

import hmap.core.hms.contact.domain.HmsStaff;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface StaffSearchRepository extends ElasticsearchRepository<HmsStaff, String> {
  List<HmsStaff> findByAccountNumber(String id);

  @Query(value = "{\"query\":{\"dis_max\":{\"queries\":["
      + "{\"wildcard\":{\"accountNumber\":{\"wildcard\":\"?0\",\"boost\":7}}},"
      + "{\"wildcard\":{\"mobile\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"genderId\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
//      + "{\"wildcard\":{\"departmentId\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"wxNumber\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"baseName\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"empStatus\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"unitName\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"attribute1\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"attribute2\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"attribute3\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"attribute4\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"attribute5\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"attribute6\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"attribute7\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"attribute8\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"attribute9\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"userName\":{\"wildcard\":\"*?0*\",\"boost\":2}}},"
      + "{\"wildcard\":{\"homeTown\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"rootUnitName\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"namePinyin\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"namePinyin\":{\"wildcard\":\"*|?0*\",\"boost\":3}}},"
      + "{\"wildcard\":{\"email\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"namePinyinCapital\":{\"wildcard\":\"*|?0|*\",\"boost\":3}}}],"
      + "\"tie_breaker\":0.3},"
      + "\"sort\":[{\"_score\":{\"order\":\"desc\"}},{\"accountNumber\":{\"order\":\"desc\"}}}}")
  List<HmsStaff> findSelective(String id);

  @Query(value = "{\"query\":{\"dis_max\":{\"queries\":["
      + "{\"wildcard\":{\"accountNumber\":{\"wildcard\":\"?0\",\"boost\":7}}},"
      + "{\"wildcard\":{\"mobile\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
//      + "{\"wildcard\":{\"email\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"userName\":{\"wildcard\":\"*?0*\",\"boost\":2}}},"
//      + "{\"wildcard\":{\"namePinyin\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
      + "{\"wildcard\":{\"namePinyin\":{\"wildcard\":\"*|?0*\",\"boost\":2}}},"
      + "{\"wildcard\":{\"namePinyinCapital\":{\"wildcard\":\"*|?0|*\",\"boost\":3}}}],"
      + "\"tie_breaker\":0.3},"
      + "\"sort\":[{\"_score\":{\"order\":\"desc\"}},{\"accountNumber\":{\"order\":\"desc\"}}}}")
  List<HmsStaff> simpleSearch(String key);
}
