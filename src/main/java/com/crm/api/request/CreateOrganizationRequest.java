package com.crm.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateOrganizationRequest {

    @NotBlank(message = "Organization name is required")
    @Size(min = 2, max = 150, message = "Organization name must be between 2 and 150 characters")
    public String name;

    @Size(max = 255, message = "Legal name must be at most 255 characters")
    public String legalName;

    @Size(max = 50, message = "Tax number must be at most 50 characters")
    public String taxNumber;

    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must be at most 100 characters")
    public String country;

    @Size(max = 100, message = "City must be at most 100 characters")
    public String city;

    public boolean active = true;
}