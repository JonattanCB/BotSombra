package com.irc.BotSombra.model;

import com.irc.BotSombra.dto.clasificacion.Clasificacionobjdto;
import com.irc.BotSombra.dto.usuario.Usuariodto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
@Entity(name = "usuario")
@EqualsAndHashCode(of = "usuarioID")
public class Usuario {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int usuarioID;

   private String nick;

   private String descripcion;

   private String clave;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "categoriaID")
   private Categoria categoria;

   @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nivelID")
    private Nivel nivel;

   @Enumerated(EnumType.STRING)
   private Estado estado;


   public Usuario(Usuariodto usuariodto, Clasificacionobjdto clasificacionobjdto) {
         this.nick = usuariodto.nick();
         this.descripcion = usuariodto.descripcion();
         this.clave = usuariodto.clave();
         this.categoria = clasificacionobjdto.categoria();
         this.nivel = clasificacionobjdto.nivel();
         this.estado = Estado.ACTIVE;
   }

   public void updateEstado(Estado estado) {
      this.estado = estado;
   }

}
