package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.MovieGenre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MovieMapper.class})
public interface MovieGenreMapper {
    MovieGenreDto movieGenre2MovieGenreDto(MovieGenre movieGenre);
}
