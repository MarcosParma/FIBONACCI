package com.mp.FIBONACCI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mp.FIBONACCI.entity.FibonacciResult;

public interface FibonacciRepository extends JpaRepository<FibonacciResult, Integer> {
	
	Optional<FibonacciResult> findTopByOrderByNDesc();
	
}
