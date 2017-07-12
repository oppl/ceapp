package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.Institute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Institute entity.
 */
public interface InstituteSearchRepository extends ElasticsearchRepository<Institute, Long> {
}
