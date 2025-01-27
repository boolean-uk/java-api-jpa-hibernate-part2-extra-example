package com.booleanuk.albums;


import com.booleanuk.artists.Artist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Column
    private int year;
    @Column
    private int rating;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    @JsonIgnoreProperties("albums")
    private Artist artist;
//    private RecordCompany recordCompany;

    public Album(String title, int year, int rating) {
        this.title = title;
        this.year = year;
        this.rating = rating;
    }
}
