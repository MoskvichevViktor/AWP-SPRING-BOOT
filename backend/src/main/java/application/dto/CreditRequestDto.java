package application.dto;

import application.constants.RequestStatus;
import application.models.CreditRequest;
import application.models.CreditResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

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
        if (request.getCreditResponse() != null){
            requestDto.setResponseId(request.getCreditResponse().getId());
        }else {
            requestDto.setResponseId(null);
        }
        requestDto.setCreatedAt(request.getCreatedAt());
        requestDto.setUpdatedAt(request.getUpdatedAt());
        return requestDto;
    }
}
