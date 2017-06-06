package com.asciipic.search.repositories;


import com.asciipic.search.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select count(id) from com.asciipic.search.models.Tag where name = ?1")
    int findNumberByName(String name);

    @Query("select id from com.asciipic.search.models.Tag t where t.name = ?1")
    long findIdByName(String name);
}