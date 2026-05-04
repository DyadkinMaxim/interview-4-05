package org.example.interview405.mapper;

import org.example.interview405.dto.CityDto;
import org.example.interview405.model.City;
import org.example.interview405.service.CountryService;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(
            target = "countryId",
            expression = "java(city.getCountry() == null" +
                    " ? null : city.getCountry().getId())"
    )
    CityDto toDto(City city);

    @Mapping(target = "country",
            expression = "java(" +
                    "cityDto.getCountryId() != null " +
                    "? countryService.findById(cityDto.getCountryId())" +
                    ": null)")
    City toEntity(CityDto cityDto, @Context CountryService countryService);

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(City source, @MappingTarget City target);
}
