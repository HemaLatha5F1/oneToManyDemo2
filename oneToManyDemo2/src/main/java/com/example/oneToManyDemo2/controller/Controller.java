package com.example.oneToManyDemo2.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.oneToManyDemo2.entities.Customer;
import com.example.oneToManyDemo2.entities.Investment;
import com.example.oneToManyDemo2.entities.Model;
import com.example.oneToManyDemo2.model.Adjustment;
import com.example.oneToManyDemo2.model.LoginForm;
import com.example.oneToManyDemo2.repositories.CustomerRepository;
import com.example.oneToManyDemo2.repositories.InvestmentRepository;
import com.example.oneToManyDemo2.repositories.ModelRepository;

@RestController
public class Controller {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private InvestmentRepository investmentRepository;

	@Autowired
	private ModelRepository modelRepository;
	
	@PostMapping("/loginValidate")
	public String loginAuthentication(@RequestBody LoginForm loginForm) {
		if("SCBDemo@123".equals(loginForm.getPassword())) {
			return "Login Success";
		}
		return "Password is not correct";
	}

	/**
	 * @param id
	 * @return
	 * 
	 * This method will fetch list of customers based on Manager Id
	 */
	@GetMapping("/getAllCustomers/{id}")
	public List<Customer> getAllCustomers(@PathVariable Integer id) {
		return customerRepository.findByManagerId(id);
	}

	/**
	 * @param id
	 * @return
	 * 
	 * This method will fetch list of investments that customer did
	 */
	@GetMapping("/getInvestmentForCustomer/{id}")
	public List<Investment> getInvestmentForCustomer(@PathVariable Integer id) {
		return investmentRepository.findByCustomerId(id);
	}

	/**
	 * @param id
	 * @return
	 * 
	 * This method will fetch model record based on cip id
	 */
	@GetMapping("/fetchModelBasedOnCip/{id}")
	public Optional<Model> fetchModelBasedOnCip(@PathVariable Integer id) {
		return modelRepository.findById(id);
	}
	
	/**
	 * @param adjustment
	 * @return
	 * 
	 * This method is to make adjustments
	 */
	@PutMapping("/adjustment")
	public Investment adjustments(@RequestBody Adjustment adjustment) {

		Investment investment = new Investment();
		investment.setId(adjustment.getId());
		investment.setCustomerId(adjustment.getCid());
		investment.setSalary(adjustment.getSalary());
		investment.setType(adjustment.getType());
		Optional.ofNullable(investmentRepository.findByCustomerId(adjustment.getCid()))
				.filter(CollectionUtils::isNotEmpty).ifPresent(rec -> {
					rec.stream().forEach(investmentRecord -> {
						if ("Bond".equals(adjustment.getType())) {
							if ("Cash".equals(investmentRecord.getType())) {
								Optional.ofNullable(investmentRepository.findById(investmentRecord.getId()))
										.ifPresent(row -> {
											row.get().setSalary(investmentRecord.getSalary() * 0.5);
											investmentRepository.save(row.get());
										});
							}
							if ("Funds".equals(investmentRecord.getType())) {
								Optional.ofNullable(investmentRepository.findById(investmentRecord.getId()))
										.ifPresent(row -> {
											row.get().setSalary(investmentRecord.getSalary() * 0.7);
											investmentRepository.save(row.get());
										});
							}
							if ("Equity".equals(investmentRecord.getType())) {
								Optional.ofNullable(investmentRepository.findById(investmentRecord.getId()))
										.ifPresent(row -> {
											row.get().setSalary(investmentRecord.getSalary() * 0.2);
											investmentRepository.save(row.get());
										});
							}
						}
					});
				});

		return investmentRepository.save(investment);

	}

}
