package org.example.interview405.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.interview405.dto.CityDto;
import org.example.interview405.dto.PagedResponse;
import org.example.interview405.mapper.CityMapper;
import org.example.interview405.model.City;
import org.example.interview405.service.CityService;
import org.example.interview405.service.CountryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@Slf4j
public class CityController {
    
    private final CityService cityService;
    private final CityMapper cityMapper;

    @GetMapping("/cities")
    public ResponseEntity<PagedResponse<CityDto>> findAll(
            @RequestParam final Integer pageNumber,
            @RequestParam final Integer size
    ) {
        Page<City> page = cityService.findAll(pageNumber, size);
        PagedResponse<CityDto> response =
                new PagedResponse<>(
                        page.stream().map(cityMapper::toDto).toList(),
                        pageNumber,
                        size,
                        page.getTotalElements(),
                        page.getTotalPages(),
                        page.isLast()
                );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<CityDto> findById(@PathVariable final Long id) {
        log.info("Find by id {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(cityMapper.toDto(cityService.findById(id)));
    }

    @GetMapping("/countries/{countryId}/cities")
    public ResponseEntity<PagedResponse<CityDto>> findCitiesByCountryId(
            @PathVariable final Long countryId,
            @RequestParam final Integer pageNumber,
            @RequestParam final Integer size) {
        log.info("Find cities by country id {}", countryId);
        Page<City> page = cityService.findAllByCountryId(countryId, pageNumber, size);
        PagedResponse<CityDto> response =
                new PagedResponse<>(
                        page.stream().map(cityMapper::toDto).toList(),
                        pageNumber,
                        size,
                        page.getTotalElements(),
                        page.getTotalPages(),
                        page.isLast()
                );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cities")
    public ResponseEntity<CityDto> save(@Valid @RequestBody CityDto cityDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cityMapper.toDto(cityService.save(cityDto)));
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<CityDto> update(@Valid @RequestBody CityDto cityDto, @PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(cityMapper.toDto(cityService.update(cityDto, id)));
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<String> deleteById(@PathVariable final Long id) {
        cityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
