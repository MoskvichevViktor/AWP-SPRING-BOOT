package application.dto;

import application.constants.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractInputDto {
    private Long contractId;
    private BigDecimal sum;
    private Integer period;
    private Float percent;
    private ContractStatus status;
}
