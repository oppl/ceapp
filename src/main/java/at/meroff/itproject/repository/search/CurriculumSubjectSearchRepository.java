package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.CurriculumSubject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CurriculumSubject entity.
 */
public interface CurriculumSubjectSearchRepository extends ElasticsearchRepository<CurriculumSubject, Long> {
}
