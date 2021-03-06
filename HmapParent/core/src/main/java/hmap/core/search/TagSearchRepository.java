/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.search 
 * Date:2016/11/19 0019
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.search;

import hmap.core.hms.contact.domain.HmsContactTag;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TagSearchRepository extends ElasticsearchRepository<HmsContactTag, String> {
    @Query(value = "{\"query\":{\"dis_max\":{\"queries\":[" +
        "{\"wildcard\":{\"tagName\":{\"wildcard\":\"*?0*\",\"boost\":2}}}," +
        "{\"wildcard\":{\"tagPinyin\":{\"wildcard\":\"*?0*\",\"boost\":1}}}," +
        "{\"wildcard\":{\"tagPinyin\":{\"wildcard\":\"*|?0|*\",\"boost\":3}}}," +
        "{\"wildcard\":{\"tagPinyinCapital\":{\"wildcard\":\"*?0*\",\"boost\":3}}}]," +
        "\"tie_breaker\":0.3}," +
        "\"sort\":[{\"_score\":{\"order\":\"desc\"}},{\"id\":{\"order\":\"desc\"}}}}")
    List<HmsContactTag> search(String key);
}
