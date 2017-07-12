package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.IdealPlanEntries;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IdealPlanEntries entity.
 */
public interface IdealPlanEntriesSearchRepository extends ElasticsearchRepository<IdealPlanEntries, Long> {
}
