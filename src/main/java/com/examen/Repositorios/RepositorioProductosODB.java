package com.examen.Repositorios;

import com.examen.Models.Producto;
import com.examen.ObjectDBUtility;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioProductosODB implements IRepository<Producto>{

    EntityManager entityManager = ObjectDBUtility.getEntityManager();

    @Override
    public void InsertOne(Producto pojo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pojo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void InsertMany(List<Producto> pojos) {
        pojos.forEach(this::InsertOne);
    }

    @Override
    public void DeleteOne(int id) {
        try {
            Query query = entityManager.createQuery("DELETE FROM Producto p WHERE p.id = ?1");
            query.setParameter(1, id);
            entityManager.getTransaction().begin();
            query.executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void UpdateOne(int id, double precio) {
        try {
            Producto producto = Find(id);
            entityManager.getTransaction().begin();
            producto.getArticulo().setPrecio(precio);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Producto> ListAll() {
        TypedQuery<Producto> productos = entityManager.createQuery("SELECT p FROM Producto p", Producto.class);
        return productos.getResultList();
    }

    @Override
    public List<Producto> FindFiltered(double min) {
        try {
            Query query = entityManager.createQuery("SELECT p FROM Producto p WHERE p.articulo.precio > ?1", Producto.class);
            query.setParameter(1, min);
            entityManager.getTransaction().begin();
            List<Producto> productos= query.getResultList();
            entityManager.getTransaction().commit();
            return productos;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Producto Find(int id) {
        try {
            return entityManager.find(Producto.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
