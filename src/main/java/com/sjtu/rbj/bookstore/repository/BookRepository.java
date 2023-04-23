package com.sjtu.rbj.bookstore.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sjtu.rbj.bookstore.entity.Book;

public interface BookRepository extends JpaRepository<Book, UUID> {
    // TODO
    @Query(value = "select * from book limit ?1", nativeQuery = true)
    List<Book> findTopX(Integer x);
    List<Book> findByUuid(UUID uuid);
}
