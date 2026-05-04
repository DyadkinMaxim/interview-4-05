package org.example.interview405.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {

    private Long id;
    @NotBlank
    private String name;
    private Long population;
    @NotNull
    private Long countryId;
}
