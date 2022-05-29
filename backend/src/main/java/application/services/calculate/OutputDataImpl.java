package application.services.calculate;

import java.math.BigDecimal;

public class OutputDataImpl implements OutputData{

    //номер платежа, номер месяца
    int month;
    //часть выплаты идущая на погашение процентов
    BigDecimal partPercent;
    //часть выплаты идущая на погашение основного долга
    BigDecimal partPayment;
    //ежемесячный платеж
    BigDecimal payment;
    //остаток долга по кредиту
    BigDecimal partSum;

    public OutputDataImpl(int month, BigDecimal partPercent, BigDecimal partPayment, BigDecimal partSum) {
        this.month = month;
        this.partPercent = partPercent;
        this.partPayment = partPayment;
        this.partSum = partSum;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getPartPercent() {
        return partPercent;
    }

    @Override
    public void setPartPercent(BigDecimal partPercent) {
        this.partPercent = partPercent;

    }

    public void setPartProcent(BigDecimal partPercent) {
        this.partPercent = partPercent;
    }

    public BigDecimal getPartPayment() {
        return partPayment;
    }

    public void setPartPayment(BigDecimal partPayment) {
        this.partPayment = partPayment;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getPartSum() {
        return partSum;
    }

    public void setPartSum(BigDecimal partSum) {
        this.partSum = partSum;
    }

    public OutputDataImpl() {

    }
}
