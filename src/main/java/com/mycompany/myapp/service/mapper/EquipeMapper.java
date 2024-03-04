package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Equipe;
import com.mycompany.myapp.service.dto.EquipeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Equipe} and its DTO {@link EquipeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EquipeMapper extends EntityMapper<EquipeDTO, Equipe> {}
