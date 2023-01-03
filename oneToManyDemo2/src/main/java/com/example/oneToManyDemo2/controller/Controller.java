package com.example.oneToManyDemo2.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	 * L̥
	 * This method will fetch model record based on cip id
	 */
	@GetMapping("/fetchModelsBasedOnCip/{id}")
	public List<Model> fetchModelsBasedOnCip(@PathVariable Integer id) {
		return modelRepository.findByCip(id);
	}
	
	@PutMapping("/investmentAdjustments/{id}")
	public void investmentAdjustments(@RequestBody Investment investment, @PathVariable Integer id) {

		Optional.ofNullable(investmentRepository.findByCustomerId(investment.getCustomerId()))
		.filter(CollectionUtils::isNotEmpty).ifPresent(list -> {
			
			List<String> finalOutput = new ArrayList<>();
			BigDecimal sum = list.stream().map(Investment::getTotalAssertValue).reduce(BigDecimal.ZERO, BigDecimal::add);
			System.out.println(sum);
			List<Model> findByCip = modelRepository.findByCip(id);
			Map<String, Integer> percentageInfo = new HashMap<>();
			for(Model j: findByCip) {
				percentageInfo.put(j.getAssetName(), j.getAssetPercentage());
			}
			System.out.println(percentageInfo.entrySet());
			for(Map.Entry<String, Integer> entry: percentageInfo.entrySet()) {
				for(Investment i: list) {
					
					if(i.getAssertType().equals(entry.getKey())) {
						
						//.divide(RetailDebitCardAuthConstant.BIGDECIMAL_THOUSAND, RetailDebitCardAuthConstant.NUM_THREE, RoundingMode.HALF_UP)
						//BigDecimal multiply = sum.multiply(new BigDecimal(entry.getValue()/100));
						BigDecimal multiply = sum.multiply(new BigDecimal(entry.getValue()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
						System.out.println(multiply);
						if(multiply.compareTo(i.getTotalAssertValue()) == 0) {
							finalOutput.add("BUY");
							Investment inv = new Investment();
							inv.setTotalAssertValue(multiply);
							investmentRepository.save(inv);
							
						}
						if(multiply.compareTo(i.getTotalAssertValue()) > 0) {
							finalOutput.add("SELL");
							investmentRepository.findByInvestmentIdAndAssertType(i.getInvestmentId(), i.getAssertType()).ifPresent(record -> {
								Investment inv = new Investment();
								inv.setInvestmentId(i.getInvestmentId());
								inv.setCustomerId(i.getCustomerId());
								inv.setAssertType(i.getAssertType());
								inv.setTotalAssertValue(multiply);
								investmentRepository.save(inv);
							});
							
						}
						if(multiply.compareTo(i.getTotalAssertValue()) < 0) {
							finalOutput.add("HOLD");
						}
					}
				}
			}
		});
	}
	
	/**
	 * @param adjustment
	 * @return
	 * 
	 * This method is to make adjustments
	 */
	@PutMapping("/adjustment")
	public Investment adjustments(@RequestBody Investment investment) {
	
		Optional.ofNullable(investmentRepository.findByCustomerId(investment.getCustomerId()))
				.filter(CollectionUtils::isNotEmpty).ifPresent(rec -> {
					rec.stream().forEach(investmentRecord -> {
						if ("Bond".equals(investment.getAssertType())) {
							if ("Cash".equals(investmentRecord.getAssertType())) {
								Optional.ofNullable(investmentRepository.findById(investmentRecord.getInvestmentId()))
										.ifPresent(row -> {
											//row.get().setTotalAssertValue(investmentRecord.getTotalAssertValue() * 0.5);
											investmentRepository.save(row.get());
										});
							}
							if ("Funds".equals(investmentRecord.getAssertType())) {
								Optional.ofNullable(investmentRepository.findById(investmentRecord.getInvestmentId()))
										.ifPresent(row -> {
											//row.get().setTotalAssertValue(investmentRecord.getTotalAssertValue() * 0.7);
											investmentRepository.save(row.get());
										});
							}
							if ("Equity".equals(investmentRecord.getAssertType())) {
								Optional.ofNullable(investmentRepository.findById(investmentRecord.getInvestmentId()))
										.ifPresent(row -> {
											//row.get().setTotalAssertValue(investmentRecord.getTotalAssertValue() * 0.2);
											investmentRepository.save(row.get());
										});
							}
						}
					});
				});

		return investmentRepository.save(investment);

	}

}
