package com.example.oneToManyDemo2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.oneToManyDemo2.entities.Customer;
import com.example.oneToManyDemo2.entities.Investment;
import com.example.oneToManyDemo2.repositories.CustomerRepository;
import com.example.oneToManyDemo2.repositories.InvestmentRepository;

@RestController
public class Controller {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private InvestmentRepository investmentRepository;

	@GetMapping("/getAllCustomers/{id}")
	public List<Customer> getAllCustomers(@PathVariable Integer id) {
		return customerRepository.findByManagerId(id);
	}

	@GetMapping("/getInvestmentForCustomer/{id}")
	public List<Investment> getInvestmentForCustomer(@PathVariable Integer id) {
		return investmentRepository.findByCustomerId(id);
	}
	
//	@PutMapping("/updateInvestment/{id}")
//	public List<Investment> updateInvestments(@PathVariable Integer id) {
//		List<Investment> findByCustomerId = investmentRepository.findByCustomerId(id);
//		Investment investment = findByCustomerId.get(1);
//		investment.setType("Equity");
//		return findByCustomerId;
//		
//	}
	
	@PutMapping("/updateInvestment/{id}/{cid}")
	public Investment updateInvestments(@PathVariable Integer id, @PathVariable Integer cid) {
//		Optional<Investment> findByIdAndCustomerId = investmentRepository.findByIdAndCustomerId(id, cid);
//		Investment newInvestment = null;
//		if(findByIdAndCustomerId.isPresent()) {
//			newInvestment = findByIdAndCustomerId.get();
//		}
//		newInvestment.setType("Equity");
//		return investmentRepository.save(newInvestment);
		
		Investment investment = investmentRepository.findByIdAndCustomerId(id, cid).get();
		Optional.ofNullable(investment).ifPresent(invest -> invest.setType("Equity"));
		return investmentRepository.save(investment);
		
		
	}

}
