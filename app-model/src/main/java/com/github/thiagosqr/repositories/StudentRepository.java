package com.github.thiagosqr.repositories;

import com.github.thiagosqr.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by thiago-rs on 3/3/15.
 */
public interface StudentRepository extends PagingAndSortingRepository<Student, Integer> {

    Page<Student> queryFirst10ByName(String name, Pageable pageable);

}
