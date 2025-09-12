package com.mp.FIBONACCI;

import com.mp.FIBONACCI.entity.FibonacciResult;
import com.mp.FIBONACCI.repository.FibonacciRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FibonacciRepositoryIT {

    @Autowired
    private FibonacciRepository repository;

    @Test
    void saveAndFindByN() {
        FibonacciResult r = new FibonacciResult();
        r.setN(3);
        r.setValue(BigInteger.valueOf(2));
        repository.save(r);

        Optional<FibonacciResult> found = repository.findById(3);
        assertThat(found).isPresent();
        assertThat(found.get().getValue()).isEqualTo(new BigInteger("2"));
    }
}
