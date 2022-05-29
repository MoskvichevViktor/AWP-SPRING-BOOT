package application.controllers.v1;

import application.services.calculate.Calculation;
import application.services.calculate.InputData;
import application.services.calculate.OutputData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculator")
@AllArgsConstructor
public class CalculationController {

    private  final Calculation calculation;

    @PostMapping
    public List<OutputData> calculate(@RequestBody InputData inputData){
        return calculation.getOutputDataArrays(inputData);
    }

}
