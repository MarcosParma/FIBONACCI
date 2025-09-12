package com.mp.FIBONACCI;

import com.mp.FIBONACCI.entity.FibonacciResult;
import com.mp.FIBONACCI.entity.FibonacciStat;
import com.mp.FIBONACCI.repository.FibonacciRepository;
import com.mp.FIBONACCI.repository.FibonacciStatRepository;
import com.mp.FIBONACCI.service.FibonacciService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FibonacciServiceTest {

    @Mock
    private FibonacciRepository repository;

    @Mock
    private FibonacciStatRepository statRepository;

    @InjectMocks
    private FibonacciService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(repository.save(any(FibonacciResult.class))).thenAnswer(i -> i.getArgument(0));
        when(statRepository.save(any(FibonacciStat.class))).thenAnswer(i -> i.getArgument(0));
    }

    @Test
    void getFibonacci_negative_throws() {
        assertThrows(IllegalArgumentException.class, () -> service.getFibonacci(-5));
    }

    @Test
    void getFibonacci_noCache_computesFromZero() {
        when(repository.findTopByOrderByNDesc()).thenReturn(Optional.empty());
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        BigInteger v = service.getFibonacci(10);
        assertEquals(new BigInteger("55"), v);

        verify(repository, atLeastOnce()).save(any(FibonacciResult.class));
        verify(statRepository, times(1)).save(argThat(s -> s.getN() != null && s.getN() == 10));
    }

    @Test
    void getFibonacci_withCache_usesExisting() {
        FibonacciResult last = new FibonacciResult();
        last.setN(8);
        last.setValue(BigInteger.valueOf(21));
        when(repository.findTopByOrderByNDesc()).thenReturn(Optional.of(last));

        FibonacciResult existing = new FibonacciResult();
        existing.setN(5);
        existing.setValue(BigInteger.valueOf(5));
        when(repository.findById(5)).thenReturn(Optional.of(existing));

        BigInteger v = service.getFibonacci(5);
        assertEquals(new BigInteger("5"), v);

        verify(repository, never()).save(argThat(r -> r.getN() != null && r.getN() == 5));
    }

    @Test
    void getStats_returnsAll() {
        service.getStats();
        verify(statRepository, times(1)).findAll();
    }
}