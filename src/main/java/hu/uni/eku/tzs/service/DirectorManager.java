package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.model.DirectorGenre;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;

import java.util.List;

public interface DirectorManager {
    Director record(Director director) throws DirectorAlreadyExistsException;

    Director readById(int id) throws DirectorNotFoundException;

    List<Director> readAll();

    Director modify(Director director);

    void delete(Director director) throws DirectorNotFoundException;

    List<DirectorGenre> readGenre(int id) throws DirectorNotFoundException;

    List<Movie> readMovie(int id) throws DirectorNotFoundException;
}
