/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;
/**
 *
 * @author Legia
 */


import Controller.MenuController;
import Controller.ProductController;
import Model.I_Menu;
import Model.Product;
import Utility.Tools;

import java.util.List;

public class BikeStoreManagement {
    public static void main(String[] args) {
        I_Menu menu = new MenuController();
        menu.addItem("----------------------------------------------------------------");
        menu.addItem("|                           MENU                               |");
        menu.addItem("|         1   :      Add a new product                         |");
        menu.addItem("|         2   :      Search product by name                    |");
        menu.addItem("|         3   :      Update a product                          |");
        menu.addItem("|         4   :      Delete a product                          |");
        menu.addItem("|         5   :      Save all products to file                 |");
        menu.addItem("|         6   :      Print list of products from the file      |");
        menu.addItem("|      Others :      Exit                                      |");
        menu.addItem("----------------------------------------------------------------");
        ProductController list = new ProductController();
        int choice;
        boolean exit = false;
        while (!exit) {
            menu.showMenu();
            choice = menu.getChoice();

            switch (choice) {
                case 1:
                    list.add();
                    break;

                case 2:
                    List<Product> foundProducts = list.searchByName();
                    if (!foundProducts.isEmpty()) {
                        list.printProductList(foundProducts);
                    }
                    break;
                case 3:
                    list.update();
                    break;

                case 4:
                    list.delete();
                    break;

                case 5:
                    list.save();
                    System.out.println("All products have been successfully saved to 'Product.txt'.");
                    break;

                case 6:
                    System.out.println("Printing list of products from 'Product.txt':");
                    list.print();
                    break;

                default:
                    boolean check = Tools.confirmYesNo("Do you want to exit? (Y/N)");
                    if (check) {
                        exit = true;
                    }
                    break;
            }

            if (!exit) {
                boolean goBack = Tools.confirmYesNo("Do you want to go back to the main menu (Y/N)?");
                if (!goBack) {
                    exit = true;
                }
            }
        }
        System.out.println("Exiting the program");
    }
}
