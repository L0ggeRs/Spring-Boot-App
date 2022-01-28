package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.*;
import hu.uni.eku.tzs.model.*;

public class TestDataProviderController {
    public static final int ACTOR_ID = 0;
    public static final int DIRECTOR_ID = 0;
    public static final int MOVIE_ID = 0;
    private static final String ACTOR_FIRSTNAME = "John";
    private static final String ACTOR_LASTNAME = "Doe";
    private static final String FIRSTNAME = "Jon";
    private static final String LASTNAME = "Favreau";
    private static final char GENDER = 'M';
    private static final String ROLE = "Han Solo";
    private static final String MOVIE_NAME = "Money Heist";
    private static final int YEAR = 2017;
    private static final double RANK = 8.2;
    private static final String GENRE = "Short";
    private static final double PROB = 1.0;

    public static Actor getTestActor() {
        return new Actor(ACTOR_ID, ACTOR_FIRSTNAME, ACTOR_LASTNAME, GENDER);
    }

    public static ActorDto getTestActorDto() {
        return ActorDto.builder()
                .id(ACTOR_ID)
                .firstName(ACTOR_FIRSTNAME)
                .lastName(ACTOR_LASTNAME)
                .gender(GENDER)
                .build();
    }

    public static Movie getTestMovie() {
        return new Movie(ACTOR_ID, MOVIE_NAME, YEAR, RANK);
    }

    public static MovieDto getTestMovieDto() {
        return MovieDto.builder()
                .id(DIRECTOR_ID)
                .name(MOVIE_NAME)
                .year(YEAR)
                .rank(RANK)
                .build();
    }

    public static Role getTestRole() {
        return new Role(ROLE, getTestActor(), getTestMovie());
    }

    public static RoleDto getTestRoleDto() {
        return RoleDto.builder()
                .role(ROLE)
                .movie(getTestMovieDto())
                .actor(getTestActorDto())
                .build();
    }

    public static Director getTestDirector() {
        return new Director(DIRECTOR_ID, FIRSTNAME, LASTNAME);
    }

    public static DirectorDto getTestDirectorDto() {
        return DirectorDto.builder()
                .id(DIRECTOR_ID)
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .build();
    }

    public static DirectorGenre getTestDirectorGenre() {
        return new DirectorGenre(GENRE, PROB, getTestDirector());
    }

    public static DirectorGenreDto getTestDirectorGenreDto() {
        return DirectorGenreDto.builder()
                .genre(GENRE)
                .prob(PROB)
                .director(getTestDirectorDto())
                .build();
    }

    public static MovieGenre getMovieGenre() {
        return new MovieGenre(GENRE, getTestMovie());
    }

    public static MovieGenreDto getMovieGenreDto() {
        return MovieGenreDto.builder()
                .genre(GENRE)
                .movie(getTestMovieDto())
                .build();
    }
}
