package com.example.springbootonetomanyrestapi2bidirectionnalmysql.repository;

import com.example.springbootonetomanyrestapi2bidirectionnalmysql.jpa.Library;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Integer> {
    List<Library> findAllByStatus(int status);

    @Query("SELECT c FROM Library c WHERE  id = ?1")
    List<Library> getByLibraryId(int id);

    @Query("SELECT c FROM Library c WHERE status = 1 OR status = 2")
    List<Library> getAllLibraryStatus();

    @Query("SELECT c FROM Library c WHERE status = 1 OR status = 2")
    Page<Library> findPaginateLibraryStatus(Pageable pageable);

    @Query("SELECT c FROM Library c WHERE name = ?1 AND id = ?2")
    Library findByLibraryName(String name);
}
