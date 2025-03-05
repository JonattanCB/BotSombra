package com.irc.BotSombra.model;

import com.irc.BotSombra.dto.nivel.Niveldto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nivel")
@Entity(name = "nivel")
@EqualsAndHashCode(of = "nivelID")
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nivelID;

    private String tipo;

    private int poder;

    public Nivel(Niveldto dto) {
        this.tipo = dto.tipo();
        this.poder = dto.poder();
    }

    public void editpoder(int poder){
        this.poder = poder;
    }
}
