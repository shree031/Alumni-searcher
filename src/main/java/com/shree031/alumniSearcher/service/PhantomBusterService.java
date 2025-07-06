package com.shree031.alumniSearcher.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shree031.alumniSearcher.dto.AlumniResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PhantomBusterService {

    @Value("${phantombuster.api.key}")
    private String apiKey;

    @Value("${phantombuster.phantom.id}")
    private String phantomId;

    @Value("${phantombuster.output.url}")
    private String outputUrl;

    @Value("${phantombuster.session.cookie}")
    private String sessionCookie;

    @Value("${phantombuster.identity.id}")
    private String identityId;

    @Value("${phantombuster.identity.name}")
    private String identityName;

    private final RestTemplate restTemplate;

    public List<AlumniResponseDto> searchProfiles(String university, String designation, Integer passoutYear) {
        String query = university + " " + designation;
        if (passoutYear != null) {
            query += " " + passoutYear;
        }

        Map<String, Object> identity = Map.of(
                "id", identityId,
                "name", identityName,
                "sessionCookie", sessionCookie
        );

        Map<String, Object> argument = Map.of(
                "search", query,
                "sessionCookie", sessionCookie,
                "identityId", identityId,
                "identities", List.of(identity)
        );

        Map<String, Object> requestBody = Map.of(
                "id", phantomId,
                "argument", argument,
                "saveArgument", false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Phantombuster-Key-1", apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.phantombuster.com/api/v2/agents/launch", entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<?, ?> body = response.getBody();
            String containerId = (String) ((Map<?, ?>) body.get("data")).get("containerId");
            return fetchResults(containerId);
        } else {
            throw new RuntimeException("Failed to launch Phantom: " + response.getBody());
        }
    }

    private List<AlumniResponseDto> fetchResults(String containerId) {
        String fetchUrl = outputUrl + "?id=" + containerId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Phantombuster-Key-1", apiKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        ResponseEntity<String> response = restTemplate.exchange(fetchUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Map<String, Object>> results = mapper.readValue(response.getBody(),
                        new TypeReference<>() {
                        });
                return results.stream().map(this::mapToAlumniDto).toList();
            } catch (Exception e) {
                throw new RuntimeException("Error parsing Phantom output", e);
            }
        }

        throw new RuntimeException("Failed to fetch Phantom results");
    }

    private AlumniResponseDto mapToAlumniDto(Map<String, Object> result) {
        return AlumniResponseDto.builder()
                .name((String) result.get("name"))
                .currentRole((String) result.get("jobTitle"))
                .university((String) result.getOrDefault("school", ""))
                .location((String) result.get("location"))
                .linkedinHeadline((String) result.get("subtitle"))
                .passoutYear(null)
                .build();
    }
}
