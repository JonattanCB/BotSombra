CREATE TABLE SOMBRA (
    sombraID SERIAL NOT NULL PRIMARY KEY,
    nombre  varchar(250) NOT NULL,
    descripcion varchar(250) NOT NULL,
    categoriaID   INT  not null,
    nivelID INT NOT NULL,
    usuarioID INT NOT NULL,
    estado VARCHAR(10),
    FOREIGN KEY (nivelID) REFERENCES NIVEL (nivelID),
    foreign key (usuarioID) references  USUARIO (usuarioID),
    FOREIGN KEY (categoriaID) REFERENCES CATEGORIA (categoriaID)
)