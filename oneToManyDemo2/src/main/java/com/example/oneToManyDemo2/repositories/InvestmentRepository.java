package com.example.oneToManyDemo2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.oneToManyDemo2.entities.Investment;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Integer>{
	
	List<Investment> findByCustomerId(Integer id);
	
	Optional<Investment> findByInvestmentIdAndCustomerId(Integer id, Integer cid);
	
	Optional<Investment> findByInvestmentIdAndAssertType(Integer id, String assertType);

}
