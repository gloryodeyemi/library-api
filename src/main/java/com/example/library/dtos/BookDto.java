package com.example.library.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BookDto {
    private Long id;
    private String title;
//    private Long authorId;
    private List<Long> authorIds;
    private Long publisherId;
    private String year;
    private Long noOfCopies;
    private Long noOfDaysToBeBorrowed;
    private Double penaltyFee;
}
