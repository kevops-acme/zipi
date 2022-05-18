package com.acme.zipi.domain.exceptions;

public class InsuranceNotCreatedException extends RuntimeException {

    public InsuranceNotCreatedException() {
        super("Insurance not created");
    }
    
}
