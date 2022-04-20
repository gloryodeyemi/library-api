package com.example.library.repositories;

import com.example.library.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findUserAccountByUserCode(String userCode);
    Boolean existsByEmailAddress(String emailAddress);
}
