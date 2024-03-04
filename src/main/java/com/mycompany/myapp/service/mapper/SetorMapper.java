package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Servidor;
import com.mycompany.myapp.domain.Setor;
import com.mycompany.myapp.service.dto.ServidorDTO;
import com.mycompany.myapp.service.dto.SetorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Setor} and its DTO {@link SetorDTO}.
 */
@Mapper(componentModel = "spring")
public interface SetorMapper extends EntityMapper<SetorDTO, Setor> {
    @Mapping(target = "servidor", source = "servidor", qualifiedByName = "servidorId")
    SetorDTO toDto(Setor s);

    @Named("servidorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServidorDTO toDtoServidorId(Servidor servidor);
}
