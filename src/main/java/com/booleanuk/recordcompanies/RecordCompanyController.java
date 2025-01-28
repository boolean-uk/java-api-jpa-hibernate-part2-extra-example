package com.booleanuk.recordcompanies;

import com.booleanuk.albums.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("recordcompanies")
public class RecordCompanyController {
    @Autowired
    private RecordCompanyRepository recordCompanyRepository;

    @GetMapping
    public ResponseEntity<List<RecordCompany>> getAllRecordCompanies() {
        return ResponseEntity.ok(this.recordCompanyRepository.findAll());
    }

    private RecordCompany getARecordCompany(int id) {
        return this.recordCompanyRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record company with that id not found")
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<RecordCompany> getOneRecordCompany(@PathVariable int id) {
        return ResponseEntity.ok(this.getARecordCompany(id));
    }

    @PostMapping
    public ResponseEntity<RecordCompany> createARecordCompany(@RequestBody RecordCompany recordCompany) {
        List<Album> albumList = new ArrayList<>();
        recordCompany.setAlbums(albumList);
        return new ResponseEntity<RecordCompany>(this.recordCompanyRepository.save(recordCompany), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<RecordCompany> updateARecordCompany(@PathVariable int id, @RequestBody RecordCompany recordCompany) {
        RecordCompany recordCompanyToUpdate = this.getARecordCompany(id);
        recordCompanyToUpdate.setName(recordCompany.getName());
        recordCompanyToUpdate.setLocation(recordCompany.getLocation());
        return new ResponseEntity<RecordCompany>(this.recordCompanyRepository.save(recordCompanyToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RecordCompany> deleteARecordCompany(@PathVariable int id) {
        RecordCompany recordCompanyToDelete = this.getARecordCompany(id);
        this.recordCompanyRepository.delete(recordCompanyToDelete);
        List<Album> albumList = new ArrayList<>();
        recordCompanyToDelete.setAlbums(albumList);
        return ResponseEntity.ok(recordCompanyToDelete);
    }
}
