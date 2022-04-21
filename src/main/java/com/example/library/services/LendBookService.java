package com.example.library.services;

import com.example.library.exceptions.ErrorException;
import com.example.library.models.*;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.LendBookRepository;
import com.example.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LendBookService {
    @Autowired
    LendBookRepository lendBookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    public List<LendBook> findAll() {
        return lendBookRepository.findAll();
    }

    public LendBook findById(Long lendId) {
        return lendBookRepository.findById(lendId).orElse(null);
    }

    public LendBook lendBook(LendBook object) throws ErrorException {
        UserAccount userAccount = userRepository.findById(object.getUserId()).orElse(null);
        if (userAccount == null) {
            throw new ErrorException("Invalid user!");
        }
        if (userAccount.getUserType().name().equals("STAFF")){
            throw new ErrorException("Unauthorized!");
        }
        Book book = bookRepository.getById(object.getBookId());
        Long copies = book.getNoOfCopies();
        if (object.getNoOfCopies() > copies){
            if (copies == 1){
                throw new ErrorException("Only " + copies+ " copy available!");
            } else  if (copies == 0){
                throw new ErrorException("No copies available!");
            } else {
                throw new ErrorException("Only " + copies + " copies available!");
            }
        }
        book.setNoOfCopies(copies - object.getNoOfCopies());
        book.setNoOfCopiesBorrowed(book.getNoOfCopiesBorrowed() + object.getNoOfCopies());
        bookRepository.save(book);
        LocalDateTime date = ChronoUnit.DAYS.addTo(object.getPickUpDate().toLocalDate(), book.getNoOfDaysToBeBorrowed()).atStartOfDay();
        object.setExpectedReturnDate(date);
        object.setReturnStatus(ReturnStatus.PENDING);
        object.setPenaltyFee(object.toString());
        return lendBookRepository.save(object);
    }

    public LendBook returnBook(Long lendBookId){
        LendBook lendBook = findById(lendBookId);
        return null;
    }
}
