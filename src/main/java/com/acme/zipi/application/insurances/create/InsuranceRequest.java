package com.acme.zipi.application.insurances.create;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsuranceRequest {
    
    String holderId;
    String holderName;
    String type;
    String amount;

}
