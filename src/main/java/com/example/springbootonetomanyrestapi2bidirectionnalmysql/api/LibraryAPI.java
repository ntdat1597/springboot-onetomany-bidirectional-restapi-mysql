package com.example.springbootonetomanyrestapi2bidirectionnalmysql.api;

import com.example.springbootonetomanyrestapi2bidirectionnalmysql.jpa.Book;
import com.example.springbootonetomanyrestapi2bidirectionnalmysql.jpa.Library;
import com.example.springbootonetomanyrestapi2bidirectionnalmysql.repository.BookRepository;
import com.example.springbootonetomanyrestapi2bidirectionnalmysql.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/libraries")
@RequiredArgsConstructor
public class LibraryAPI {
    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    @GetMapping //read data
    public ResponseEntity<Page<Library>> getAll (Pageable pageable) {
        return ResponseEntity.ok (libraryRepository.findAll (pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Library> getById (@PathVariable Integer id) {
        Optional<Library> optionalLibrary = libraryRepository.findById (id);
        if (!optionalLibrary.isPresent ()) {
            return ResponseEntity.unprocessableEntity ().build ();
        }

        return ResponseEntity.ok (libraryRepository.getOne (id));
    }

    @PostMapping
    public ResponseEntity<Library> createLibrary (@Valid @RequestBody Library library) {
        Library librarySaved = libraryRepository.save (library);
        //check header of service
        URI location = ServletUriComponentsBuilder.fromCurrentRequest ().path ("/{id}").buildAndExpand (librarySaved.getId ()).toUri ();
        return ResponseEntity.created (location).body (librarySaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Library> updateLibrary (@PathVariable Integer id, @Valid @RequestBody Library library) {
        Optional<Library> optionalLibrary = libraryRepository.findById (id);
        if (!optionalLibrary.isPresent ()) {
            return ResponseEntity.unprocessableEntity ().build ();
        }
        library.setId (optionalLibrary.get ().getId ());
        libraryRepository.save (library);
        return ResponseEntity.noContent ().build ();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Library> deleteLibrary (@PathVariable Integer id) {
        Optional<Library> optionalLibrary = libraryRepository.findById (id);
        if (!optionalLibrary.isPresent ()) {
            return ResponseEntity.unprocessableEntity ().build ();
        }
        /*libraryRepository.delete(optionalLibrary.get());*/
        deleteByLibraryCustomTransactional (optionalLibrary.get ());
        return ResponseEntity.noContent ().build ();

    }
    // get book id  custom from libraryRepository
 /*   @GetMapping("library/{libraryId}")
    public ResponseEntity<Page<Book>> getByLibraryId(@PathVariable Integer libraryId,Pageable pageable){
        return ResponseEntity.ok (libraryRepository.findByLibraryId (libraryId,pageable));
    }
*/
    @Transactional
    public void deleteByLibraryCustomTransactional (Library library) {
        libraryRepository.deleteByLibraryId (library.getId ());
        libraryRepository.delete (library);
    }


}
