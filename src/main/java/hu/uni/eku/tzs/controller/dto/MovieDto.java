package hu.uni.eku.tzs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private int id;

    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "year cannot be empty")
    private Integer year;

    @NotBlank(message = "rank cannot be empty")
    private Double rank;
}
