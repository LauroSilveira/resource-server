CREATE TABLE task (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   title VARCHAR(255) NOT NULL,
   description VARCHAR(255) NOT NULL,
   CONSTRAINT pk_task PRIMARY KEY (id)
);