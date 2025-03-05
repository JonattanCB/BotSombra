package com.irc.BotSombra.service;

import com.irc.BotSombra.dto.categoria.Categoriadto;
import com.irc.BotSombra.model.Categoria;
import com.irc.BotSombra.model.Estado;
import com.irc.BotSombra.repository.CategoriaRepository;
import com.irc.BotSombra.utils.Colors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class CategoriaService {
    private Categoria cate;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria saveCategoria(Categoriadto categoriadto) {
        var nombre = categoriadto.nombre();
        if (existCategoria(nombre)){
            cate = getCategoriaByNombre(nombre);
        }else{
            cate = new Categoria(categoriadto);

            categoriaRepository.save(cate);
        }
        return  cate;
    }

    public Categoria editCategoria(Categoriadto categoriadto){
        var nombre = categoriadto.nombre();
        if (existCategoria(nombre)){
            cate = getCategoriaByNombre(nombre);
        }else {
            cate.ActualizarDatos(categoriadto);
        }
        return cate;
    }

    public String editStatus(String nombre){
        if (existCategoria(nombre)){
            cate = getCategoriaByNombre(nombre);
            if (cate.getEstado() == Estado.ACTIVE){
                cate.desactivarCategoria();
                return Colors.verde +"[ + ]"+Colors.gris+" Categoria "+nombre+" esta"+ Colors.azulOscuro+" desactivada";
            }else{
                cate.activerCategoria();
                return Colors.verde +"[ - ]"+Colors.gris+" Categoria "+nombre+" esta"+Colors.azulOscuro+" activada";
            }
        }else{
            return Colors.rojo +"[ ! ] Categoria no encontrada";
        }
    }

    public List<Categoria> categoriaList(){
        return categoriaRepository.findAll();
    }

    public Categoria getCategoriaByNombre(String nombre){
        return categoriaRepository.getCategoriaByNombre(nombre);
    }

    private boolean existCategoria(String nombre){
        var exist = categoriaRepository.existsByNombre(nombre);
        return exist;
    }

}
