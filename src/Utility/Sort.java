/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Legia
 */
package Utility;

import Model.Product;
import java.util.Comparator;

public class Sort {


    public static Comparator<Product> sortByName = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getName().compareToIgnoreCase(p2.getName());
        }
    };
    public static Comparator<Product> sortByModelYear = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            return Integer.compare(p1.getModelYear(), p2.getModelYear());
        }
    };

    public static Comparator<Product> sortByPriceThenName = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            // First compare by price (descending)
            int priceComparison = Double.compare(p2.getListPrice(), p1.getListPrice());
            if (priceComparison != 0) {
                return priceComparison;
            }
            // If prices are the same, reuse sortByName for ascending order by name
            return sortByName.compare(p1, p2);
        }
    };
}


