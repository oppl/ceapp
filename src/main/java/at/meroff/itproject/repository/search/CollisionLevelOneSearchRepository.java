package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.CollisionLevelOne;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CollisionLevelOne entity.
 */
public interface CollisionLevelOneSearchRepository extends ElasticsearchRepository<CollisionLevelOne, Long> {
}
