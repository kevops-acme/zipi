package com.acme.zipi.application.insurances.create;

import com.acme.zipi.domain.exceptions.InsuranceNotCreatedException;
import com.acme.zipi.domain.model.User;
import com.acme.zipi.domain.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateInsuranceUseCase {

    @Value("${app.baseurl.zape}")
    String baseUrlZape;

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
        try {
            InsuranceResponse insuranceResponse = restTemplate.postForObject(
                    String.format("%s/v1/insurances", this.baseUrlZape), insuranceDTO, InsuranceResponse.class);
            if (insuranceResponse != null) {
                return CreateInsuranceResponse.builder().insuranceId(insuranceResponse.insuranceCode).build();
            } else {
                throw new InsuranceNotCreatedException();
            }
        } catch (HttpClientErrorException e) {
            throw new InsuranceNotCreatedException();
        }

    }

}
