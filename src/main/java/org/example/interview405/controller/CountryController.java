package org.example.interview405.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.interview405.dto.CountryDto;
import org.example.interview405.dto.PagedResponse;
import org.example.interview405.mapper.CountryMapper;
import org.example.interview405.model.Country;
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
public class CountryController {

    private final CountryService countryService;
    private final CountryMapper countryMapper;

    @GetMapping("/countries")
    public ResponseEntity<PagedResponse<CountryDto>> findAll(
            @RequestParam final Integer pageNumber,
            @RequestParam final Integer size
    ) {
        Page<Country> page = countryService.findAll(pageNumber, size);
        PagedResponse<CountryDto> response =
                new PagedResponse<>(
                        page.stream().map(countryMapper::toDto).toList(),
                        pageNumber,
                        size,
                        page.getTotalElements(),
                        page.getTotalPages(),
                        page.isLast()
                );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<CountryDto> findById(@PathVariable final Long id) {
        log.info("Find by id {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(countryMapper.toDto(countryService.findById(id)));
    }

    @PostMapping("/countries")
    public ResponseEntity<CountryDto> save(@Valid @RequestBody CountryDto countryDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(countryMapper.toDto(countryService.save(
                        countryMapper.toEntity(countryDto))));
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<CountryDto> update(@Valid @RequestBody CountryDto countryDto, @PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(countryMapper.toDto(countryService.update(
                        countryMapper.toEntity(countryDto), id)));
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<String> deleteById(@PathVariable final Long id) {
        countryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
