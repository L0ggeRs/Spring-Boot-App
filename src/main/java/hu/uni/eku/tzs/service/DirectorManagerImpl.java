package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.DirectorRepository;
import hu.uni.eku.tzs.dao.entity.DirectorEntity;
import hu.uni.eku.tzs.dao.entity.DirectorGenreEntity;
import hu.uni.eku.tzs.dao.entity.MovieEntity;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.model.DirectorGenre;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectorManagerImpl implements DirectorManager {

    private final String message = "Cannot find director with ID %s";

    private final DirectorRepository directorRepository;

    private static Director convertDirectorEntity2Model(DirectorEntity directorEntity) {
        return new Director(
                directorEntity.getId(),
                directorEntity.getFirstName(),
                directorEntity.getLastName()
        );
    }

    private static DirectorEntity convertDirectorModel2Entity(Director director) {
        return DirectorEntity.builder()
                .id(director.getId())
                .firstName(director.getFirstName())
                .lastName(director.getLastName())
                .build();
    }

    private static Movie convertMovieEntity2Model(MovieEntity movieEntity) {
        return new Movie(
                movieEntity.getId(),
                movieEntity.getName(),
                movieEntity.getYear(),
                movieEntity.getRank()
        );
    }

    private static DirectorGenre convertDirectorGenreEntity2Model(DirectorGenreEntity directorGenreEntity) {
        return new DirectorGenre(
                directorGenreEntity.getGenre(),
                directorGenreEntity.getProb(),
                new Director(
                        directorGenreEntity.getDirector().getId(),
                        directorGenreEntity.getDirector().getFirstName(),
                        directorGenreEntity.getDirector().getLastName()
                )
        );
    }

    @Override
    public Director record(Director director) throws DirectorAlreadyExistsException {
        if (directorRepository.findById(director.getId()).isPresent()) {
            throw new DirectorAlreadyExistsException();
        }
        DirectorEntity directorEntity = directorRepository.save(
                DirectorEntity.builder()
                        .id(director.getId())
                        .firstName(director.getFirstName())
                        .lastName(director.getLastName())
                        .build()
        );
        return convertDirectorEntity2Model(directorEntity);
    }

    @Override
    public Director readById(int id) throws DirectorNotFoundException {
        Optional<DirectorEntity> entity = directorRepository.findById(id);
        if (entity.isEmpty()) {
            throw new DirectorNotFoundException(String.format(message, id));
        }
        return convertDirectorEntity2Model(entity.get());
    }

    @Override
    public List<Director> readAll() {
        return directorRepository.findAll().stream().map(DirectorManagerImpl::convertDirectorEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public List<DirectorGenre> readGenre(int id) throws DirectorNotFoundException {
        Optional<DirectorEntity> entity = directorRepository.findById(id);
        if (entity.isEmpty()) {
            throw new DirectorNotFoundException(String.format(message, id));
        }
        return entity.get().getGenres().stream()
                .map(DirectorManagerImpl::convertDirectorGenreEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> readMovie(int id) throws DirectorNotFoundException {
        Optional<DirectorEntity> entity = directorRepository.findById(id);
        if (entity.isEmpty()) {
            throw new DirectorNotFoundException(String.format(message, id));
        }
        return entity.get().getMovies().stream()
                .map(DirectorManagerImpl::convertMovieEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Director modify(Director director) {
        DirectorEntity entity = convertDirectorModel2Entity(director);
        return convertDirectorEntity2Model(directorRepository.save(entity));
    }

    @Override
    public void delete(Director director) throws DirectorNotFoundException {
        directorRepository.delete(convertDirectorModel2Entity(director));
    }
}
