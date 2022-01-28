package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.MovieRepository;
import hu.uni.eku.tzs.dao.entity.DirectorEntity;
import hu.uni.eku.tzs.dao.entity.MovieEntity;
import hu.uni.eku.tzs.dao.entity.MovieGenreEntity;
import hu.uni.eku.tzs.dao.entity.RoleEntity;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.model.MovieGenre;
import hu.uni.eku.tzs.model.Role;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieManagerImpl implements MovieManager {

    private final String message = "Cannot find movie with ID %s";

    private final MovieRepository movieRepository;

    private static Movie convertMovieEntity2Model(MovieEntity movieEntity) {
        return new Movie(
                movieEntity.getId(),
                movieEntity.getName(),
                movieEntity.getYear(),
                movieEntity.getRank()
        );
    }

    private static MovieEntity convertMovieModel2Entity(Movie movie) {
        return MovieEntity.builder()
                .id(movie.getId())
                .name(movie.getName())
                .year(movie.getYear())
                .rank(movie.getRank())
                .build();
    }

    private static MovieGenre convertMovieGenreEntity2Model(MovieGenreEntity movieGenreEntity) {
        return new MovieGenre(
                movieGenreEntity.getGenre(),
                new Movie(
                        movieGenreEntity.getMovie().getId(),
                        movieGenreEntity.getMovie().getName(),
                        movieGenreEntity.getMovie().getYear(),
                        movieGenreEntity.getMovie().getRank()
                )
        );
    }

    private static Role convertRoleEntity2Model(RoleEntity roleEntity) {
        return new Role(
                roleEntity.getRole(),
                new Actor(
                        roleEntity.getActor().getId(),
                        roleEntity.getActor().getFirstName(),
                        roleEntity.getActor().getLastName(),
                        roleEntity.getActor().getGender()
                ),
                new Movie(
                        roleEntity.getMovie().getId(),
                        roleEntity.getMovie().getName(),
                        roleEntity.getMovie().getYear(),
                        roleEntity.getMovie().getRank()

                )
        );
    }

    private static Director convertDirectorEntity2Model(DirectorEntity directorEntity) {
        return new Director(
                directorEntity.getId(),
                directorEntity.getFirstName(),
                directorEntity.getLastName()
        );
    }

    @Override
    public Movie record(Movie movie) throws MovieAlreadyExistsException {
        if (movieRepository.findById(movie.getId()).isPresent()) {
            throw new MovieAlreadyExistsException();
        }
        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .id(movie.getId())
                        .name(movie.getName())
                        .year(movie.getYear())
                        .rank(movie.getRank())
                        .build()
        );
        return convertMovieEntity2Model(movieEntity);
    }

    @Override
    public Movie readById(int id) throws MovieNotFoundException {
        Optional<MovieEntity> entity = movieRepository.findById(id);
        if (entity.isEmpty()) {
            throw new MovieNotFoundException(String.format(message, id));
        }
        return convertMovieEntity2Model(entity.get());
    }

    @Override
    public List<Movie> readAll() {
        return movieRepository.findAll().stream().map(MovieManagerImpl::convertMovieEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieGenre> readGenre(int id) throws MovieNotFoundException {
        Optional<MovieEntity> entity = movieRepository.findById(id);
        if (entity.isEmpty()) {
            throw new MovieNotFoundException(String.format(message, id));
        }
        return entity.get().getGenres().stream()
                .map(MovieManagerImpl::convertMovieGenreEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> readRole(int id) throws MovieNotFoundException {
        Optional<MovieEntity> entity = movieRepository.findById(id);
        if (entity.isEmpty()) {
            throw new MovieNotFoundException(String.format(message, id));
        }
        return entity.get().getRoles().stream()
                .map(MovieManagerImpl::convertRoleEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public List<Director> readDirectors(int id) throws MovieNotFoundException {
        Optional<MovieEntity> entity = movieRepository.findById(id);
        if (entity.isEmpty()) {
            throw new MovieNotFoundException(String.format(message, id));
        }

        return entity.get().getDirectors().stream()
                .map(MovieManagerImpl::convertDirectorEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Movie modify(Movie movie) {
        MovieEntity entity = convertMovieModel2Entity(movie);
        return convertMovieEntity2Model(movieRepository.save(entity));
    }

    @Override
    public void delete(Movie movie) throws MovieNotFoundException {
        movieRepository.delete(convertMovieModel2Entity(movie));
    }
}
