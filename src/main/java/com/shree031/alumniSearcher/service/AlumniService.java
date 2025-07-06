package com.shree031.alumniSearcher.service;

import com.shree031.alumniSearcher.Repository.AlumniRepository;
import com.shree031.alumniSearcher.dto.AlumniResponseDto;
import com.shree031.alumniSearcher.dto.AlumniSearchRequest;
import com.shree031.alumniSearcher.entity.Alumni;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AlumniService {

    private final AlumniRepository alumniRepository;
    private final PhantomBusterService phantomBusterService;

    /*public List<AlumniResponseDto> searchAndSaveAlumni(AlumniSearchRequest request) {
        List<AlumniResponseDto> results = phantomBusterService.searchProfiles(
                request.getUniversity(), request.getDesignation(), request.getPassoutYear()
        );

        List<Alumni> alumniEntities = results.stream()
                .map(dto -> Alumni.builder()
                        .name(dto.getName())
                        .currentRole(dto.getCurrentRole())
                        .university(dto.getUniversity())
                        .location(dto.getLocation())
                        .linkedinHeadline(dto.getLinkedinHeadline())
                        .passoutYear(dto.getPassoutYear())
                        .build())
                .toList();

        alumniRepository.saveAll(alumniEntities);
        return results;
    }*/

    public List<AlumniResponseDto> getAllAlumni() {
        return alumniRepository.findAll().stream().map(a -> AlumniResponseDto.builder()
                .name(a.getName())
                .currentRole(a.getCurrentRole())
                .university(a.getUniversity())
                .location(a.getLocation())
                .linkedinHeadline(a.getLinkedinHeadline())
                .passoutYear(a.getPassoutYear())
                .build()).toList();
    }

    public List<AlumniResponseDto> searchAndSaveAlumni(AlumniSearchRequest request) {
        List<AlumniResponseDto> results = phantomBusterService.searchProfiles(
                request.getUniversity(), request.getDesignation(), request.getPassoutYear()
        );

        List<Alumni> alumniEntities = results.stream().map(dto -> Alumni.builder()
                .name(dto.getName())
                .currentRole(dto.getCurrentRole())
                .university(dto.getUniversity())
                .location(dto.getLocation())
                .linkedinHeadline(dto.getLinkedinHeadline())
                .passoutYear(dto.getPassoutYear())
                .build()).toList();
        alumniRepository.saveAll(alumniEntities);

        return results;
    }

}
