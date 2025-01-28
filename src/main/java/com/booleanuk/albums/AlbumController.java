package com.booleanuk.albums;

import com.booleanuk.artists.Artist;
import com.booleanuk.artists.ArtistRepository;
import com.booleanuk.recordcompanies.RecordCompany;
import com.booleanuk.recordcompanies.RecordCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("albums")
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private RecordCompanyRepository recordCompanyRepository;


    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        return ResponseEntity.ok(this.albumRepository.findAll());
    }

    private Album getAnAlbum(int id) {
        return this.albumRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No album with that id found")
        );
    }

    private Artist getAnArtist(int id) {
        return this.artistRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No artist with that id found")
        );
    }

    private RecordCompany getARecordCompany(int id) {
        return this.recordCompanyRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No record company with that id found")
        );
    }

    @PostMapping
    public ResponseEntity<Album> createAnAlbum(@RequestBody Album album) {
        Artist artist = this.getAnArtist(album.getArtist().getId());
        RecordCompany recordCompany = this.getARecordCompany(album.getRecordCompany().getId());
        album.setArtist(artist);
        album.setRecordCompany(recordCompany);
        return new ResponseEntity<Album>(this.albumRepository.save(album), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Album> getOne(@PathVariable int id) {
        return ResponseEntity.ok(this.getAnAlbum(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable int id, @RequestBody Album album) {
        Album albumToUpdate = this.getAnAlbum(id);
        albumToUpdate.setTitle(album.getTitle());
        albumToUpdate.setYear(album.getYear());
        albumToUpdate.setRating(album.getYear());
        albumToUpdate.setArtist(this.getAnArtist(album.getArtist().getId()));
        albumToUpdate.setRecordCompany(this.getARecordCompany(album.getRecordCompany().getId()));
        return new ResponseEntity<Album>(this.albumRepository.save(albumToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Album> deleteAnAlbum(@PathVariable int id) {
        Album albumToDelete = this.getAnAlbum(id);
        this.albumRepository.delete(albumToDelete);
        return ResponseEntity.ok(albumToDelete);
    }
}
