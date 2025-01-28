package com.booleanuk.artists;

import com.booleanuk.responses.ArtistListResponse;
import com.booleanuk.responses.ArtistResponse;
import com.booleanuk.responses.ErrorResponse;
import com.booleanuk.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("artists")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping
    public ResponseEntity<ArtistListResponse> getAll() {
        ArtistListResponse artistListResponse = new ArtistListResponse();
        artistListResponse.set(this.artistRepository.findAll());
        return ResponseEntity.ok(artistListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOneArtist(@PathVariable int id) {
        Artist artist = this.artistRepository.findById(id).orElse(null);
        if (artist == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Artist not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ArtistResponse artistResponse = new ArtistResponse();
        artistResponse.set(artist);
        return ResponseEntity.ok(artistResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createAnArtist(@RequestBody Artist artist) {
        ArtistResponse artistResponse = new ArtistResponse();
        try {
            artistResponse.set(this.artistRepository.save(artist));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request - cannot create artist");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(artistResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateAnArtist(@PathVariable int id, @RequestBody Artist artist) {
        Artist artistToUpdate = null;
        try {
            artistToUpdate = this.artistRepository.findById(id).orElse(null);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request - could not successfully find artist");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        if (artistToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Artist not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        artistToUpdate.setName(artistToUpdate.getName());
        artistToUpdate.setStillPerforming(artist.isStillPerforming());
        artistToUpdate = this.artistRepository.save(artistToUpdate);
        ArtistResponse artistResponse = new ArtistResponse();
        artistResponse.set(artistToUpdate);
        return new ResponseEntity<>(artistResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteAnArtist(@PathVariable int id) {
        Artist artistToDelete = this.artistRepository.findById(id).orElse(null);
        if (artistToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Artist not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // The following line of code was causing the issue!
        //artistToDelete.setAlbums(null);
        this.artistRepository.delete(artistToDelete);
        ArtistResponse artistResponse = new ArtistResponse();
        artistResponse.set(artistToDelete);
        return ResponseEntity.ok(artistResponse);
    }
}
