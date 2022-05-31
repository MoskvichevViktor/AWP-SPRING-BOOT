package application.services;

import application.constants.RequestStatus;
import application.dto.CreditRequestDto;
import application.dto.CreditRequestInputDto;
import application.models.Client;
import application.models.CreditRequest;
import application.repositories.ClientRepository;
import application.repositories.CreditRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final CreditRequestRepository requestRepository;
    private final ClientRepository clientRepository;

    public List<CreditRequest> findAll() {
        return requestRepository.findAll();
    }

    public Optional<CreditRequest> findById(Long id) {
        return requestRepository.findById(id);
    }

    public List<CreditRequestDto> findAllRequestDto(RequestStatus status) {
        return requestRepository.findAll().stream()
                                .filter(creditRequest -> status == null || creditRequest.getStatus().equals(status))
                                .map(CreditRequestDto::valueOf)
                                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<CreditRequestDto> findRequestDtoById(Long id){
        return  requestRepository.findById(id).map(CreditRequestDto::valueOf);
    }

    public void save(CreditRequestInputDto requestInputDto){
        if (clientRepository.existsById(requestInputDto.getClientId())){
            Client client = clientRepository.getById(requestInputDto.getClientId());
            CreditRequest newRequest = new CreditRequest();
            newRequest.setClient(client);
            newRequest.setSum(requestInputDto.getSum());
            newRequest.setPeriod(requestInputDto.getPeriod());
            newRequest.setStatus(RequestStatus.WAITING);
            requestRepository.save(newRequest);
        }
    }

    public void update(CreditRequestInputDto requestDto) {
        if (requestRepository.existsById(requestDto.getId())) {
            CreditRequest request = requestRepository.getById(requestDto.getId());
            RequestStatus status = request.getStatus();
            request.setSum(requestDto.getSum());
            request.setPeriod(requestDto.getPeriod());
            if (!RequestStatus.WAITING.equals(status)) {
                throw new IllegalArgumentException("Редактировать заявку со статусом: " + status + " запрещено");
            }
            request.setStatus(status);
            requestRepository.save(request);
        } else {
            throw new NoSuchElementException();
        }
    }

}
