package by.smirnov.guitarstoreproject.model;

import by.smirnov.guitarstoreproject.model.enums.MusicGenre;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genres")
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
}
