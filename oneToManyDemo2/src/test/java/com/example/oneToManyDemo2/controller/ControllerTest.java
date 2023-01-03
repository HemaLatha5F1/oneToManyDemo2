package com.example.oneToManyDemo2.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.oneToManyDemo2.entities.Investment;
import com.example.oneToManyDemo2.model.LoginForm;
import com.example.oneToManyDemo2.repositories.CustomerRepository;
import com.example.oneToManyDemo2.repositories.InvestmentRepository;
import com.example.oneToManyDemo2.repositories.ModelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

class ControllerTest {
	
	@InjectMocks
	private Controller controller;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private ModelRepository modelRepository;
	
	@Mock
	private InvestmentRepository investmentRepository;
	
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testGetAllCustomers() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/getAllCustomers/23")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testGetInvestmentForCustomer() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/getInvestmentForCustomer/23")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
//	@Test
//	void testFetchModelBasedOnCip() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/fetchModelBasedOnCip/23")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
//	}
	
//	@Test
//	void testUpdateInvestments() throws Exception {
//		
//		Adjustment adjustment = new Adjustment();
//		adjustment.setCid(45);
//		adjustment.setId(23);
//		adjustment.setSalary(10000.0);
//		adjustment.setType("Bond");
//		List<Investment> list = new ArrayList<>();
//		Investment investment1 = new Investment();
//		investment1.setId(23);
//		investment1.setCustomerId(45);
//		investment1.setSalary(10000.0);
//		investment1.setType("Cash");
//		list.add(investment1);
//		Investment investment2 = new Investment();
//		investment2.setId(87);
//		investment2.setCustomerId(45);
//		investment2.setSalary(10000.0);
//		investment2.setType("Funds");
//		list.add(investment2);
//		Investment investment3 = new Investment();
//		investment3.setId(56);
//		investment3.setCustomerId(45);
//		investment3.setSalary(10000.0);
//		investment3.setType("Equity");
//		list.add(investment3);
//		
//		String writeValueAsString = new ObjectMapper().writeValueAsString(adjustment);
//		when(investmentRepository.findByCustomerId(ArgumentMatchers.any())).thenReturn(list);
//		when(investmentRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(investment3));
//		mockMvc.perform(MockMvcRequestBuilders.put("/adjustment").content(writeValueAsString)
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
//
//		
//	}
//
//	@Test
//	void testUpdateInvestmentsForNotBond() throws Exception {
//		
//		Adjustment adjustment = new Adjustment();
//		adjustment.setCid(45);
//		adjustment.setId(23);
//		adjustment.setSalary(10000.0);
//		adjustment.setType("Cash");
//		List<Investment> list = new ArrayList<>();
//		Investment investment1 = new Investment();
//		investment1.setId(23);
//		investment1.setCustomerId(45);
//		investment1.setSalary(10000.0);
//		investment1.setType("Cash");
//		list.add(investment1);
//		
//		String writeValueAsString = new ObjectMapper().writeValueAsString(adjustment);
//		when(investmentRepository.findByCustomerId(ArgumentMatchers.any())).thenReturn(list);
//		when(investmentRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(investment1));
//		mockMvc.perform(MockMvcRequestBuilders.put("/adjustment").content(writeValueAsString)
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());	
//	}
//	
	@Test
	void testLoginAuthentication() throws Exception {
		LoginForm loginForm = new LoginForm();
		loginForm.setId(23);
		loginForm.setName("Manager1");
		loginForm.setPassword("SCBDemo@123");
		String writeValueAsString = new ObjectMapper().writeValueAsString(loginForm);
		mockMvc.perform(MockMvcRequestBuilders.post("/loginValidate").content(writeValueAsString)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testLoginAuthenticationPasswordFailure() throws Exception {
		LoginForm loginForm = new LoginForm();
		loginForm.setId(23);
		loginForm.setName("Manager1");
		loginForm.setPassword("SCB@123");
		String writeValueAsString = new ObjectMapper().writeValueAsString(loginForm);
		mockMvc.perform(MockMvcRequestBuilders.post("/loginValidate").content(writeValueAsString)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
