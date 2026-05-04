package org.example.interview405.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.interview405.dto.CityDto;
import org.example.interview405.exception.NotFoundException;
import org.example.interview405.mapper.CityMapper;
import org.example.interview405.model.City;
import org.example.interview405.repository.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final CountryService countryService;

    @Override
    public Page<City> findAll(Integer page , Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return cityRepository.findAll(pageable);
    }

    @Override
    public Page<City> findAllByCountryId(final Long countryId, final Integer page, final Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return cityRepository.findAllByCountryId(countryId, pageable);
    }

    @Override
    public City findById(final Long id) {
        return cityRepository.findById(id).orElseThrow(
                () -> new NotFoundException("City is not found by id: " + id));
    }

    @Override
    @Transactional
    public City save(final CityDto cityDto) {
        City city = cityMapper.toEntity(cityDto, countryService);
        return cityRepository.save(city);
    }

    @Override
    @Transactional
    public City update(final CityDto cityDto, Long id) {
        City city = cityMapper.toEntity(cityDto, countryService);
        City savedcity = cityRepository.findById(id).orElseThrow(
                () -> new NotFoundException("City is not found by id: " + id));
        log.info("Updating: {}", city);
        cityMapper.updateEntity(city, savedcity);
        log.info("After mapping: " + savedcity);
        return cityRepository.save(savedcity);
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        findById(id);
        cityRepository.deleteById(id);
    }
}
