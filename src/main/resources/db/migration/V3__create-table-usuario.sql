CREATE TABLE USUARIO (
    usuarioID SERIAL NOT NULL PRIMARY KEY,
    nick VARCHAR(20) NOT NULL,
    descripcion VARCHAR(250) NOT NULL,
    clave VARCHAR(250) NOT NULL,
    categoriaID INT NOT NULL,
    nivelID INT NOT NULL,
    estado varchar(10),
    FOREIGN KEY (nivelID) REFERENCES NIVEL (nivelID),
    FOREIGN KEY (categoriaID) REFERENCES categoria(categoriaID))
