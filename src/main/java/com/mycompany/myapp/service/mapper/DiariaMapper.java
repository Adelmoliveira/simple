package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Diaria;
import com.mycompany.myapp.domain.Missao;
import com.mycompany.myapp.service.dto.DiariaDTO;
import com.mycompany.myapp.service.dto.MissaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Diaria} and its DTO {@link DiariaDTO}.
 */
@Mapper(componentModel = "spring")
public interface DiariaMapper extends EntityMapper<DiariaDTO, Diaria> {
    @Mapping(target = "missao", source = "missao", qualifiedByName = "missaoId")
    DiariaDTO toDto(Diaria s);

    @Named("missaoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MissaoDTO toDtoMissaoId(Missao missao);
}
