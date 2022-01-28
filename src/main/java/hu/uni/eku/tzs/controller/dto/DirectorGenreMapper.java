package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.DirectorGenre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DirectorMapper.class})
public interface DirectorGenreMapper {
    DirectorGenreDto directorGenre2DirectorGenreDto(DirectorGenre directorGenre);
}
