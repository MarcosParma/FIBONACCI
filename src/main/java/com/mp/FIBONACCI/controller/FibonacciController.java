package com.mp.FIBONACCI.controller;

import java.math.BigInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mp.FIBONACCI.entity.FibonacciStat;
import com.mp.FIBONACCI.service.FibonacciService;

@RestController
@RequestMapping("/api/fibonacci")
public class FibonacciController {
	
	private final FibonacciService service;

    public FibonacciController(FibonacciService service) {
        this.service = service;
    }

    @GetMapping("/{n}")
    public BigInteger getFibonacci(@PathVariable int n) {
        return service.getFibonacci(n);
    }
    
    @GetMapping("/stats")
    public Iterable<FibonacciStat> getStats() {
        return service.getStats();
    }

}
