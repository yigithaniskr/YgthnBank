package com.ygthnbank.dto;

import com.ygthnbank.model.City;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AddressDto {

    private String id;
    private City city;
    private String postCode;
}