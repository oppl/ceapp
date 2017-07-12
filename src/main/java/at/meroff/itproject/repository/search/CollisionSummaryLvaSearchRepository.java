package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.CollisionSummaryLva;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CollisionSummaryLva entity.
 */
public interface CollisionSummaryLvaSearchRepository extends ElasticsearchRepository<CollisionSummaryLva, Long> {
}
