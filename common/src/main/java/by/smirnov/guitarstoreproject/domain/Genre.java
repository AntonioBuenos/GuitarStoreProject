package by.smirnov.guitarstoreproject.domain;

import by.smirnov.guitarstoreproject.domain.enums.MusicGenre;
import lombok.*;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"byGenreGuitars"})
@EqualsAndHashCode(exclude = {"byGenreGuitars"})
@Entity
@Table(name = "genres")
@Cacheable("genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "music_genre")
    @Enumerated(EnumType.STRING)
    private MusicGenre musicGenre;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "termination_date")
    private Timestamp terminationDate;

    @ManyToMany
    @JoinTable(name = "l_genres_guitars",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "guitar_id")
    )
    private List<Guitar> byGenreGuitars;
}
