package by.smirnov.guitarstoreproject.model;

import by.smirnov.guitarstoreproject.model.enums.MusicGenre;
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
@Entity
@Table(name = "genres")
@Cacheable("genres")
public class Genre implements ObjectEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "music_genre")
    @Enumerated(EnumType.STRING)
    private MusicGenre musicGenre;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @ManyToMany
    @JoinTable(name = "l_genres_guitars",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "guitar_id")
    )
    private List<Guitar> byGenreGuitars;
}
