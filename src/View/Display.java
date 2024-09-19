/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Legia
 */
package View;

import Model.Brand;
import Model.Category;
import Model.Product;

import java.util.List;
import java.util.Map;

public class Display {
    public static void printProducts(List<Product> productMap, Map<String, Brand> brandMap, Map<String, Category> categoryMap)
    /*
    //way1 for print following str split and add each attribute
    public static void printProducts(Map<String, Product> productMap, Map<String, Brand> brandMap, Map<String, Category> categoryMap*/{
        System.out.printf("+-----------------+----------------------+-----------------+----------------------+------------+------------+%n");
        System.out.printf("| %-15s | %-20s | %-15s | %-20s | %-10s | %-10s |%n", "ID", "Name", "Brand Name", "Category Name", "Model Year", "List Price");
        System.out.printf("+-----------------+----------------------+-----------------+----------------------+------------+------------+%n");
        //way 1
        //for (Product product : productMap.values())
        for (Product product : productMap) {
            Brand brand = brandMap.get(product.getBrandId());
            Category category = categoryMap.get(product.getCategoryId());
            System.out.printf("| %-15s | %-20s | %-15s | %-20s | %-10d | %-10.2f |%n",
                    product.getId(),
                    product.getName(),
                    brand.getBrandName(),
                    category.getCategoryName(),
                    product.getModelYear(),
                    product.getListPrice());
        }

        System.out.printf("+-----------------+----------------------+-----------------+----------------------+------------+------------+%n");

    }
}
