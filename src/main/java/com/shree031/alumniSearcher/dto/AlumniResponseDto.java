package com.shree031.alumniSearcher.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlumniResponseDto {
    private String name;
    private String currentRole;
    private String university;
    private String location;
    private String linkedinHeadline;
    private Integer passoutYear;
}
