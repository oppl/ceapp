package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.CollisionLevelFour;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CollisionLevelFour entity.
 */
public interface CollisionLevelFourSearchRepository extends ElasticsearchRepository<CollisionLevelFour, Long> {
}
