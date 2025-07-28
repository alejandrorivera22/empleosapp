CREATE TABLE usuarios (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(45) NOT NULL,
  email VARCHAR(100) NOT NULL,
  username VARCHAR(45) NOT NULL,
  password VARCHAR(100) NOT NULL,
  estatus INT NOT NULL DEFAULT 1,
  fecha_registro DATE,
  CONSTRAINT uc_username UNIQUE (username),
  CONSTRAINT uc_email UNIQUE (email)
);
