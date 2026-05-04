package org.example.interview405.service;

import org.example.interview405.model.Country;
import org.springframework.data.domain.Page;

public interface CountryService {
    Page<Country> findAll(final Integer page, final Integer size);

    Country findById(final Long id);

    Country save(final Country city);

    Country update(final Country city, Long id);

    void deleteById(final Long id);
}
