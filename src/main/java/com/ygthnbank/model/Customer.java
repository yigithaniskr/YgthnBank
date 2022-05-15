package com.ygthnbank.model;

import com.ygthnbank.dto.CityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    private String id;

    private String name;
    private Integer dateOfBirth;
    private City city;
    private String address;

}
