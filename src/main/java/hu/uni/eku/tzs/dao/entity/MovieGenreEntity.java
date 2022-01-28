package hu.uni.eku.tzs.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Movies Genres")
@Table(name = "movies_genres")
public class MovieGenreEntity {

    @Id
    private String genre;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;
}
