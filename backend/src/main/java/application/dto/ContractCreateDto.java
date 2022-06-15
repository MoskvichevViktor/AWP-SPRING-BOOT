package application.dto;

import application.constants.ContractStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContractCreateDto {
    Long responseId;
    ContractStatus status;
}
