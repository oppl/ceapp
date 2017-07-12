package at.meroff.itproject.repository.search;

import at.meroff.itproject.domain.Appointment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Appointment entity.
 */
public interface AppointmentSearchRepository extends ElasticsearchRepository<Appointment, Long> {
}
