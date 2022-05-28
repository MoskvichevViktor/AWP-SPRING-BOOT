package application.services;

import application.constants.RequestStatus;
import application.dto.CreditRequestDto;
import application.models.CreditRequest;
import application.repositories.CreditRequestRepositoriy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final CreditRequestRepositoriy requestRepositoriy;

    public List<CreditRequest> findAll() {
        return requestRepositoriy.findAll();
    }

    public Optional<CreditRequest> findById(Long id) {
        return requestRepositoriy.findById(id);
    }

    public List<CreditRequestDto> findAllRequestDto(RequestStatus status) {
        return requestRepositoriy.findAll().stream()
                .filter(creditRequest -> status == null || creditRequest.getStatus().equals(status))
                .map(CreditRequestDto::valueOf)
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<CreditRequestDto> findRequestDtoById(Long id){
        return  requestRepositoriy.findById(id).map(CreditRequestDto::valueOf);
    }

    public void save(CreditRequestDto requestDto){
//        requestRepositoriy.save(requestDto.mapToCreditRequest());
    }

    public void update(CreditRequestDto requestDto) {
        if (requestRepositoriy.existsById(requestDto.getId())) {
            CreditRequest request = requestRepositoriy.getById(requestDto.getId());
//            requestRepositoriy.save(requestDto.updateCreditRequest(request));
        } else {
            throw new NoSuchElementException();
        }
    }

//    public void deleteById(Long id){
//        requestRepositoriy.deleteById(id);
//    }
}
