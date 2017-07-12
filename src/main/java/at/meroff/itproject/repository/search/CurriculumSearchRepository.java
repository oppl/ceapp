package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.Curriculum;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Curriculum entity.
 */
public interface CurriculumSearchRepository extends ElasticsearchRepository<Curriculum, Long> {
}
