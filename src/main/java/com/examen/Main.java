package com.examen;

import com.examen.Models.Articulo;
import com.examen.Models.Producto;
import com.examen.Repositorios.IRepository;
import com.examen.Repositorios.RepositorioProductos;
import com.examen.Repositorios.RepositorioProductosODB;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static IRepository repositorioProductos;
    static boolean shouldExit = false;
    public static void main(String[] args) {

        System.out.println(
                "1.- Uso de MongoDB\n" +
                "2.- Uso de ObjectDB\n"
                );
        int repositorySelect = scanner.nextInt();
        scanner.nextLine();
        repositorioProductos = (repositorySelect == 1) ? new RepositorioProductos() : new RepositorioProductosODB();

        do {
            PrintMenu();
            int menuselector = scanner.nextInt();

            switch (menuselector){
                case 1:
                    MenuListAll();
                    break;
                case 2:
                    MenuInsertOne();
                    break;
                case 3 :
                    MenuInsertMany();
                    break;
                case 4:
                    MenuDelete();
                    break;
                case 5 :
                    MenuUpdatePrice();
                    break;
                case 6 :
                    MenuFindOne();
                    break;
                case 7 :
                    MenuFindGreaterPrice();
                    break;
                case 8 :
                    shouldExit = true;
                    break;
                default:
                    break;
            }
        } while (!shouldExit);
    }
    private static void PrintMenu() {
        System.out.println(
                "---------------------------MENU------------------------\n" +
                        "1.- Listar todos los Productos\n" +
                        "2.- Insertar un producto\n" +
                        "3.- Insertar varios productos\n" +
                        "4.- Borrar un producto por su Id\n" +
                        "5.- Actualizar precio de un producto\n" +
                        "6.- Buscar producto por su Id\n" +
                        "7.- Buscar productos mayores que el precio introducido\n" +
                        "8.- Salir del programa\n" +
                        "----------------------------------------------------------"
        );
    }
    private static void MenuListAll() {
        System.out.println("Lista de todos los productos\n");
        repositorioProductos.ListAll().forEach(System.out::println);
    }
    private static void MenuInsertOne(){
        System.out.println("Insertando Nuevo Producto");
        Producto producto = new Producto();
        Articulo articulo = new Articulo();
        List<String> categorias = new ArrayList<>();
        System.out.println("Introduce el id del articulo");
        articulo.setId(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Descripción del articulo");
        articulo.setDescripcion(scanner.nextLine());
        System.out.println("Precio del articulo");
        articulo.setPrecio(scanner.nextDouble());
        scanner.nextLine();
        System.out.println("Introduce el id del producto");
        producto.setId(scanner.nextInt());
        scanner.nextLine();
        System.out.println("nombre de la primera categoria del producto");
        categorias.add(scanner.nextLine());
        System.out.println("nombre de la segunda categoria del producto");
        categorias.add(scanner.nextLine());
        producto.setCategorias(categorias);
        producto.setArticulo(articulo);
        repositorioProductos.InsertOne(producto);
    }
    private static void MenuInsertMany(){
        int exitfromInsertMany;
        List<Producto> ProductosMany = new ArrayList<>();
        do{
            System.out.println("Insertando Nuevo Producto");
            Producto producto = new Producto();
            Articulo articulo = new Articulo();
            List<String> categorias = new ArrayList<>();

            System.out.println("Introduce el id del articulo");
            articulo.setId(scanner.nextInt());
            scanner.nextLine();

            System.out.println("Descripción del articulo");
            articulo.setDescripcion(scanner.nextLine());

            System.out.println("Precio del articulo");
            articulo.setPrecio(scanner.nextDouble());
            scanner.nextLine();

            System.out.println("Introduce el id del producto");
            producto.setId(scanner.nextInt());
            scanner.nextLine();

            System.out.println("nombre de la primera categoria del producto");
            categorias.add(scanner.nextLine());
            System.out.println("nombre de la segunda categoria del producto");
            categorias.add(scanner.nextLine());

            producto.setCategorias(categorias);
            producto.setArticulo(articulo);
            ProductosMany.add(producto);

            System.out.println(
                    "¿Quieres seguir introduciendo Productos?\n" +
                            "1.- Si\n" +
                            "2.- No");
            exitfromInsertMany = scanner.nextInt();
            scanner.nextLine();
        }
        while(exitfromInsertMany == 1);
        repositorioProductos.InsertMany(ProductosMany);
    }
    private static void MenuDelete(){
        System.out.println("Introduce el id del producto a eliminar");
        repositorioProductos.DeleteOne(scanner.nextInt());
        scanner.nextLine();
    }
    private static void MenuUpdatePrice() {
        System.out.println("Introduce el id del Producto a modificar");
        int idnumber = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Introduce el precio del Producto a modificar");
        repositorioProductos.UpdateOne(idnumber, scanner.nextDouble());
    }
    private static void MenuFindOne() {
        System.out.println("Introduce el Id del producto que se quiere buscar");
        System.out.println(repositorioProductos.Find(scanner.nextInt()).toString());
        scanner.nextLine();
    }
    private static void MenuFindGreaterPrice() {
        System.out.println("Introduce el precio minimo");
        repositorioProductos.FindFiltered(scanner.nextInt()).forEach(System.out::println);
        scanner.nextLine();
    }
}