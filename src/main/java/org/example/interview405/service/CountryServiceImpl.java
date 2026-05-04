package org.example.interview405.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.interview405.mapper.CountryMapper;
import org.example.interview405.model.Country;
import org.example.interview405.exception.NotFoundException;
import org.example.interview405.repository.CountryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Slf4j
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public Page<Country> findAll(Integer page , Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return countryRepository.findAll(pageable);
    }

    @Override
    public Country findById(final Long id) {
        return countryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Country is not found by id: " + id));
    }

    @Override
    @Transactional
    public Country save(final Country country) {
        return countryRepository.save(country);
    }

    @Override
    @Transactional
    public Country update(final Country country, Long id) {
        Country savedCountry = countryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Country is not found by id: " + id));
        log.info("Updating: " + country);
        countryMapper.updateEntity(country, savedCountry);
        log.info("Updating: " + country);
        log.info("After mapping: " + savedCountry);
        return countryRepository.save(savedCountry);
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        findById(id);
        countryRepository.deleteById(id);
    }
}
