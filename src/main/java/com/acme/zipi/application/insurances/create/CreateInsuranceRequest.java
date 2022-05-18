package com.acme.zipi.application.insurances.create;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateInsuranceRequest {

    String userId;
    String type;
    String amount;

}