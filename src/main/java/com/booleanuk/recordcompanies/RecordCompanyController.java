package com.booleanuk.recordcompanies;

import com.booleanuk.responses.ErrorResponse;
import com.booleanuk.responses.RecordCompanyListResponse;
import com.booleanuk.responses.RecordCompanyResponse;
import com.booleanuk.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("recordcompanies")
public class RecordCompanyController {
    @Autowired
    private RecordCompanyRepository recordCompanyRepository;

    @GetMapping
    public ResponseEntity<RecordCompanyListResponse> getAllRecordCompanies() {
        RecordCompanyListResponse recordCompanyListResponse = new RecordCompanyListResponse();
        recordCompanyListResponse.set(this.recordCompanyRepository.findAll());
        return ResponseEntity.ok(recordCompanyListResponse);
    }


    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOneRecordCompany(@PathVariable int id) {
        RecordCompany company = this.recordCompanyRepository.findById(id).orElse(null);
        if (company == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        RecordCompanyResponse recordCompanyResponse = new RecordCompanyResponse();
        recordCompanyResponse.set(company);
        return ResponseEntity.ok(recordCompanyResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createARecordCompany(@RequestBody RecordCompany recordCompany) {
        RecordCompanyResponse recordCompanyResponse = new RecordCompanyResponse();
        try {
            recordCompanyResponse.set(this.recordCompanyRepository.save(recordCompany));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(recordCompanyResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateARecordCompany(@PathVariable int id, @RequestBody RecordCompany recordCompany) {
        RecordCompany companyToUpdate = null;
        try {
            companyToUpdate = this.recordCompanyRepository.findById(id).orElse(null);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        if (companyToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        companyToUpdate.setName(recordCompany.getName());
        companyToUpdate.setLocation(recordCompany.getLocation());
        companyToUpdate = this.recordCompanyRepository.save(companyToUpdate);
        RecordCompanyResponse recordCompanyResponse = new RecordCompanyResponse();
        recordCompanyResponse.set(companyToUpdate);
        return new ResponseEntity<>(recordCompanyResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteARecordCompany(@PathVariable int id) {
        RecordCompany companyToDelete = this.recordCompanyRepository.findById(id).orElse(null);
        if (companyToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.recordCompanyRepository.delete(companyToDelete);
        RecordCompanyResponse recordCompanyResponse = new RecordCompanyResponse();
        recordCompanyResponse.set(companyToDelete);
        return ResponseEntity.ok(recordCompanyResponse);
    }
}
