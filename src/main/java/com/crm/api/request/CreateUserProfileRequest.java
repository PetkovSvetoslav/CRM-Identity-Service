package com.crm.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CreateUserProfileRequest {

    @NotNull(message = "authUserId is required")
    public UUID authUserId;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must be at most 255 characters")
    public String email;

    @NotBlank(message = "Role is required")
    @Size(max = 50, message = "Role must be at most 50 characters")
    public String role;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    public String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    public String lastName;

    @Size(max = 30, message = "Phone must be at most 30 characters")
    public String phone;

    @Size(max = 500, message = "Avatar URL must be at most 500 characters")
    public String avatarUrl;

    @Size(max = 150, message = "Job title must be at most 150 characters")
    public String jobTitle;

    public UUID organizationId;
    public UUID departmentId;
    public UUID teamId;

    public boolean active = true;
}