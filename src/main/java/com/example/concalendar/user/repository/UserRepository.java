package com.example.concalendar.user.repository;

import com.example.concalendar.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryCustom {
    public Optional<User> findByUserEmail(String userEmail);

}
