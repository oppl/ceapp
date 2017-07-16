package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.CollisionLevelThree;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CollisionLevelThree entity.
 */
public interface CollisionLevelThreeSearchRepository extends ElasticsearchRepository<CollisionLevelThree, Long> {
}
