package com.irc.BotSombra.controllers;

import com.irc.BotSombra.dto.categoria.Categoriadto;
import com.irc.BotSombra.model.Categoria;
import com.irc.BotSombra.model.Estado;
import com.irc.BotSombra.service.CategoriaService;
import com.irc.BotSombra.utils.Colors;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CategoriaControllers {

    private Categoriadto dto;
    private Categoria cate;

    @Autowired
    private CategoriaService categoriaService;

    public String saveCategoria(String[] comando){
        if (comando.length > 2){
            String nombre = comando[1];
            String descripcion = "";
            for (int i = 2; i < comando.length; i++) {
                descripcion += comando[i] + " ";
            }
            dto = new Categoriadto(nombre, descripcion, Estado.ACTIVE);
            cate =  categoriaService.saveCategoria(dto);
        }else{
            cate = new Categoria();
        }
        return cate.getNombre();
    }

    public List<Categoria> categoriaList(){
        return categoriaService.categoriaList();
    }

    @Transactional
    public String editStatus(String[] comando){
        if (comando.length > 1){
           return  categoriaService.editStatus(comando[1]);
        }else{
            return Colors.rojo + "[ ! ] Comando incorrecto, usa el comando .helpCategoria para ver los comandos disponibles";
        }
    }

    public Categoria getCategoria(String nombre) {
        return categoriaService.getCategoriaByNombre(nombre);
    }
}
