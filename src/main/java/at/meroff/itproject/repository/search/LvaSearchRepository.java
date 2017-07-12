package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.Lva;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Lva entity.
 */
public interface LvaSearchRepository extends ElasticsearchRepository<Lva, Long> {
}
