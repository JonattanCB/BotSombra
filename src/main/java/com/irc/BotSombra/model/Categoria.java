package com.irc.BotSombra.model;

import com.irc.BotSombra.dto.categoria.Categoriadto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categoria")
@Entity(name = "categoria")
@EqualsAndHashCode(of = "categoriaID")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoriaID;

    private String nombre;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public Categoria(Categoriadto categoriadto){
        this.nombre = categoriadto.nombre();
        this.descripcion = categoriadto.descripcion();
        this.estado = Estado.ACTIVE;
    }

    public void ActualizarDatos(Categoriadto categoriadto){
        if (categoriadto.nombre() != null && !categoriadto.nombre().isEmpty()){
            this.nombre = categoriadto.nombre();
        }
        if (categoriadto.descripcion() != null && !categoriadto.descripcion().isEmpty()){
            this.descripcion = categoriadto.descripcion();
        }
    }

    public void desactivarCategoria(){
        this.estado = Estado.INACTIVE;
    }

    public void activerCategoria(){
        this.estado = Estado.ACTIVE;
    }
}
