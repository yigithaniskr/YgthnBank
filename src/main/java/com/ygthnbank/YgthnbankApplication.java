package com.ygthnbank;

import com.ygthnbank.model.Account;
import com.ygthnbank.model.City;
import com.ygthnbank.model.Currency;
import com.ygthnbank.model.Customer;
import com.ygthnbank.repository.AccountRepository;
import com.ygthnbank.repository.CustomerRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@EnableRabbit
public class YgthnbankApplication implements CommandLineRunner {

	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;

	public YgthnbankApplication(AccountRepository accountRepository, CustomerRepository customerRepository) {
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(YgthnbankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Customer c1 = Customer.builder()
				.id("1234568")
				.name("Yigit")
				.city(City.ISTANBUL)
				.address("Ev")
				.dateOfBirth(1999)
				.build();
		Customer c2 = Customer.builder()
				.id("789456")
				.name("Hasan")
				.city(City.MANISA)
				.address("Ev")
				.dateOfBirth(2000)
				.build();
		Customer c3 = Customer.builder()
				.id("456238")
				.name("Mert")
				.city(City.IZMIR)
				.address("Ev")
				.dateOfBirth(2005)
				.build();

		customerRepository.saveAll(Arrays.asList(c1,c2,c3));

		Account a1 = Account.builder()
				.id("100")
				.customerId("1234568")
				.city(City.ISTANBUL)
				.balance(1320.0)
				.currency(Currency.TRY)
				.build();
		Account a2 = Account.builder()
				.id("101")
				.customerId("789456")
				.city(City.ISTANBUL)
				.balance(7898.0)
				.currency(Currency.EUR)
				.build();
		Account a3 = Account.builder()
				.id("102")
				.customerId("456238")
				.city(City.ISTANBUL)
				.currency(Currency.USD)
				.balance(120000.0)
				.build();

		accountRepository.saveAll(Arrays.asList(a1,a2,a3));

	}
}
