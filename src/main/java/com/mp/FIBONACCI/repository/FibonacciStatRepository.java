package com.mp.FIBONACCI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mp.FIBONACCI.entity.FibonacciStat;

public interface FibonacciStatRepository extends JpaRepository<FibonacciStat, Integer> {
}
