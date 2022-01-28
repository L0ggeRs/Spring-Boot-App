package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.model.MovieGenre;
import hu.uni.eku.tzs.model.Role;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;

import java.util.List;

public interface MovieManager {
    Movie record(Movie movie) throws MovieAlreadyExistsException;

    Movie readById(int id) throws MovieNotFoundException;

    List<Movie> readAll();

    Movie modify(Movie movie);

    void delete(Movie movie) throws MovieNotFoundException;

    List<MovieGenre> readGenre(int id) throws MovieNotFoundException;

    List<Role> readRole(int id) throws MovieNotFoundException;

    List<Director> readDirectors(int id) throws MovieNotFoundException;
}
