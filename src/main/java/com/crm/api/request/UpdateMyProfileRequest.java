package com.crm.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateMyProfileRequest {

    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    public String firstName;

    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    public String lastName;

    @Size(max = 30, message = "Phone must be at most 30 characters")
    public String phone;

    @Size(max = 500, message = "Avatar URL must be at most 500 characters")
    public String avatarUrl;

    @Size(max = 150, message = "Job title must be at most 150 characters")
    public String jobTitle;
}