package application.services;

import application.constants.RequestStatus;
import application.dto.CreditRequestDto;
import application.dto.CreditRequestInputDto;
import application.exception.ResourceNotFoundException;
import application.models.Client;
import application.models.CreditRequest;
import application.repositories.ClientRepository;
import application.repositories.CreditRequestRepository;
import application.repositories.CreditResponseRepository;
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
    private final CreditResponseRepository responseRepository;


    public List<CreditRequestDto> findAllRequestDto(RequestStatus status) {
        return requestRepository.findAll().stream()
                .filter(creditRequest -> status == null || creditRequest.getStatus().equals(status))
                .map(CreditRequestDto::valueOf)
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<CreditRequestDto> findRequestDtoById(Long id) {
        return requestRepository.findById(id).map(CreditRequestDto::valueOf);
    }

    public void save(CreditRequestInputDto requestInputDto) {
        if (clientRepository.existsById(requestInputDto.getClientId())) {
            Client client = clientRepository.getById(requestInputDto.getClientId());
            CreditRequest newRequest = new CreditRequest();
            newRequest.setClient(client);
            newRequest.setSum(requestInputDto.getSum());
            newRequest.setPeriod(requestInputDto.getPeriod());
            newRequest.setStatus(RequestStatus.WAITING);
            requestRepository.save(newRequest);
        }
    }

    public void update(CreditRequestDto requestDto) {

        CreditRequest request = requestRepository.getById(requestDto.getId());
        if (request != null) {
            RequestStatus status = request.getStatus();
            request.setSum(requestDto.getSum());
            request.setPeriod(requestDto.getPeriod());
            request.setStatus(status);
            if (status != RequestStatus.WAITING) {
                throw new IllegalArgumentException("Редактировать запрос со статусом: " + status + " запрещено");
            }
            //   request.setCreditResponse(responseRepository.findById(requestDto.getResponseId()).orElse(null));
            if (requestDto.getResponseId() != null) {
                if (responseRepository.existsById(requestDto.getResponseId())) {
                    request.setCreditResponse(responseRepository.getById(requestDto.getResponseId()));
                } else {
                    throw new ResourceNotFoundException("Incorrect credit response id: " + requestDto.getResponseId() + " Not exists!");
                }
            } else {
                request.setCreditResponse(null);
            }
            requestRepository.save(request);
        } else {
            throw new NoSuchElementException();
        }
    }

}
