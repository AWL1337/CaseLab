CREATE TABLE file (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title VARCHAR(255),
    description VARCHAR(255),
    file_base64 TEXT,
    creation_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT file_pk PRIMARY KEY (id)
)