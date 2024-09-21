package vacation_pay_calculation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vacation_pay_calculation.services.CalculatePaymentService;
import vacation_pay_calculation.util.exceptions.SalaryAmountException;
import vacation_pay_calculation.util.exceptions.VacationDaysException;

@RestController
public class PaymentController {

    private final CalculatePaymentService calculatePaymentService;

    @Autowired
    public PaymentController(CalculatePaymentService calculatePaymentService) {
        this.calculatePaymentService = calculatePaymentService;
    }

    @GetMapping("/calculate")
    public double getResult(double salary, int days) throws SalaryAmountException, VacationDaysException {
        return calculatePaymentService.calculate(salary, days);
    }
}
