package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.IdealPlan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IdealPlan entity.
 */
public interface IdealPlanSearchRepository extends ElasticsearchRepository<IdealPlan, Long> {
}
