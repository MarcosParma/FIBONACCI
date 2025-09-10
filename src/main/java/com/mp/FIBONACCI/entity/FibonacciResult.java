package com.mp.FIBONACCI.entity;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FibonacciResult {
	
	@Id
    private Integer n;

	@Column(name = "fib_value", nullable = false, columnDefinition = "TEXT")
    private String  value;

    public FibonacciResult() {
    }

    public FibonacciResult(Integer n, BigInteger value) {
        this.n = n;
        this.value = value.toString();;
    }
    
    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public BigInteger getValue() {
        return new BigInteger(value);
    }

    public void setValue(BigInteger value) {
        this.value = value.toString();
    }

}
