package application.services;

import application.constants.RequestStatus;
import application.dto.CreditRequestDto;
import application.dto.CreditRequestInputDto;
import application.exception.AwpException;
import application.models.Client;
import application.models.CreditRequest;
import application.repositories.ClientRepository;
import application.repositories.CreditRequestRepository;
import application.repositories.CreditResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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


    public List<CreditRequestDto> findAllRequestDto(RequestStatus status, Long clientId) {
        return requestRepository.findAll().stream()
                .filter(creditRequest -> status == null || creditRequest.getStatus().equals(status))
                .filter(creditRequest -> clientId == null || creditRequest.getClient().getId().equals(clientId))
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

    @SneakyThrows
    public void update(CreditRequestDto requestDto) {
        CreditRequest request = requestRepository.findById(requestDto.getId()).orElseThrow(() ->
                new NoSuchElementException(String.format("Incorrect credit request ID: %d", requestDto.getId())));
        RequestStatus status = request.getStatus();
        if (status != RequestStatus.WAITING) {
            throw new AwpException("Редактировать запрос со статусом: " + status + " запрещено");
        }
        request.setSum(requestDto.getSum());
        request.setPeriod(requestDto.getPeriod());
        request.setStatus(requestDto.getStatus());
        if (requestDto.getResponseId() != null) {
            if (responseRepository.existsById(requestDto.getResponseId())) {
                request.setCreditResponse(responseRepository.getById(requestDto.getResponseId()));
            } else {
                throw new AwpException("Incorrect credit response id: " + requestDto.getResponseId() + " Not exists!");
            }
        } else {
            request.setCreditResponse(null);
        }
        requestRepository.save(request);
    }

}
