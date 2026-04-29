CREATE TABLE organization (
    id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE,
    legal_name VARCHAR(255),
    tax_number VARCHAR(50),
    country VARCHAR(100) NOT NULL,
    city VARCHAR(100),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE department (
    id UUID PRIMARY KEY,
    organization_id UUID NOT NULL,
    name VARCHAR(150) NOT NULL,
    manager_user_id UUID,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_department_org FOREIGN KEY (organization_id) REFERENCES organization(id),
    CONSTRAINT uk_department_org_name UNIQUE (organization_id, name)
);

CREATE TABLE team (
    id UUID PRIMARY KEY,
    organization_id UUID NOT NULL,
    name VARCHAR(150) NOT NULL,
    manager_user_id UUID,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_team_org FOREIGN KEY (organization_id) REFERENCES organization(id),
    CONSTRAINT uk_team_org_name UNIQUE (organization_id, name)
);

CREATE TABLE user_profile (
    id UUID PRIMARY KEY,
    auth_user_id UUID NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(30),
    avatar_url VARCHAR(500),
    job_title VARCHAR(150),
    organization_id UUID,
    department_id UUID,
    team_id UUID,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_user_profile_org FOREIGN KEY (organization_id) REFERENCES organization(id),
    CONSTRAINT fk_user_profile_department FOREIGN KEY (department_id) REFERENCES department(id),
    CONSTRAINT fk_user_profile_team FOREIGN KEY (team_id) REFERENCES team(id)
);