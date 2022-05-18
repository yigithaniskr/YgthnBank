package com.ygthnbank.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
public class Account implements Serializable {

    @Id
    private String id;

    private String customerId;
    private Double balance;
    private City city;
    private Currency currency;

}