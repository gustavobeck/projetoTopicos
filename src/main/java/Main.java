/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import modelo.AnimalDAO;
import modelo.Cliente;
import modelo.ClienteDAO;

/**
 * @author gubec
 */
public class Main {
    public static void main(final String[] args) {
//        final Cliente c1 = new Cliente(1, "Gustavo", "Artur Nogueira", "1999999999", "00000-000", "xxxx@gmail.com");
//
//        final Animal a1 = new Animal(1, "Laica", 10, 1);
//        final Animal a2 = new Animal(2, "Safira", 5, 1);
//
//        c1.addAnimal(a1);
//        c1.addAnimal(a2);
//
//        System.out.println(c1);
//
//        final List<Animal> animais = c1.getAnimais();
//        final Animal a3 = new Animal(3, "    ", 5, 0);
//        animais.add(a3);
//
//        System.out.println(c1);
//        System.out.println(animais);


        //ClienteDAO.getInstance().create("Gustavo", "Artur Nogueira", "1999999999", "00000-000", "xxxx@gmail.com");
//
        final Cliente c1 = ClienteDAO.getInstance().retrieveById(1);
//        System.out.println(c1);

//        System.out.println(ClienteDAO.getInstance().retrieveAll());

//        AnimalDAO.getInstance().create("Safira", 2006, "Femea", 1, c1);
//        System.out.println(AnimalDAO.getInstance().retrieveAll());
        AnimalDAO.getInstance().create("Apolo", 2006, "Macho", 1, c1);

        System.out.println(AnimalDAO.getInstance().retrieveByIdCliente(c1.getId()));
    }

}
