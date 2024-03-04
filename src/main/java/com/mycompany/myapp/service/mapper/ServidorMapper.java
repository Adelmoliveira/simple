package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Equipe;
import com.mycompany.myapp.domain.Servidor;
import com.mycompany.myapp.service.dto.EquipeDTO;
import com.mycompany.myapp.service.dto.ServidorDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Servidor} and its DTO {@link ServidorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServidorMapper extends EntityMapper<ServidorDTO, Servidor> {
    @Mapping(target = "equipes", source = "equipes", qualifiedByName = "equipeIdSet")
    ServidorDTO toDto(Servidor s);

    @Mapping(target = "removeEquipes", ignore = true)
    Servidor toEntity(ServidorDTO servidorDTO);

    @Named("equipeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EquipeDTO toDtoEquipeId(Equipe equipe);

    @Named("equipeIdSet")
    default Set<EquipeDTO> toDtoEquipeIdSet(Set<Equipe> equipe) {
        return equipe.stream().map(this::toDtoEquipeId).collect(Collectors.toSet());
    }
}
