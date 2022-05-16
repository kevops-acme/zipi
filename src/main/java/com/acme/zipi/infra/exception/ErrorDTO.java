package com.acme.zipi.infra.exception;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {

    private final int status;
    private final String message;
    private final UUID code;

}
