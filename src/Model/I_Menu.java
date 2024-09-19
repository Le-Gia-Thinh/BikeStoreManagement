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
public interface I_Menu {


    void addItem(String s);

    int getChoice ();

    void showMenu();

    boolean confirm_YesNo (String welcome);



}
