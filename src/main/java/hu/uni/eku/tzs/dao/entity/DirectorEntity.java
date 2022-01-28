package hu.uni.eku.tzs.dao.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Directors")
@Table(name = "directors")
public class DirectorEntity {

    @Id
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(cascade = {
            CascadeType.ALL
    })
    @JoinTable(name = "movies_directors",
            joinColumns = @JoinColumn(name = "director_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<MovieEntity> movies = new ArrayList<>();

    @OneToMany(
            mappedBy = "director",
            cascade = CascadeType.ALL
    )
    private List<DirectorGenreEntity> genres = new ArrayList<>();
}
