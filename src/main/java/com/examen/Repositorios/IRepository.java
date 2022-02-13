package com.examen.Repositorios;

import java.util.List;

public interface IRepository <P> {

    void InsertOne(P pojo);
    void InsertMany(List<P> pojos);
    void DeleteOne(int id);
    void UpdateOne(int id, double precio);
    List<P> ListAll();
    List<P> FindFiltered(double min);
    P Find(int id);

}
