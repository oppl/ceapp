package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.CollisionLevelFive;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CollisionLevelFive entity.
 */
public interface CollisionLevelFiveSearchRepository extends ElasticsearchRepository<CollisionLevelFive, Long> {
}
