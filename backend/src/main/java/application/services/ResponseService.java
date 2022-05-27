package application.services;

import application.dto.CreditResponseDto;
import application.models.CreditResponse;
import application.repositories.CreditResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final CreditResponseRepository responseRepository;

    public List<CreditResponse> findAll() {
        return responseRepository.findAll();
    }

    public Optional<CreditResponse> findById(Long id) {
        return responseRepository.findById(id);
    }

    public List<CreditResponseDto> findAllResponseDto(){
        return responseRepository.findAll().stream()
                .map(CreditResponseDto::valueOf)
                .collect(Collectors.toUnmodifiableList());

    }

    public Optional<CreditResponseDto> findResponseDtoById(Long id){
        return  responseRepository.findById(id).map(CreditResponseDto::valueOf);
    }

    public void save(CreditResponseDto responseDto){
        responseRepository.save(responseDto.mapToCreditResponse());
    }

    public void update(CreditResponseDto responseDto) {
        if (responseRepository.existsById(responseDto.getId())) {
            CreditResponse response = responseRepository.getById(responseDto.getId());
            responseRepository.save(responseDto.updateCreditResponse(response));
        }
    }

//    public void deleteById(Long id){
//        responseRepository.deleteById(id);
//    }
}
