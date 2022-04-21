package com.example.library.repositories;

import com.example.library.models.LendBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LendBookRepository extends JpaRepository<LendBook, Long> {
}
