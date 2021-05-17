package com.example.springbootonetomanyrestapi2bidirectionnalmysql.service;


import com.example.springbootonetomanyrestapi2bidirectionnalmysql.jpa.Library;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LibraryService {
    List<Library> listLibraries();
    List<Library> listLibrariesByStatus(int status);
    Library getLibraryById(int id);
    boolean saveLibrary(Library library);
    boolean deleteLibrary(int id);
    boolean updateLibrary(Library library);
    boolean checkLibraryName(String name);
    Page<Library> findPaginated(int pageNo, int pageSize);
}
