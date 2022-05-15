package com.ygthnbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseCustomerRequest {

    private String name;
    private Integer dateOfBirt;
    private CityDto city;
    private String address;
}
