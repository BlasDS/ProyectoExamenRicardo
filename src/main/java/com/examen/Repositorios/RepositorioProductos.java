package com.examen.Repositorios;

import com.examen.Models.Articulo;
import com.examen.Models.Producto;
import com.examen.MongoUtility;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;

public class RepositorioProductos implements IRepository<Producto>{

    @Override
    public void InsertOne(Producto pojo) {
        MongoUtility.getMongoDatabase().getCollection("productos").insertOne(ProductoToDocument(pojo));
    }

    @Override
    public void InsertMany(List<Producto> pojos) {
        pojos.forEach(this::InsertOne);
    }

    @Override
    public void DeleteOne(int id) {
        MongoUtility.getMongoDatabase().getCollection("productos").deleteOne(eq("_id", id));
    }

    @Override
    public void UpdateOne(int id, double precio) {
        Bson bsonUpdate = Updates.set("articulo.precio", precio);
        Bson bsonQuery = eq("_id", id);
        MongoUtility.getMongoDatabase().getCollection("productos").updateOne(bsonQuery, bsonUpdate);
    }

    @Override
    public List<Producto> ListAll() {
        List<Producto> productos = new ArrayList<>();
        MongoCursor<Document> mongoCursor = MongoUtility.getMongoDatabase().getCollection("productos").find().iterator();
        for (MongoCursor<Document> it = mongoCursor; it.hasNext(); ) {
            Document d = it.next();
            productos.add(DocumentToProducto(d));
        }
        return productos;
    }

    @Override
    public List<Producto> FindFiltered(double min) {
        List<Producto> productos = new ArrayList<>();
        MongoCursor<Document> mongoCursor = MongoUtility.getMongoDatabase().getCollection("productos").find(gt("articulo.precio", min)).iterator();
        for (MongoCursor<Document> it = mongoCursor; it.hasNext(); ) {
            Document d = it.next();
            productos.add(DocumentToProducto(d));
        }
        return productos;
    }

    @Override
    public Producto Find(int id) {
        return DocumentToProducto(Objects.requireNonNull(MongoUtility.getMongoDatabase().getCollection("productos").find(eq("_id", id)).first()));
    }

    public Document ArticuloToDocument(Articulo articulo) {
        return new Document()
                .append("_id", articulo.getId())
                .append("descripcion", articulo.getDescripcion())
                .append("precio", articulo.getPrecio());
    }
    public Articulo DocumentToArticulo(Document document){
        Articulo articulo = new Articulo();
        articulo.setId(document.getInteger("_id"));
        articulo.setDescripcion(document.getString("descripcion"));
        articulo.setPrecio(document.getDouble("precio"));
        return articulo;
    }
    public Document ProductoToDocument(Producto producto) {
       return new Document()
               .append("_id", producto.getId())
               .append("articulo", ArticuloToDocument(producto.getArticulo()))
               .append("categorias", producto.getCategorias());
    }
    public Producto DocumentToProducto(Document document) {
        Producto producto = new Producto();
        List<String> cat = document.get("categorias", List.class);
        producto.setId(document.getInteger("_id"));
        producto.setArticulo(DocumentToArticulo(document.get("articulo", Document.class)));
        producto.setCategorias(cat);
        return producto;
    }
}
