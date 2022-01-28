package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.DirectorRepository;
import hu.uni.eku.tzs.dao.entity.*;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.model.DirectorGenre;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DirectorManagerImplTest {

    @Mock
    DirectorRepository directorRepository;

    @InjectMocks
    DirectorManagerImpl service;

    @Test
    void recordDirectorHappyPath() throws DirectorAlreadyExistsException {
        //given
        Director jon = TestDataProviderService.getJon();
        DirectorEntity jonEntity = TestDataProviderService.getJonEntity();
        when(directorRepository.findById(any())).thenReturn(Optional.empty());
        when(directorRepository.save(any())).thenReturn(jonEntity);
        //when
        Director actual = service.record(jon);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(jon);
    }

    @Test
    void recordDirectorAlreadyExistsException() {
        //given
        Director jon = TestDataProviderService.getJon();
        DirectorEntity jonEntity = TestDataProviderService.getJonEntity();
        when(directorRepository.findById(TestDataProviderService.JON_ID)).thenReturn(Optional.of(jonEntity));
        //when
        assertThatThrownBy(() -> service.record(jon)).isInstanceOf(DirectorAlreadyExistsException.class);
    }

    @Test
    void readByIdHappyPath() throws DirectorNotFoundException {
        //given
        when(directorRepository.findById(TestDataProviderService.JON_ID))
                .thenReturn(Optional.of(TestDataProviderService.getJonEntity()));
        Director expected = TestDataProviderService.getJon();
        //when
        Director actual = service.readById(TestDataProviderService.JON_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readMoviesHappyPath() throws DirectorNotFoundException {
        //given
        when(directorRepository.findById(TestDataProviderService.JON_ID))
                .thenReturn(Optional.of(TestDataProviderService.getJonMoviesEntity()));
        List<Movie> expected = TestDataProviderService.getMovies();
        //when
        List<Movie> actual = service.readMovie(TestDataProviderService.JON_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readGenresHappyPath() throws DirectorNotFoundException {
        //given
        when(directorRepository.findById(TestDataProviderService.JON_ID))
                .thenReturn(Optional.of(TestDataProviderService.getJonGenresEntity()));
        List<DirectorGenre> expected = TestDataProviderService.getGenres();
        //when
        List<DirectorGenre> actual = service.readGenre(TestDataProviderService.JON_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdDirectorNotFoundException() {
        //given
        when(directorRepository.findById(TestDataProviderService.UNKNOWN_ID)).thenReturn(Optional.empty());
        //when then
        assertThatThrownBy(() -> service.readById(TestDataProviderService.UNKNOWN_ID)).isInstanceOf(DirectorNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProviderService.UNKNOWN_ID));
    }

    @Test
    void readGenreDirectorNotFoundException() {
        //given
        when(directorRepository.findById(TestDataProviderService.UNKNOWN_ID)).thenReturn(Optional.empty());
        //when then
        assertThatThrownBy(() -> service.readGenre(TestDataProviderService.UNKNOWN_ID)).isInstanceOf(DirectorNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProviderService.UNKNOWN_ID));
    }

    @Test
    void readMovieDirectorNotFoundException() {
        //given
        when(directorRepository.findById(TestDataProviderService.UNKNOWN_ID)).thenReturn(Optional.empty());
        //when then
        assertThatThrownBy(() -> service.readMovie(TestDataProviderService.UNKNOWN_ID)).isInstanceOf(DirectorNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProviderService.UNKNOWN_ID));
    }

    @Test
    void readAllHappyPath() {
        //given
        List<DirectorEntity> directorEntities = List.of(
                TestDataProviderService.getJonEntity()
        );
        List<Director> expectedDirectors = List.of(
                TestDataProviderService.getJon()
        );
        when(directorRepository.findAll()).thenReturn(directorEntities);
        //when
        List<Director> actualDirectors = service.readAll();
        //then
        assertThat(actualDirectors)
                .usingRecursiveComparison()
                .isEqualTo(expectedDirectors);
    }

    @Test
    void modifyDirectorHappyPath() {
        //given
        Director jon = TestDataProviderService.getJon();
        DirectorEntity jonEntity = TestDataProviderService.getJonEntity();
        when(directorRepository.save(jonEntity)).thenReturn(jonEntity);
        //when
        Director actual = service.modify(jon);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(jon);
    }

    @Test
    void deleteHappyPath() throws DirectorNotFoundException{
        //given
        Director jon = TestDataProviderService.getJon();
        DirectorEntity directorEntity = TestDataProviderService.getJonEntity();
        directorRepository.delete(directorEntity);
        //when
        service.delete(jon);
    }
}