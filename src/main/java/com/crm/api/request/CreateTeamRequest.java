package com.crm.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CreateTeamRequest {

    @NotNull(message = "Organization id is required")
    public UUID organizationId;

    @NotBlank(message = "Team name is required")
    @Size(min = 2, max = 150, message = "Team name must be between 2 and 150 characters")
    public String name;

    public UUID managerUserId;

    public boolean active = true;
}