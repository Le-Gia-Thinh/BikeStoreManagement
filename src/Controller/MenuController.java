/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Legia
 */
import Model.I_Menu;
import Utility.Tools;


import java.util.ArrayList;


public class MenuController extends ArrayList< String > implements I_Menu {


    public MenuController () {
        super();
    }
    @Override
    public void addItem(String s) {
        this.add(s) ;
    }



    @Override
    public int getChoice() {
    return Tools.inputInt( " Please input your choice : ", 1, 9 );

    }
    @Override
    public void showMenu() {
    for(String s: this){
        System.out.println(s);
    }
    }

    @Override
    public boolean confirm_YesNo(String welcome) {
        return Tools.confirmYesNo( welcome );
    }


}

