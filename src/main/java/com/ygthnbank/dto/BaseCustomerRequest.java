package com.ygthnbank.dto;

import com.ygthnbank.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseCustomerRequest {
    private String name;
    private Integer dateOfBirth;
    private CityDto city;
    private String address;
}
