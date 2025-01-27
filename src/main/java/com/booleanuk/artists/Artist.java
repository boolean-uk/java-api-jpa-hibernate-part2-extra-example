package com.booleanuk.artists;

import com.booleanuk.albums.Album;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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

    @OneToMany(mappedBy = "artist")
    @JsonIncludeProperties({"title", "year"})
    private List<Album> albums;

    public Artist(String name, boolean stillPerforming) {
        this.name = name;
        this.stillPerforming = stillPerforming;
    }
}
