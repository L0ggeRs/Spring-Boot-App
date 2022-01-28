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
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.service.DirectorManager;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class DirectorControllerTest {

    @Mock
    private DirectorManager directorManager;

    @Mock
    private DirectorMapper directorMapper;

    @Mock
    private DirectorGenreMapper directorGenreMapper;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private DirectorController controller;

    @Test
    void readAllHappyPath() {
        //given
        when(directorManager.readAll()).thenReturn(List.of(TestDataProviderController.getTestDirector()));
        when(directorMapper.director2DirectorDto(any())).thenReturn(TestDataProviderController.getTestDirectorDto());
        Collection<DirectorDto> expected = List.of(TestDataProviderController.getTestDirectorDto());
        //when
        Collection<DirectorDto> actual = controller.readAllDirectors();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdHappyPath() throws DirectorNotFoundException {
        //given
        when(directorManager.readById(TestDataProviderController.DIRECTOR_ID)).thenReturn(TestDataProviderController.getTestDirector());
        Director expected = TestDataProviderController.getTestDirector();
        //when
        Director actual = controller.readById(TestDataProviderController.DIRECTOR_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readGenreHappyPath() throws DirectorNotFoundException {
        //given
        when(directorManager.readGenre(TestDataProviderController.DIRECTOR_ID)).thenReturn(List.of(TestDataProviderController.getTestDirectorGenre()));
        when(directorGenreMapper.directorGenre2DirectorGenreDto(any())).thenReturn(TestDataProviderController.getTestDirectorGenreDto());
        List<DirectorGenreDto> expected = List.of(TestDataProviderController.getTestDirectorGenreDto());
        //when
        List<DirectorGenreDto> actual = controller.readGenre(TestDataProviderController.DIRECTOR_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readMovieHappyPath() throws DirectorNotFoundException {
        //given
        when(directorManager.readMovie(TestDataProviderController.DIRECTOR_ID)).thenReturn(List.of(TestDataProviderController.getTestMovie()));
        when(movieMapper.movie2MovieDto(any())).thenReturn(TestDataProviderController.getTestMovieDto());
        List<MovieDto> expected = List.of(TestDataProviderController.getTestMovieDto());
        //when
        List<MovieDto> actual = controller.readMovie(TestDataProviderController.DIRECTOR_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdDirectorNotFound() throws DirectorNotFoundException {
        //given
        final int notFoundDirectorId = TestDataProviderController.DIRECTOR_ID;
        doThrow(new DirectorNotFoundException()).when(directorManager).readById(notFoundDirectorId);
        //when then
        assertThatThrownBy(() -> controller.readById(notFoundDirectorId)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void readGenreDirectorNotFound() throws DirectorNotFoundException {
        //given
        final int notFoundDirectorId = TestDataProviderController.DIRECTOR_ID;
        doThrow(new DirectorNotFoundException()).when(directorManager).readGenre(notFoundDirectorId);
        //when then
        assertThatThrownBy(() -> controller.readGenre(notFoundDirectorId)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void readMoviesWhenDirectorNotFound() throws DirectorNotFoundException {
        //given
        final int notFoundDirectorId = TestDataProviderController.DIRECTOR_ID;
        doThrow(new DirectorNotFoundException()).when(directorManager).readMovie(notFoundDirectorId);
        //when then
        assertThatThrownBy(() -> controller.readMovie(notFoundDirectorId)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void createDirectorHappyPath() throws DirectorAlreadyExistsException {
        //given
        Director test = TestDataProviderController.getTestDirector();
        DirectorDto testDto = TestDataProviderController.getTestDirectorDto();
        when(directorMapper.directorDto2Director(testDto)).thenReturn(test);
        when(directorManager.record(test)).thenReturn(test);
        when(directorMapper.director2DirectorDto(test)).thenReturn(testDto);
        //when
        DirectorDto actual = controller.create(testDto);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testDto);
    }

    @Test
    void createDirectorThrowsDirectorAlreadyExistsException() throws DirectorAlreadyExistsException {
        //given
        Director test = TestDataProviderController.getTestDirector();
        DirectorDto testDto = TestDataProviderController.getTestDirectorDto();
        when(directorMapper.directorDto2Director(testDto)).thenReturn(test);
        when(directorManager.record(test)).thenThrow(new DirectorAlreadyExistsException());
        //when then
        assertThatThrownBy(() -> controller.create(testDto)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateHappyPath() {
        //given
        DirectorDto requestDto = TestDataProviderController.getTestDirectorDto();
        Director test = TestDataProviderController.getTestDirector();
        when(directorMapper.directorDto2Director(requestDto)).thenReturn(test);
        when(directorManager.modify(test)).thenReturn(test);
        when(directorMapper.director2DirectorDto(test)).thenReturn(requestDto);
        DirectorDto expected = TestDataProviderController.getTestDirectorDto();
        //when
        DirectorDto response = controller.update(requestDto);
        //then
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws DirectorNotFoundException {
        //given
        Director test = TestDataProviderController.getTestDirector();
        when(directorManager.readById(TestDataProviderController.DIRECTOR_ID)).thenReturn(test);
        doNothing().when(directorManager).delete(test);
        //when
        controller.delete(TestDataProviderController.DIRECTOR_ID);
    }

    @Test
    void deleteFromQueryParamWhenActorNotFound() throws DirectorNotFoundException {
        //given
        final int notFoundDirectorId = TestDataProviderController.DIRECTOR_ID;
        doThrow(new DirectorNotFoundException()).when(directorManager).readById(notFoundDirectorId);
        //when then
        assertThatThrownBy(() -> controller.delete(notFoundDirectorId)).isInstanceOf(ResponseStatusException.class);
    }
}