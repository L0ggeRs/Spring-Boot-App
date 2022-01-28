package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.entity.*;
import hu.uni.eku.tzs.model.*;

import java.util.List;

public class TestDataProviderService {
    public static final int UNKNOWN_ID = 0;
    public static final int THE_ROCK_ID = 1;
    public static final int MONEY_HEIST_ID = 1;
    public static final int JON_ID = 1;
    private static final String MOVIE_NAME = "Money Heist";
    private static final int YEAR = 2017;
    private static final Double RANK = 8.2;
    private static final String ACTOR_FIRSTNAME = "Dwayne";
    private static final String ACTOR_LASTNAME = "Johnson";
    private static final String DIRECTOR_FIRSTNAME = "Jon";
    private static final String DIRECTOR_LASTNAME = "Favreau";
    private static final char GENDER = 'M';

    public static Actor getRock() {
        return new Actor(THE_ROCK_ID, ACTOR_FIRSTNAME, ACTOR_LASTNAME, GENDER);
    }

    public static ActorEntity getRockEntity() {
        return ActorEntity.builder()
                .id(THE_ROCK_ID)
                .firstName(ACTOR_FIRSTNAME)
                .lastName(ACTOR_LASTNAME)
                .gender(GENDER)
                .build();
    }

    public static ActorEntity getRockRolesEntity() {
        return ActorEntity.builder()
                .id(THE_ROCK_ID)
                .firstName(ACTOR_FIRSTNAME)
                .lastName(ACTOR_LASTNAME)
                .gender(GENDER)
                .roles(getRolesEntities())
                .build();
    }

    public static Director getJon() {
        return new Director(JON_ID, DIRECTOR_FIRSTNAME, DIRECTOR_LASTNAME);
    }

    public static DirectorEntity getJonEntity() {
        return DirectorEntity.builder()
                .id(JON_ID)
                .firstName(DIRECTOR_FIRSTNAME)
                .lastName(DIRECTOR_LASTNAME)
                .build();
    }

    public static DirectorEntity getJonMoviesEntity() {
        return DirectorEntity.builder()
                .id(JON_ID)
                .firstName(DIRECTOR_FIRSTNAME)
                .lastName(DIRECTOR_LASTNAME)
                .movies(getMoviesEntities())
                .build();
    }

    public static DirectorEntity getJonGenresEntity() {
        return DirectorEntity.builder()
                .id(JON_ID)
                .firstName(DIRECTOR_FIRSTNAME)
                .lastName(DIRECTOR_LASTNAME)
                .genres(getDirectorGenresEntities())
                .build();
    }

    public static Movie getMoneyHeist() {
        return new Movie(MONEY_HEIST_ID, MOVIE_NAME, YEAR, RANK);
    }

    public static MovieEntity getMoneyHeistEntity() {
        return MovieEntity.builder()
                .id(MONEY_HEIST_ID)
                .name(MOVIE_NAME)
                .year(YEAR)
                .rank(RANK)
                .build();
    }

    public static MovieEntity getMoviesRoleEntity() {
        return MovieEntity.builder()
                .id(MONEY_HEIST_ID)
                .name(MOVIE_NAME)
                .year(YEAR)
                .rank(RANK)
                .roles(getRolesEntities())
                .build();
    }

    public static MovieEntity getMoviesDirectorEntity() {
        return MovieEntity.builder()
                .id(MONEY_HEIST_ID)
                .name(MOVIE_NAME)
                .year(YEAR)
                .rank(RANK)
                .directors(getDirectorsEntities())
                .build();
    }

    public static RoleEntity getRole() {
        return RoleEntity.builder()
                .role("Han Solo")
                .movie(getMoneyHeistEntity())
                .actor(getRockEntity())
                .build();
    }


    public static List<Role> getRoles() {
        return List.of(
                new Role("Han Solo", getRock(), getMoneyHeist()),
                new Role("Himself", getRock(), getMoneyHeist()),
                new Role("Group Leader", getRock(), getMoneyHeist())
        );
    }

    public static List<RoleEntity> getRolesEntities() {
        return List.of(
                new RoleEntity("Han Solo", getMoneyHeistEntity(), getRockEntity()),
                new RoleEntity("Himself", getMoneyHeistEntity(), getRockEntity()),
                new RoleEntity("Group Leader", getMoneyHeistEntity(), getRockEntity())
        );
    }


    public static List<DirectorGenreEntity> getDirectorGenresEntities() {
        return List.of(
                new DirectorGenreEntity("Drama", 1.0, getJonEntity())
        );
    }

    public static List<Movie> getMovies() {
        return List.of(
                new Movie(MONEY_HEIST_ID, MOVIE_NAME, YEAR, RANK)
        );
    }

    public static List<MovieEntity> getMoviesEntities() {
        return List.of(
                new MovieEntity(MONEY_HEIST_ID, MOVIE_NAME, YEAR, RANK, List.of(getMovieGenre()), List.of(getRole()), List.of(getRockEntity()), List.of(getJonEntity()))
        );
    }

    public static MovieGenreEntity getMovieGenre() {
        return MovieGenreEntity.builder()
                .genre("Short")
                .movie(getMoneyHeistEntity())
                .build();
    }

    public static List<DirectorGenre> getGenres() {
        return List.of(
                new DirectorGenre("Drama", 1.0, getJon())
        );
    }

    public static MovieEntity getMovieGenresEntity() {
        return MovieEntity.builder()
                .id(MONEY_HEIST_ID)
                .name(MOVIE_NAME)
                .year(YEAR)
                .rank(RANK)
                .genres(getMovieGenreEntities())
                .build();
    }

    public static List<MovieGenre> getMoviesGenres() {
        return List.of(
                new MovieGenre("Drama", getMoneyHeist())
        );
    }

    public static List<MovieGenreEntity> getMovieGenreEntities() {
        return List.of(
                new MovieGenreEntity("Drama",getMoneyHeistEntity())
        );
    }

    private static List<DirectorEntity> getDirectorsEntities() {
        return List.of(
                new DirectorEntity(JON_ID, DIRECTOR_FIRSTNAME, DIRECTOR_LASTNAME, List.of(getMoneyHeistEntity()), getDirectorsGenresEntities())
        );
    }

    public static List<Director> getDirectors() {
        return List.of(
                new Director(JON_ID, DIRECTOR_FIRSTNAME, DIRECTOR_LASTNAME)
        );
    }

    public static List<DirectorGenreEntity> getDirectorsGenresEntities() {
        return List.of(
                new DirectorGenreEntity("Drama", 1.0, getJonEntity())
        );
    }
}
