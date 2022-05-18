package com.acme.zipi.application.insurances.create;

import com.acme.zipi.domain.model.User;
import com.acme.zipi.domain.repositories.UsersRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateInsuranceUseCase {

    private final UsersRepository usersRepository;

    public CreateInsuranceResponse execute(CreateInsuranceRequest request) {

        String userId = request.userId;
        String type = request.type;
        String amount = request.amount;

        User user = this.usersRepository.getById(userId);
        String userName = user.getName();

        InsuranceRequest insuranceDTO = InsuranceRequest.builder()
        .holderName(userName)
        .type(type)
        .amount(amount)
        .holderId(userId).build();

        RestTemplate restTemplate = new RestTemplate();
        InsuranceResponse insuranceResponse = restTemplate.postForObject("http://localhost:3000/v1/insurances", insuranceDTO, InsuranceResponse.class);

        return CreateInsuranceResponse.builder().insuranceId(insuranceResponse.insuranceCode).build();

    }
    
}
