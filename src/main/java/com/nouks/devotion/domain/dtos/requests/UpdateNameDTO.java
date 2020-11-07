package com.nouks.devotion.domain.dtos.requests;

import javax.validation.constraints.NotBlank;

public class UpdateNameDTO {
    @NotBlank(message = "name is required")
    String name;

    public UpdateNameDTO() {
    }

    public UpdateNameDTO(@NotBlank(message = "name is required") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
