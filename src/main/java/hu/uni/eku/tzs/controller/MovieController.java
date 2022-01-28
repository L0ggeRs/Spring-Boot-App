package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.App;
import hu.uni.eku.tzs.controller.dto.DirectorMapper;
import hu.uni.eku.tzs.controller.dto.MovieGenreMapper;
import hu.uni.eku.tzs.controller.dto.MovieMapper;
import hu.uni.eku.tzs.controller.dto.DirectorDto;
import hu.uni.eku.tzs.controller.dto.MovieDto;
import hu.uni.eku.tzs.controller.dto.RoleMapper;
import hu.uni.eku.tzs.controller.dto.RoleDto;
import hu.uni.eku.tzs.controller.dto.MovieGenreDto;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.MovieManager;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Movies")
@RequestMapping("/movies")
@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieManager movieManager;

    private final MovieMapper movieMapper;

    private final RoleMapper roleMapper;

    private final MovieGenreMapper movieGenreMapper;

    private final DirectorMapper directorMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {"/"})
    public List<MovieDto> readAllMovies() {
        return movieManager.readAll()
                .stream()
                .limit(App.limit)
                .map(movieMapper::movie2MovieDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("Read By ID")
    @GetMapping("/{id}")
    public Movie readById(@PathVariable int id) {
        try {
            return movieManager.readById(id);
        } catch (MovieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ApiOperation("Read Genres")
    @RequestMapping(path = "/readGenres/{movieId}", method = RequestMethod.GET)
    public List<MovieGenreDto> readGenre(@RequestParam(value = "movieId") int movieId) {
        try {
            return movieManager.readGenre(movieId)
                    .stream()
                    .map(movieGenreMapper::movieGenre2MovieGenreDto)
                    .collect(Collectors.toList());
        } catch (MovieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ApiOperation("Read Roles")
    @RequestMapping(path = "/readRoles/{movieId}", method = RequestMethod.GET)
    public List<RoleDto> readRole(@RequestParam(value = "movieId") int movieId) {
        try {
            return movieManager.readRole(movieId)
                    .stream()
                    .map(roleMapper::role2RoleDto)
                    .collect(Collectors.toList());
        } catch (MovieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ApiOperation("Read Directors")
    @RequestMapping(path = "/readDirectors/{movieId}", method = RequestMethod.GET)
    public List<DirectorDto> readDirectors(@RequestParam(value = "directorId") int directorId) {
        try {
            return movieManager.readDirectors(directorId)
                    .stream()
                    .map(directorMapper::director2DirectorDto)
                    .collect(Collectors.toList());
        } catch (MovieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ApiOperation("Record")
    @PostMapping(value = {"/"})
    public MovieDto create(@Valid @RequestBody MovieDto recordRequestDto) {
        Movie movie = movieMapper.movieDto2Movie(recordRequestDto);
        try {
            Movie recordedMovie = movieManager.record(movie);
            return movieMapper.movie2MovieDto(recordedMovie);
        } catch (MovieAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping(value = {"/"})
    public MovieDto update(@Valid @RequestBody MovieDto updateRequestDto) {
        Movie movie = movieMapper.movieDto2Movie(updateRequestDto);
        Movie updatedMovie = movieManager.modify(movie);
        return movieMapper.movie2MovieDto(updatedMovie);
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"/"})
    public void delete(@RequestParam int id) {
        try {
            movieManager.delete(movieManager.readById(id));
        } catch (MovieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
