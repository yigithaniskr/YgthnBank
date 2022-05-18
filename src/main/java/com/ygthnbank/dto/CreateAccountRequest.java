package com.ygthnbank.dto;

import com.ygthnbank.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountRequest extends BaseAccountRequest{

    @NotBlank(message = "Account id must not be empty")
    private String id;

}
