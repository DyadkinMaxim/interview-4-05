package org.example.interview405.service;

import org.example.interview405.dto.CityDto;
import org.example.interview405.model.City;
import org.springframework.data.domain.Page;



public interface CityService {
    Page<City> findAll(final Integer page, final Integer size);

    Page<City> findAllByCountryId(final Long countryId, final Integer page, final Integer size);

    City findById(final Long id);

    City save(final CityDto cityDto);

    City update(final CityDto cityDto, Long id);

    void deleteById(final Long id);
}
