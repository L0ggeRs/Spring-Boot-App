package hu.uni.eku.tzs.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import hu.uni.eku.tzs.controller.dto.*;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.MovieManager;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @Mock
    private MovieManager movieManager;

    @Mock
    private MovieMapper movieMapper;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private DirectorMapper directorMapper;

    @Mock
    private MovieGenreMapper movieGenreMapper;

    @InjectMocks
    private MovieController controller;

    @Test
    void readAllHappyPath() {
        //given
        when(movieManager.readAll()).thenReturn(List.of(TestDataProviderController.getTestMovie()));
        when(movieMapper.movie2MovieDto(any())).thenReturn(TestDataProviderController.getTestMovieDto());
        Collection<MovieDto> expected = List.of(TestDataProviderController.getTestMovieDto());
        //when
        Collection<MovieDto> actual = controller.readAllMovies();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readRoleHappyPath() throws MovieNotFoundException {
        //given
        when(movieManager.readRole(TestDataProviderController.MOVIE_ID)).thenReturn(List.of(TestDataProviderController.getTestRole()));
        when(roleMapper.role2RoleDto(any())).thenReturn(TestDataProviderController.getTestRoleDto());
        List<RoleDto> expected = List.of(TestDataProviderController.getTestRoleDto());
        //when
        List<RoleDto> actual = controller.readRole(TestDataProviderController.MOVIE_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readGenreHappyPath() throws MovieNotFoundException {
        //given
        when(movieManager.readGenre(TestDataProviderController.MOVIE_ID)).thenReturn(List.of(TestDataProviderController.getMovieGenre()));
        when(movieGenreMapper.movieGenre2MovieGenreDto(any())).thenReturn(TestDataProviderController.getMovieGenreDto());
        List<MovieGenreDto> expected = List.of(TestDataProviderController.getMovieGenreDto());
        //when
        List<MovieGenreDto> actual = controller.readGenre(TestDataProviderController.MOVIE_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readDirectorHappyPath() throws MovieNotFoundException {
        //given
        when(movieManager.readDirectors(TestDataProviderController.MOVIE_ID)).thenReturn(List.of(TestDataProviderController.getTestDirector()));
        when(directorMapper.director2DirectorDto(any())).thenReturn(TestDataProviderController.getTestDirectorDto());
        List<DirectorDto> expected = List.of(TestDataProviderController.getTestDirectorDto());
        //when
        List<DirectorDto> actual = controller.readDirectors(TestDataProviderController.MOVIE_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdHappyPath() throws MovieNotFoundException {
        //given
        when(movieManager.readById(TestDataProviderController.MOVIE_ID)).thenReturn(TestDataProviderController.getTestMovie());
        Movie expected = TestDataProviderController.getTestMovie();
        //when
        Movie actual = controller.readById(TestDataProviderController.MOVIE_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdWhenMovieNotFoundException() throws MovieNotFoundException {
        //given
        final int notFoundMovieId = TestDataProviderController.MOVIE_ID;
        doThrow(new MovieNotFoundException()).when(movieManager).readById(notFoundMovieId);
        //when then
        assertThatThrownBy(() -> controller.readById(notFoundMovieId)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void readRoleMovieNotFoundException() throws MovieNotFoundException {
        //given
        final int notFoundMovieId = TestDataProviderController.MOVIE_ID;
        doThrow(new MovieNotFoundException()).when(movieManager).readRole(notFoundMovieId);
        //when then
        assertThatThrownBy(() -> controller.readRole(notFoundMovieId)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void readGenreMovieNotFoundException() throws MovieNotFoundException {
        //given
        final int notFoundMovieId = TestDataProviderController.MOVIE_ID;
        doThrow(new MovieNotFoundException()).when(movieManager).readGenre(notFoundMovieId);
        //when then
        assertThatThrownBy(() -> controller.readGenre(notFoundMovieId)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void readDirectorsWhenMovieNotFoundException() throws MovieNotFoundException {
        //given
        final int notFoundMovieId = TestDataProviderController.MOVIE_ID;
        doThrow(new MovieNotFoundException()).when(movieManager).readDirectors(notFoundMovieId);
        //when then
        assertThatThrownBy(() -> controller.readDirectors(notFoundMovieId)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void createMovieHappyPath() throws MovieAlreadyExistsException {
        //given
        Movie test = TestDataProviderController.getTestMovie();
        MovieDto testDto = TestDataProviderController.getTestMovieDto();
        when(movieMapper.movieDto2Movie(testDto)).thenReturn(test);
        when(movieManager.record(test)).thenReturn(test);
        when(movieMapper.movie2MovieDto(test)).thenReturn(testDto);
        //when
        MovieDto actual = controller.create(testDto);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testDto);
    }

    @Test
    void createMovieThrowsMovieAlreadyExistsException() throws MovieAlreadyExistsException {
        //given
        Movie test = TestDataProviderController.getTestMovie();
        MovieDto testDto = TestDataProviderController.getTestMovieDto();
        when(movieMapper.movieDto2Movie(testDto)).thenReturn(test);
        when(movieManager.record(test)).thenThrow(new MovieAlreadyExistsException());
        //when then
        assertThatThrownBy(() -> controller.create(testDto)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateHappyPath() {
        //given
        MovieDto requestDto = TestDataProviderController.getTestMovieDto();
        Movie test = TestDataProviderController.getTestMovie();
        when(movieMapper.movieDto2Movie(requestDto)).thenReturn(test);
        when(movieManager.modify(test)).thenReturn(test);
        when(movieMapper.movie2MovieDto(test)).thenReturn(requestDto);
        MovieDto expected = TestDataProviderController.getTestMovieDto();
        //when
        MovieDto response = controller.update(requestDto);
        //then
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws MovieNotFoundException {
        //given
        Movie test = TestDataProviderController.getTestMovie();
        when(movieManager.readById(TestDataProviderController.MOVIE_ID)).thenReturn(test);
        doNothing().when(movieManager).delete(test);
        //when
        controller.delete(TestDataProviderController.MOVIE_ID);
    }

    @Test
    void deleteFromQueryParamWhenMovieNotFound() throws MovieNotFoundException {
        //given
        final int notFoundMovieId = TestDataProviderController.MOVIE_ID;
        doThrow(new MovieNotFoundException()).when(movieManager).readById(notFoundMovieId);
        //when then
        assertThatThrownBy(() -> controller.delete(notFoundMovieId)).isInstanceOf(ResponseStatusException.class);
    }
}