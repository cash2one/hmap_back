package hmap.core.search;

import hmap.core.hms.feedback.domain.HmsFeedback;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by hand on 2016/11/17.
 */
public interface FeedbackSearchRepository extends ElasticsearchRepository<HmsFeedback, String> {
//    @Query(value = "{\"query\":{\"dis_max\":{\"queries\":["
//            + "{\"wildcard\":" + "{\"feedbackId\":{\"wildcard\":\"*?0*\",\"boost\":2}}},"
//            + "{\"wildcard\":{\"userId\":{\"wildcard\":\"*?0*\",\"boost\":1}}},"
//            + "{\"wildcard\":{\"feedbackData\":{\"wildcard\":\"*?0*\",\"boost\":3}}},"
//            + "{\"wildcard\":{\"replyFlag\":{\"wildcard\":\"*?0*\",\"boost\":3}}},"
//            + "{\"wildcard\":{\"feedbackType\":{\"wildcard\":\"*?0*\",\"boost\":3}}},"
//            + "{\"wildcard\":{\"feedbackDate\":{\"wildcard\":\"*?0*\",\"boost\":3}}}],"
//            + "\"tie_breaker\":0.3},"
//            + "\"sort\":[{\"_score\":{\"order\":\"desc\"}},{\"feedbackData\":{\"order\":\"desc\"}}}}")
@Query(value = "{\"query\":{\"match\": {\"feedbackData\":{\"query\":\"*?0*\",\"minimum_should_match\":\"15%\"}}}," +
        "\"sort\":[{\"_score\":\"desc\"}]}")
    List<HmsFeedback> search(String key);
}
