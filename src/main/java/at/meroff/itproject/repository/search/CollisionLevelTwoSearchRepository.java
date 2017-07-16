package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.CollisionLevelTwo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CollisionLevelTwo entity.
 */
public interface CollisionLevelTwoSearchRepository extends ElasticsearchRepository<CollisionLevelTwo, Long> {
}
