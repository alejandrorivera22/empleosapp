CREATE TABLE users (
  username varchar(50) NOT NULL,
  password varchar(50) NOT NULL,
  enabled tinyint NOT NULL,
  PRIMARY KEY (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE usuarios (
  id int NOT NULL AUTO_INCREMENT,
  nombre varchar(45) NOT NULL,
  email varchar(100) NOT NULL,
  username varchar(45) NOT NULL,
  password varchar(100) NOT NULL,
  estatus int NOT NULL DEFAULT '1',
  fecha_registro date DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY username_UNIQUE (username),
  UNIQUE KEY email_UNIQUE (email)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE perfiles (
  id int NOT NULL AUTO_INCREMENT,
  perfil varchar(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE usuarioperfil (
  id_usuario int NOT NULL,
  id_perfil int NOT NULL,
  UNIQUE KEY Usuario_Perfil_UNIQUE (id_usuario,id_perfil),
  KEY fk_Usuarios1_idx (id_usuario),
  KEY fk_Perfiles1_idx (id_perfil),
  CONSTRAINT fk_Perfiles1 FOREIGN KEY (id_perfil) REFERENCES perfiles (id),
  CONSTRAINT fk_Usuarios1 FOREIGN KEY (id_usuario) REFERENCES usuarios (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE authorities (
  username varchar(50) NOT NULL,
  authority varchar(50) NOT NULL,
  UNIQUE KEY authorities_idx_1 (username,authority),
  CONSTRAINT authorities_ibfk_1 FOREIGN KEY (username) REFERENCES users (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE categorias (
  id int NOT NULL AUTO_INCREMENT,
  nombre varchar(100) NOT NULL,
  descripcion text,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE vacantes (
  id int NOT NULL AUTO_INCREMENT,
  nombre varchar(200) NOT NULL,
  descripcion text NOT NULL,
  fecha date NOT NULL,
  salario double NOT NULL,
  estatus enum('Creada','Aprobada','Eliminada') NOT NULL,
  destacado int NOT NULL,
  imagen varchar(250) NOT NULL,
  detalles text,
  id_categoria int NOT NULL,
  PRIMARY KEY (id),
  KEY fk_vacantes_categorias1_idx (id_categoria),
  CONSTRAINT fk_vacantes_categorias1 FOREIGN KEY (id_categoria) REFERENCES categorias (id)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE solicitudes (
  id int NOT NULL AUTO_INCREMENT,
  fecha date NOT NULL,
  archivo varchar(250) NOT NULL,
  comentarios text,
  id_vacante int NOT NULL,
  id_usuario int NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY Vacante_Usuario_UNIQUE (id_vacante,id_usuario),
  KEY fk_Solicitudes_Vacantes1_idx (id_vacante),
  KEY fk_Solicitudes_Usuarios1_idx (id_usuario),
  CONSTRAINT fk_Solicitudes_Usuarios1 FOREIGN KEY (id_usuario) REFERENCES usuarios (id),
  CONSTRAINT fk_Solicitudes_Vacantes1 FOREIGN KEY (id_vacante) REFERENCES vacantes (id)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

