package application.dto;

import application.constants.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditResponseInputDto {
    private Long requestId;
    private BigDecimal sum;
    private Integer period;
    private Float percent;
    private RequestStatus status;
}
