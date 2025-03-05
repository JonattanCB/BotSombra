package com.irc.BotSombra.model;

import com.irc.BotSombra.dto.clasificacion.Clasificacionobjdto;
import com.irc.BotSombra.dto.sombra.Sombrasavedto;
import com.irc.BotSombra.repository.UsuarioRespository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sombra")
@Entity(name = "sombra")
@EqualsAndHashCode(of = "sombraID")
public class Sombra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sombraID;

    private String nombre;

    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoriaID")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nivelID")
    private Nivel nivel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuarioid")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public Sombra(Sombrasavedto sombradto, Clasificacionobjdto clasificacionobjdto) {
        this.nombre = sombradto.nombre();
        this.descripcion = sombradto.Description();
        this.categoria = clasificacionobjdto.categoria();
        this.nivel = clasificacionobjdto.nivel();
        this.usuario = sombradto.usuario();
        this.estado = Estado.ACTIVE;
    }

}
