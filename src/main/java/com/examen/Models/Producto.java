package com.examen.Models;

import org.bson.codecs.pojo.annotations.BsonId;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Producto {

    @Id
    @BsonId
    private int id;
    @Embedded
    private Articulo articulo;
    @ElementCollection
    private List<String> categorias = new ArrayList<>();

    public Producto() {}

    public Producto(int id, Articulo articulo, List<String> categorias) {
        this.id = id;
        this.articulo = articulo;
        this.categorias = categorias;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Articulo getArticulo() {
        return articulo;
    }
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
    public List<String> getCategorias() {
        return categorias;
    }
    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", articulo=" + articulo +
                ", categorias=" + categorias +
                '}';
    }
}

