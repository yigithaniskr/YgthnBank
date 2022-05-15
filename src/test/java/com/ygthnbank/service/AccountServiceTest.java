package com.ygthnbank.service;

import com.ygthnbank.dto.AccountDto;
import com.ygthnbank.dto.AccountDtoConverter;
import com.ygthnbank.dto.CreateAccountRequest;
import com.ygthnbank.model.Account;
import com.ygthnbank.model.City;
import com.ygthnbank.model.Currency;
import com.ygthnbank.model.Customer;
import com.ygthnbank.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;

public class AccountServiceTest {
    private AccountService accountService;

    private AccountRepository accountRepository;
    private CustomerService customerService;
    private AccountDtoConverter accountDtoConverter;
    private DirectExchange exchange;
    private AmqpTemplate rabbitTemplate;
    @Before
    public void setUp() throws Exception {
        accountRepository = Mockito.mock(AccountRepository.class);
        customerService = Mockito.mock(CustomerService.class);
        accountDtoConverter = Mockito.mock(AccountDtoConverter.class);

        accountService = new AccountService(accountRepository,customerService,accountDtoConverter, exchange, rabbitTemplate);
    }

    @Test
    public void whenCreateAccountCalledWithValidRequest_itShouldReturnValidAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
         createAccountRequest.setId("1234");
         createAccountRequest.setCustomerId("12345");
         createAccountRequest.setBalance(100.0);
         createAccountRequest.setCity(City.ISTANBUL);
         createAccountRequest.setCurrency(Currency.TRY);

         Customer customer = Customer.builder()
                 .id("12345")
                 .address("Adres")
                 .city(City.ISTANBUL)
                 .dateOfBirth(1998)
                 .name("Yiğithan")
                 .build();

        Account account = Account.builder()
                .id(createAccountRequest.getCustomerId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();

        AccountDto accountDto = AccountDto.builder()
                .id("1234")
                .customerId("12345")
                .currency(Currency.TRY)
                .balance(100.0)
                .build();

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountDtoConverter.convert(account)).thenReturn(accountDto);

        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result, accountDto);

        Mockito.verify(customerService).getCustomerById("12345");
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(accountDtoConverter).convert(account);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledWithNonExistCustomer_itShouldReturnEmptyAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setId("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.TRY);

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder().build());

        AccountDto expectedAccountDto = AccountDto.builder().build();
        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result,expectedAccountDto);

        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledWithCustomerWithOutId_itShouldReturnEmptyAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setId("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.TRY);

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder()
                 .id(" ")
                 .build());

        AccountDto expectedAccountDto = AccountDto.builder().build();
        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result,expectedAccountDto);

        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledAndRepositoryThrewException_ItShouldThrowException(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setId("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.TRY);

        Customer customer = Customer.builder()
                .id("12345")
                .address("Adres")
                .city(City.ISTANBUL)
                .dateOfBirth(1998)
                .name("Yiğithan")
                .build();

        Account account = Account.builder()
                .id(createAccountRequest.getCustomerId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();


        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenThrow(new RuntimeException("Bla bla"));

        accountService.createAccount(createAccountRequest);

        Mockito.verify(customerService).getCustomerById("12345");
        Mockito.verify(accountRepository).save(account);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }
}