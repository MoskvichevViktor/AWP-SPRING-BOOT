package application.services.calculate;

import java.math.BigDecimal;

public class InputData {

    private BigDecimal sum;
    private float percent;
    private int period;

    public InputData(BigDecimal sum, float percent, int period) {
        this.sum = sum;
        this.percent = percent;
        this.period = period;
    }

        public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
}
