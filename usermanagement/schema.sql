CREATE TABLE users (
id BIGINT NOT NULL IDENTITY, 
firstname VARCHAR(32) NOT NULL, 
lastname VARCHAR(32) NOT NULL, 
dateOfBirth DATE NOT NULL,
PRIMARY KEY (id)
)