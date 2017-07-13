package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.CurriculumSemester;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CurriculumSemester entity.
 */
public interface CurriculumSemesterSearchRepository extends ElasticsearchRepository<CurriculumSemester, Long> {
}
