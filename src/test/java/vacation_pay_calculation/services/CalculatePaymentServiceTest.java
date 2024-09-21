package vacation_pay_calculation.services;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import vacation_pay_calculation.util.exceptions.SalaryAmountException;
import vacation_pay_calculation.util.exceptions.VacationDaysException;

class CalculatePaymentServiceTest {

    private final CalculatePaymentService calculatePaymentService = new CalculatePaymentService();

    @Test
    void calculateSuccessTest() throws SalaryAmountException, VacationDaysException {
        double salary = 80000;
        int days = 10;
        double expected = salary / 29.3 * days;
        assertEquals(expected, calculatePaymentService.calculate(salary, days));
    }

    @Test
    void testCalculateSalaryAmountException() {
        assertThrows(SalaryAmountException.class, () -> {
            calculatePaymentService.calculate(0, 10);
        });
        assertThrows(SalaryAmountException.class, () -> {
            calculatePaymentService.calculate(-1, 10);
        });
    }

    @Test
    void testCalculateVacationDaysException() {
        assertThrows(VacationDaysException.class, () -> {
            calculatePaymentService.calculate(80000, 0);
        });
        assertThrows(VacationDaysException.class, () -> {
            calculatePaymentService.calculate(80000, -5);
        });
    }
}