package com.ygthnbank.dto;

import com.ygthnbank.model.City;
import com.ygthnbank.model.Currency;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AccountDto implements Serializable {

    private String id;
    private String customerId;
    private Double balance;
    private Currency currency;
}
