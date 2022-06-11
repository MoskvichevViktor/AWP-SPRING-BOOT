package application.dto;

import application.constants.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractCreateDto {
    Long responseId;
    ContractStatus status;

}
