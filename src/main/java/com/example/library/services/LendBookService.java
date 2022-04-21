package com.example.library.services;

import com.example.library.dtos.ReturnDto;
import com.example.library.exceptions.ErrorException;
import com.example.library.models.*;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.ExchangeRateRepository;
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

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

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

    public LendBook returnBook(ReturnDto returnDto) throws ErrorException{
        UserAccount userAccount = userRepository.findById(returnDto.getUserId()).orElse(null);
        if (userAccount == null) {
            throw new ErrorException("Invalid user!");
        }
        if (userAccount.getUserType().name().equals("STAFF")){
            throw new ErrorException("Unauthorized!");
        }
        LendBook lendBook = findById(returnDto.getLendBookId());
        if (lendBook == null) {
            throw new ErrorException("Not found");
        }
        if (lendBook.getReturnStatus().equals(ReturnStatus.RETURNED)){
            throw new ErrorException("Invalid operation - the book has been returned");
        }
        Book book = bookRepository.getById(lendBook.getBookId());
        if (!returnDto.getUserId().equals(lendBook.getUserId())){
            throw new ErrorException("Unauthorized!");
        }
        LocalDateTime returnDate =  LocalDateTime.now();
        lendBook.setReturnDate(returnDate);
        Long daysDiff = ChronoUnit.DAYS.between(lendBook.getExpectedReturnDate().toLocalDate(), returnDate.toLocalDate());
        System.out.println("Days difference = " + daysDiff);
        if (daysDiff > 0){
            ExchangeRate rate = exchangeRateRepository.findByCurrency(lendBook.getCurrency());
            Double fee = book.getPenaltyFee() * daysDiff * rate.getRate();
            lendBook.setFee(fee);
            lendBookRepository.save(lendBook);
            lendBook.setPenaltyFee(lendBook.toString());
            lendBookRepository.save(lendBook);
        }
        book.setNoOfCopies(book.getNoOfCopies() + lendBook.getNoOfCopies());
        book.setNoOfCopiesBorrowed(book.getNoOfCopiesBorrowed() - lendBook.getNoOfCopies());
        bookRepository.save(book);
        lendBook.setReturnStatus(ReturnStatus.RETURNED);
        return lendBookRepository.save(lendBook);
    }
}
