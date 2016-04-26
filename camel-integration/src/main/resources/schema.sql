CREATE TABLE property (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT property_pk PRIMARY KEY, name VARCHAR(50) DEFAULT NULL, value VARCHAR(50) DEFAULT NULL, inserted TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
INSERT INTO PROPERTY (name, value) VALUES ('esgo4', 'żąć');