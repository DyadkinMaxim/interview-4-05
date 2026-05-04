package org.example.interview405.repository;

import org.example.interview405.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CityRepository extends JpaRepository<City, Long>  {

    Page<City> findAllByCountryId(Long countryId, Pageable pageable);
}
