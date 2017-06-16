package com.asciipic.login.repositories;

import com.asciipic.login.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select count(id) from com.asciipic.login.models.User where email = ?1")
    int findNumberOfEmails(String email);

    User findByEmailAndPassword(String email, String password);
}
