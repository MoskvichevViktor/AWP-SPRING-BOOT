package application.dto;

import application.constants.RequestStatus;
import application.models.CreditRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequestDto {
    private Long id;
    private BigDecimal sum;
    private Integer period;
    private RequestStatus status;
    private Long clientId;
    private String clientName;
    private Long responseId;
    private Date createdAt;
    private Date updatedAt;

    public static CreditRequestDto valueOf(CreditRequest request){
        CreditRequestDto requestDto = new CreditRequestDto();
        requestDto.setId(request.getId());
        requestDto.setSum(request.getSum());
        requestDto.setPeriod(request.getPeriod());
        requestDto.setStatus(request.getStatus());
        requestDto.setClientId(request.getClient().getId());
        requestDto.setClientName(request.getClient().getName());
        requestDto.setResponseId(request.getCreditResponse().getId());
        requestDto.setCreatedAt(request.getCreatedAt());
        requestDto.setUpdatedAt(request.getUpdatedAt());
        return requestDto;
    }

// TODO: 28.05.2022 перенести в сервис - создание нового Request на основе ДТО
    //    public CreditRequest mapToCreditRequest(){
//        CreditRequest newRequest = new CreditRequest();
//        newRequest.setId(id);
//        newRequest.setSum(sum);
//        newRequest.setPeriod(period);
//        newRequest.setStatus(status);
//        newRequest.setClient(client);
//        newRequest.setCreditResponse(response);
//        return newRequest;
//    }
    //todo 28.05.2022 перенести в сервис - обновление Request на основе ДТО
//    public CreditRequest updateCreditRequest(CreditRequest request){
//        request.setStatus(status);
//        request.setSum(sum);
//        request.setPeriod(period);
//        request.setCreditResponse(response);
//        return request;
//    }
}
