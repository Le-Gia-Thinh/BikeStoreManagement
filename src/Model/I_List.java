/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Legia
 */
import java.util.List;

public interface I_List {



    void add();

    List<Product> searchByName ();

    void printProductList (List<Product> products);

    void update();

    void delete();

    void save();

    void print();



}
