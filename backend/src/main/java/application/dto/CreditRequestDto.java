package application.dto;

import application.constants.RequestStatus;
import application.models.Client;
import application.models.CreditRequest;
import application.models.CreditResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.net.CacheResponse;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequestDto {
    private Long id;
    private BigDecimal sum;
    private Integer period;
    private RequestStatus status;
    private Client client;
    private CreditResponse response;
    private Date createdAt;

    public static CreditRequestDto valueOf(CreditRequest request){
        CreditRequestDto requestDto = new CreditRequestDto();
        requestDto.setId(request.getId());
        requestDto.setSum(request.getSum());
        requestDto.setPeriod(request.getPeriod());
        requestDto.setStatus(request.getStatus());
        requestDto.setClient(request.getClient());
        requestDto.setResponse(request.getCreditResponse());
        requestDto.setCreatedAt(request.getCreatedAt());
        return requestDto;
    }

    public CreditRequest mapToCreditRequest(){
        CreditRequest newRequest = new CreditRequest();
        newRequest.setId(id);
        newRequest.setSum(sum);
        newRequest.setPeriod(period);
        newRequest.setStatus(status);
        newRequest.setClient(client);
        newRequest.setCreditResponse(response);
        return newRequest;
    }

    public CreditRequest updateCreditRequest(CreditRequest request){
        request.setStatus(status);
        request.setSum(sum);
        request.setPeriod(period);
        request.setCreditResponse(response);
        return request;
    }
}
