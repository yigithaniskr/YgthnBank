package com.ygthnbank.service;

import com.ygthnbank.dto.CreateCustomerRequest;
import com.ygthnbank.dto.CustomerDto;
import com.ygthnbank.dto.CustomerDtoConverter;
import com.ygthnbank.dto.UpdateCustomerRequest;
import com.ygthnbank.model.City;
import com.ygthnbank.model.Customer;
import com.ygthnbank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
    }

    public CustomerDto createCustomer(CreateCustomerRequest createCustomerRequest){
        Customer customer = new Customer();
        customer.setId(createCustomerRequest.getId());
        customer.setName(createCustomerRequest.getName());
        customer.setAddress(createCustomerRequest.getAddress());
        customer.setDateOfBirth(createCustomerRequest.getDateOfBirt());
        customer.setCity(City.valueOf(createCustomerRequest.getCity().name()));

        customerRepository.save(customer);

        return customerDtoConverter.convert(customer);

    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for(Customer customer:customerList){
            customerDtoList.add(customerDtoConverter.convert(customer)) ;
        }

        return customerDtoList;
    }

    public CustomerDto getCustomerDtoById(String id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerOptional.map(customerDtoConverter::convert).orElse(new CustomerDto());
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    public CustomerDto updateCustomer(String id, UpdateCustomerRequest updateCustomerRequest) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        customerOptional.ifPresent(customer -> {
            customer.setName(updateCustomerRequest.getName());
            customer.setCity(City.valueOf(updateCustomerRequest.getCity().name()));
            customer.setDateOfBirth(updateCustomerRequest.getDateOfBirt());
            customer.setAddress(updateCustomerRequest.getAddress());
            customerRepository.save(customer);
        });

        return customerOptional.map(customerDtoConverter::convert).orElse(new CustomerDto());
    }

    protected Customer getCustomerById(String id){
        return customerRepository.findById(id).orElse(new Customer());
    }
}
