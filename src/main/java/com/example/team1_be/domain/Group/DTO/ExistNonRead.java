package com.example.team1_be.domain.Group.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ExistNonRead {
    @JsonProperty("exist_not_read")
    private boolean existNotRead;

    public ExistNonRead(boolean existNotRead) {
        this.existNotRead = existNotRead;
    }
}
