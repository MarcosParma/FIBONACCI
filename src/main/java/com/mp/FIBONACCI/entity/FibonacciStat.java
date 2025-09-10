package com.mp.FIBONACCI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FibonacciStat {

	@Id
    private Integer n; // número consultado

    private Long count; // cantidad de veces consultado

    public FibonacciStat() {
    }

    public FibonacciStat(Integer n, Long count) {
        this.n = n;
        this.count = count;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
	
}
