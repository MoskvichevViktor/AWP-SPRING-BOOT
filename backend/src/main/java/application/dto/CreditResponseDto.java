package application.dto;

import application.constants.ResponseStatus;
import application.models.Contract;
import application.models.CreditResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditResponseDto {
    private Long id;
    private Date updateAt;
    private BigDecimal sum;
    private Integer period;
    private Float percent;
    private ResponseStatus status;
    private Contract contract;
    private Long client_id;

    public static CreditResponseDto valueOf(CreditResponse response){
        CreditResponseDto responseDto = new CreditResponseDto();
        responseDto.setId(response.getId());
        responseDto.setUpdateAt(response.getUpdatedAt());
        responseDto.setSum(response.getSum());
        responseDto.setPeriod(response.getPeriod());
        responseDto.setPercent(response.getPercent());
        responseDto.setStatus(responseDto.getStatus());
        responseDto.setContract(response.getContract());
        responseDto.setClient_id(response.getClientId());
        return responseDto;
    }

    public CreditResponse mapToCreditResponse(){
        CreditResponse newResponse = new CreditResponse();
        newResponse.setId(id);
        newResponse.setSum(sum);
        newResponse.setPeriod(period);
        newResponse.setPercent(percent);
        newResponse.setStatus(status);
        newResponse.setContract(contract);
        newResponse.setClientId(client_id);
        return newResponse;
    }

    public CreditResponse updateCreditResponse(CreditResponse response){
        response.setSum(this.sum);
        response.setPeriod(this.period);
        response.setPercent(this.percent);
        response.setStatus((this.status));
        response.setContract(this.contract);
        return  response;
    }
}
