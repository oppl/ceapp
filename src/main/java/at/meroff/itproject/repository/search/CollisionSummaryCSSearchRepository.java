package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.CollisionSummaryCS;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CollisionSummaryCS entity.
 */
public interface CollisionSummaryCSSearchRepository extends ElasticsearchRepository<CollisionSummaryCS, Long> {
}
