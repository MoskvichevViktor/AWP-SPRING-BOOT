package application.dto;

import application.constants.ContractStatus;
import application.models.Contract;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class ContractDto {
    private Long clientId;
    private String clientName;
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private Integer period;
    private BigDecimal sum;
    private Float percent;
    private ContractStatus status;

    public static ContractDto valueOf(Contract contract) {
        ContractDto contractDto = new ContractDto();
        contractDto.setClientId(contract.getClient().getId());
        contractDto.setClientName(contract.getClient().getName());
        contractDto.setId(contract.getId());
        contractDto.setCreatedAt(contract.getCreatedAt());
        contractDto.setUpdatedAt(contract.getUpdatedAt());
        contractDto.setPeriod(contract.getPeriod());
        contractDto.setSum(contract.getSum());
        contractDto.setPercent(contract.getPercent());
        contractDto.setStatus(contract.getStatus());
        return contractDto;
    }

}
