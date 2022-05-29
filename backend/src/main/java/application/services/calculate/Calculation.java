package application.services.calculate;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
@Service
public class Calculation {

    public  List<OutputData> getOutputDataArrays(InputData inputData){
        List<OutputData> outputDataArrays = new ArrayList<OutputData>();
        double P=inputData.getPercent()/(100*12);
        BigDecimal payment = inputData.getSum().multiply(BigDecimal.valueOf(P + P/(Math.pow((1+P), inputData.getPeriod()) - 1)));
        BigDecimal sumPlusPercent = payment.multiply(BigDecimal.valueOf(inputData.getPeriod()));
        int j = inputData.getPeriod();


        for(int i = 0; i < j; i++ ){
            if(i==0){
                OutputData outputData = new OutputDataImpl();
                outputData.setMonth(i+1);
                outputData.setPayment(payment.setScale(2, RoundingMode.CEILING)); //платеж
                outputData.setPartSum(sumPlusPercent.subtract(payment).setScale(2, RoundingMode.CEILING)); //остаток долга
                outputData.setPartPercent(outputData.getPartSum().multiply(BigDecimal.valueOf(P/12)).setScale(2, RoundingMode.CEILING)); //часть платежа на погашение процентов
                outputData.setPartPayment(payment.subtract(outputData.getPartPercent()).setScale(2, RoundingMode.CEILING)); ////часть платежа на погашение долга

                outputDataArrays.add(outputData);


            } else{
                OutputData outputData = new OutputDataImpl();
                outputData.setMonth(i+1);
                outputData.setPayment(payment.setScale(2, RoundingMode.CEILING));
                outputData.setPartSum(sumPlusPercent.subtract(payment.multiply(BigDecimal.valueOf(i+1))).setScale(2, RoundingMode.CEILING));
                outputData.setPartPercent(outputData.getPartSum().multiply(BigDecimal.valueOf(P/12)).setScale(2, RoundingMode.CEILING));
                outputData.setPartPayment(payment.subtract(outputData.getPartPercent()).setScale(2, RoundingMode.CEILING));

                outputDataArrays.add(outputData);

            }

        }
        return outputDataArrays;
    }


}
