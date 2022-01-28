package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.MovieRepository;
import hu.uni.eku.tzs.dao.entity.*;
import hu.uni.eku.tzs.model.*;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieManagerImplTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieManagerImpl service;

    @Test
    void recordMovieHappyPath() throws MovieAlreadyExistsException {
        //given
        Movie moneyHeist = TestDataProviderService.getMoneyHeist();
        MovieEntity moneyHeistEntity = TestDataProviderService.getMoneyHeistEntity();
        when(movieRepository.findById(any())).thenReturn(Optional.empty());
        when(movieRepository.save(any())).thenReturn(moneyHeistEntity);
        //when
        Movie actual = service.record(moneyHeist);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(moneyHeist);
    }

    @Test
    void recordMovieAlreadyExistsException() {
        //given
        Movie moneyHeist = TestDataProviderService.getMoneyHeist();
        MovieEntity movieEntity = TestDataProviderService.getMoneyHeistEntity();
        when(movieRepository.findById(TestDataProviderService.MONEY_HEIST_ID)).thenReturn(Optional.ofNullable(movieEntity));
        //when
        assertThatThrownBy(() -> service.record(moneyHeist)).isInstanceOf(MovieAlreadyExistsException.class);
    }

    @Test
    void readByIdHappyPath() throws MovieNotFoundException {
        //given
        when(movieRepository.findById(TestDataProviderService.MONEY_HEIST_ID))
                .thenReturn(Optional.of(TestDataProviderService.getMoneyHeistEntity()));
        Movie expected = TestDataProviderService.getMoneyHeist();
        //when
        Movie actual = service.readById(TestDataProviderService.MONEY_HEIST_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readGenresHappyPath() throws MovieNotFoundException {
        //given
        when(movieRepository.findById(TestDataProviderService.MONEY_HEIST_ID))
                .thenReturn(Optional.of(TestDataProviderService.getMovieGenresEntity()));
        List<MovieGenre> expected = TestDataProviderService.getMoviesGenres();
        //when
        List<MovieGenre> actual = service.readGenre(TestDataProviderService.MONEY_HEIST_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readRolesHappyPath() throws MovieNotFoundException {
        //given
        when(movieRepository.findById(TestDataProviderService.MONEY_HEIST_ID))
                .thenReturn(Optional.of(TestDataProviderService.getMoviesRoleEntity()));
        List<Role> expected = TestDataProviderService.getRoles();
        //when
        List<Role> actual = service.readRole(TestDataProviderService.MONEY_HEIST_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readDirectorsHappyPath() throws MovieNotFoundException {
        //given
        when(movieRepository.findById(TestDataProviderService.MONEY_HEIST_ID))
                .thenReturn(Optional.of(TestDataProviderService.getMoviesDirectorEntity()));
        List<Director> expected = TestDataProviderService.getDirectors();
        //when
        List<Director> actual = service.readDirectors(TestDataProviderService.MONEY_HEIST_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdMovieNotFoundException() {
        //given
        when(movieRepository.findById(TestDataProviderService.UNKNOWN_ID)).thenReturn(Optional.empty());
        //when then
        assertThatThrownBy(() -> service.readById(TestDataProviderService.UNKNOWN_ID)).isInstanceOf(MovieNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProviderService.UNKNOWN_ID));
    }

    @Test
    void readRoleMovieNotFoundException() {
        //given
        when(movieRepository.findById(TestDataProviderService.UNKNOWN_ID)).thenReturn(Optional.empty());
        //when then
        assertThatThrownBy(() -> service.readRole(TestDataProviderService.UNKNOWN_ID)).isInstanceOf(MovieNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProviderService.UNKNOWN_ID));
    }

    @Test
    void readGenreMovieNotFoundException() {
        //given
        when(movieRepository.findById(TestDataProviderService.UNKNOWN_ID)).thenReturn(Optional.empty());
        //when then
        assertThatThrownBy(() -> service.readGenre(TestDataProviderService.UNKNOWN_ID)).isInstanceOf(MovieNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProviderService.UNKNOWN_ID));
    }

    @Test
    void readDirectorMovieNotFoundException() {
        //given
        when(movieRepository.findById(TestDataProviderService.UNKNOWN_ID)).thenReturn(Optional.empty());
        //when then
        assertThatThrownBy(() -> service.readDirectors(TestDataProviderService.UNKNOWN_ID)).isInstanceOf(MovieNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProviderService.UNKNOWN_ID));
    }

    @Test
    void readAllHappyPath() {
        //given
        List<MovieEntity> movieEntities = List.of(
                TestDataProviderService.getMoneyHeistEntity()
        );
        List<Movie> expectedMovies = List.of(
                TestDataProviderService.getMoneyHeist()
        );
        when(movieRepository.findAll()).thenReturn(movieEntities);
        //when
        List<Movie> actualMovies = service.readAll();
        //then
        assertThat(actualMovies).usingRecursiveComparison().isEqualTo(expectedMovies);
    }

    @Test
    void modifyMovieHappyPath() {
        //given
        Movie moneyHeist = TestDataProviderService.getMoneyHeist();
        MovieEntity moneyHeistEntity = TestDataProviderService.getMoneyHeistEntity();
        when(movieRepository.save(moneyHeistEntity)).thenReturn(moneyHeistEntity);
        //when
        Movie actual = service.modify(moneyHeist);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(moneyHeist);
    }

    @Test
    void deleteHappyPath() throws MovieNotFoundException {
        //given
        Movie money = TestDataProviderService.getMoneyHeist();
        MovieEntity movieEntity = TestDataProviderService.getMoneyHeistEntity();
        movieRepository.delete(movieEntity);
        //then
        service.delete(money);
    }
}