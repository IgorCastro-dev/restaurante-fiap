CREATE TABLE restaurante (
    id_restaurante BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    tipo_cozinha VARCHAR(100) NOT NULL,
    horario_funcionamento VARCHAR(100) NOT NULL,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);
