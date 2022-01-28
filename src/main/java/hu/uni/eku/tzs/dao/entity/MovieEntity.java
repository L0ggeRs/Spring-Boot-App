package hu.uni.eku.tzs.dao.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Movies")
@Table(name = "movies")
public class MovieEntity {

    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private Integer year;

    @Column(name = "rank")
    private Double rank;

    @OneToMany(
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MovieGenreEntity> genres = new ArrayList<>();

    @OneToMany(
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RoleEntity> roles = new ArrayList<>();

    @ManyToMany(mappedBy = "movies")
    private List<ActorEntity> actors = new ArrayList<>();

    @ManyToMany(mappedBy = "movies")
    private List<DirectorEntity> directors = new ArrayList<>();
}
