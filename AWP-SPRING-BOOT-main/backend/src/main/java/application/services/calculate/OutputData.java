package application.services.calculate;

import java.math.BigDecimal;

public interface OutputData {
    int getMonth();

    void setMonth(int month);

    BigDecimal  getPartPercent();

    void setPartPercent(BigDecimal  partPercent);

    BigDecimal getPartPayment();

    void setPartPayment(BigDecimal partPayment);

    BigDecimal getPayment();

    void setPayment(BigDecimal payment);

    BigDecimal getPartSum();

    void setPartSum(BigDecimal partSum);

}
