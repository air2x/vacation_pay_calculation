package vacation_pay_calculation.services;

import org.springframework.stereotype.Service;
import vacation_pay_calculation.util.exceptions.SalaryAmountException;
import vacation_pay_calculation.util.exceptions.VacationDaysException;

@Service
public class CalculatePaymentService {

    public double calculate(double salary, int day) throws SalaryAmountException, VacationDaysException {
        if (salary <= 0) {
            throw new SalaryAmountException("The average monthly salary cannot be less than or equal to 0");
        }
        if (day <= 0) {
            throw new VacationDaysException("the number of days worked cannot be less than or equal to 0");
        }
        return salary / 29.3 * day;
    }
}
