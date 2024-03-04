package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Cidades;
import com.mycompany.myapp.domain.Missao;
import com.mycompany.myapp.service.dto.CidadesDTO;
import com.mycompany.myapp.service.dto.MissaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Missao} and its DTO {@link MissaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface MissaoMapper extends EntityMapper<MissaoDTO, Missao> {
    @Mapping(target = "municipio", source = "municipio", qualifiedByName = "cidadesId")
    MissaoDTO toDto(Missao s);

    @Named("cidadesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CidadesDTO toDtoCidadesId(Cidades cidades);
}
