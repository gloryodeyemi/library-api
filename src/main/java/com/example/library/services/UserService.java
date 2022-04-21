package com.example.library.services;

import com.example.library.dtos.UserDto;
import com.example.library.exceptions.ErrorException;
import com.example.library.models.Author;
import com.example.library.models.UserAccount;
import com.example.library.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserAccount save(UserDto userDto) throws ErrorException {
        // checking to see if user already exists
        if (userRepository.existsByEmailAddress(userDto.getEmailAddress())) {
            throw new ErrorException("Email address already exists!");
        }
        UserAccount user = new UserAccount();
        BeanUtils.copyProperties(userDto, user);
        userRepository.save(user);
        // encode the password
        String password = passwordEncoder.encode(userDto.getPassword());
        // check if password matches
        if (!passwordEncoder.matches(userDto.getConfirmPassword(), password)){
            throw new ErrorException("Password does not match!");
        }
        user.setPassword(password);
        user.setUserCode(user.toString());
        return userRepository.save(user);
    }

    public List<UserAccount> findAll() {
        return userRepository.findAll();
    }

    public UserAccount findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public UserAccount findByUserCode(String userCode) {
        return userRepository.findUserAccountByUserCode(userCode);
    }

    public void userValidation(Long userId) throws ErrorException{
        UserAccount userAccount = findById(userId);
        if (userAccount == null) {
            throw new ErrorException("Invalid user!");
        }
        if (userAccount.getUserType().name().equals("CUSTOMER")){
            throw new ErrorException("Unauthorized!");
        }
    }
}
