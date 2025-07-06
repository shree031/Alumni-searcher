package com.shree031.alumniSearcher.dto;

import lombok.Data;

@Data
public class AlumniSearchRequest {
    private String university;
    private String designation;
    private Integer passoutYear; // Optional
}
