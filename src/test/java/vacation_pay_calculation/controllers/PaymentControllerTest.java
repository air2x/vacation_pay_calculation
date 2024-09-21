package vacation_pay_calculation.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import vacation_pay_calculation.services.CalculatePaymentService;
import vacation_pay_calculation.util.exceptions.SalaryAmountException;
import vacation_pay_calculation.util.exceptions.VacationDaysException;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private CalculatePaymentService calculatePaymentService;

    @BeforeEach
    void setUp() {
        calculatePaymentService = Mockito.mock(CalculatePaymentService.class);
    }

    @Test
    void testGetResultSuccess() throws Exception {
        when(calculatePaymentService.calculate(anyDouble(), anyInt())).thenReturn(2000.0);

        mockMvc.perform(get("/calculate?salary=60000&days=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("2000.0"));
    }

    @Test
    void testGetResultSalaryAmountException() throws Exception {
        when(calculatePaymentService.calculate(0, 10))
                .thenThrow(new SalaryAmountException("The average monthly salary cannot be less than or equal to 0"));
        mockMvc.perform(get("/calculate?salary=0&days=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetResultVacationDaysException() throws Exception {
        when(calculatePaymentService.calculate(60000, 0))
                .thenThrow(new VacationDaysException("the number of days worked cannot be less than or equal to 0"));

        mockMvc.perform(get("/calculate?salary=60000&days=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}