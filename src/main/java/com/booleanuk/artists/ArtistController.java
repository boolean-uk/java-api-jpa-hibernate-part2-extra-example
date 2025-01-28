package com.booleanuk.artists;

import com.booleanuk.albums.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("artists")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    private Artist getAnArtist(int id) {
        return this.artistRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist with that id not found")
        );
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAll() {
        return ResponseEntity.ok(this.artistRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Artist> getOneArtist(@PathVariable int id) {
        return ResponseEntity.ok(this.getAnArtist(id));
    }

    @PostMapping
    public ResponseEntity<Artist> createAnArtist(@RequestBody Artist artist) {
        List<Album> albumList = new ArrayList<>();
        artist.setAlbums(albumList);
        return new ResponseEntity<Artist>(this.artistRepository.save(artist), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Artist> updateAnArtist(@PathVariable int id, @RequestBody Artist artist) {
        Artist artistToUpdate = this.getAnArtist(id);
        artistToUpdate.setName(artist.getName());
        artistToUpdate.setStillPerforming(artist.isStillPerforming());
        return new ResponseEntity<Artist>(this.artistRepository.save(artistToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Artist> deleteAnArtist(@PathVariable int id) {
        Artist artistToDelete = this.getAnArtist(id);

        this.artistRepository.delete(artistToDelete);

        List<Album> albumList = new ArrayList<>();
        artistToDelete.setAlbums(albumList);
        return ResponseEntity.ok(artistToDelete);
    }
}
