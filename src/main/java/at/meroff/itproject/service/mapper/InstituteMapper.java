package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.InstituteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Institute and its DTO InstituteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InstituteMapper extends EntityMapper <InstituteDTO, Institute> {
    
    @Mapping(target = "curricula", ignore = true)
    @Mapping(target = "lvas", ignore = true)
    Institute toEntity(InstituteDTO instituteDTO); 
    default Institute fromId(Long id) {
        if (id == null) {
            return null;
        }
        Institute institute = new Institute();
        institute.setId(id);
        return institute;
    }
}
