package com.mp.FIBONACCI;

import com.mp.FIBONACCI.controller.FibonacciController;
import com.mp.FIBONACCI.entity.FibonacciStat;
import com.mp.FIBONACCI.service.FibonacciService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FibonacciController.class)
class FibonacciControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FibonacciService fibonacciService;

    @Test
    void getFibonacci_returnsValue() throws Exception {
        BDDMockito.given(fibonacciService.getFibonacci(6)).willReturn(new BigInteger("8"));

        mvc.perform(get("/api/fibonacci/6"))
                .andExpect(status().isOk())
                .andExpect(content().string("8"));
    }

    @Test
    void getStats_returnsList() throws Exception {
        BDDMockito.given(fibonacciService.getStats()).willReturn(new ArrayList<FibonacciStat>());

        mvc.perform(get("/api/fibonacci/stats"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }
}
