package com.sedefbas.learnTest.repository;

import com.sedefbas.learnTest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByIdentificationNumber(String identificationNumber);
}
