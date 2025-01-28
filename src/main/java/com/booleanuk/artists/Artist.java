package com.booleanuk.artists;

import com.booleanuk.albums.Album;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private boolean stillPerforming;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"artist"})
    private List<Album> albums;

    public Artist(String name, boolean stillPerforming) {
        this.name = name;
        this.stillPerforming = stillPerforming;
    }

    public Artist(int id) {
        this.id = id;
    }
}
