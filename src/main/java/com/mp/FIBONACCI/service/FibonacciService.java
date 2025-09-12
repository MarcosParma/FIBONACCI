package com.mp.FIBONACCI.service;

import org.springframework.stereotype.Service;

import com.mp.FIBONACCI.entity.FibonacciResult;
import com.mp.FIBONACCI.entity.FibonacciStat;
import com.mp.FIBONACCI.repository.FibonacciRepository;
import com.mp.FIBONACCI.repository.FibonacciStatRepository;

import jakarta.transaction.Transactional;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class FibonacciService {

	private final FibonacciRepository repository;
    private final FibonacciStatRepository statRepository;

    public FibonacciService(FibonacciRepository repository, FibonacciStatRepository statRepository) {
        this.repository = repository;
        this.statRepository = statRepository;
    }

    @Transactional
    public BigInteger getFibonacci(int n) {
        if (n < 0) throw new IllegalArgumentException("n debe ser >= 0");

        updateStats(n);

        // Busca en BD cuál es el mayor valor ya calculado
        Optional<FibonacciResult> lastCalculatedOpt = repository.findTopByOrderByNDesc();
        int start = 0;
        BigInteger prev1 = BigInteger.ZERO;
        BigInteger prev2 = BigInteger.ONE;

        if (lastCalculatedOpt.isPresent()) {
            FibonacciResult last = lastCalculatedOpt.get();
            start = last.getN() + 1; // siguiente número a calcular

            // Recuperar los últimos dos valores para continuar iteración
            if (start - 2 >= 0) {
                FibonacciResult f1 = repository.findById(start - 2).orElse(new FibonacciResult(start - 2, BigInteger.ZERO));
                FibonacciResult f2 = repository.findById(start - 1).orElse(new FibonacciResult(start - 1, BigInteger.ONE));
                prev1 = f1.getValue();
                prev2 = f2.getValue();
            }
        }

        // Si ya está calculado lo busco en la BD directamente
        if (n < start) {
            return repository.findById(n).get().getValue();
        }

        BigInteger value = BigInteger.ZERO;

        for (int i = start; i <= n; i++) {
            if (i == 0) {
            	value = BigInteger.ZERO;
            }
            else if (i == 1) {
            	value = BigInteger.ONE;
            }
            else {
            	value = prev1.add(prev2);
            }

            // Guardar en BD
            repository.save(new FibonacciResult(i, value));

            prev1 = prev2;
            prev2 = value;
        }

        return value;
    }
    
    private void updateStats(int n) {
        FibonacciStat stat = statRepository.findById(n)
                .orElse(new FibonacciStat(n, 0L));
        stat.setCount(stat.getCount() + 1);
        statRepository.save(stat);
    }

    public Iterable<FibonacciStat> getStats() {
        return statRepository.findAll();
    }
}
