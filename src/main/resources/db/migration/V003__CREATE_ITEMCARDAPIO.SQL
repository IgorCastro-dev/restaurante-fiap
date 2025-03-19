CREATE TABLE item_cardapio (
    id_item_cardapio BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    disponivel_apenas_no_local BOOLEAN NOT NULL,
    caminho_foto VARCHAR(255)
);