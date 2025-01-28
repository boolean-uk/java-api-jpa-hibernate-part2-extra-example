package com.booleanuk.recordcompanies;

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
@Table(name = "record_companies")
public class RecordCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String location;

    @OneToMany(mappedBy = "recordCompany", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"recordCompany"})
    private List<Album> albums;

    public RecordCompany(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public RecordCompany(int id) {
        this.id = id;
    }
}
