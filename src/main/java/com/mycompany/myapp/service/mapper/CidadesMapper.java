package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Cidades;
import com.mycompany.myapp.service.dto.CidadesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cidades} and its DTO {@link CidadesDTO}.
 */
@Mapper(componentModel = "spring")
public interface CidadesMapper extends EntityMapper<CidadesDTO, Cidades> {}
