package com.crm.api.response;

public class UserProfileResponse {
    public String id;
    public String authUserId;
    public String email;
    public String role;
    public String firstName;
    public String lastName;
    public String phone;
    public String avatarUrl;
    public String jobTitle;
    public NamedReferenceResponse organization;
    public NamedReferenceResponse department;
    public NamedReferenceResponse team;
    public boolean active;
    public String createdAt;
    public String updatedAt;
}