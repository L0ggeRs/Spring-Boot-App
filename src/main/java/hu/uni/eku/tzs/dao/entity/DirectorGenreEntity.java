package hu.uni.eku.tzs.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Directors Genres")
@Table(name = "directors_genres")
public class DirectorGenreEntity {

    @Id
    private String genre;

    @Column(name = "prob")
    private Double prob;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private DirectorEntity director;
}