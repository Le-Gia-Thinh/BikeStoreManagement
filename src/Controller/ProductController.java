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
import Model.Brand;
import Model.Category;
import Model.I_List;
import Model.Product;
import Utility.Form;
import Utility.Sort;
import Utility.Tools;
import View.Display;
import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
import java.util.*;

public class ProductController extends TreeMap<String , Product > implements I_List {
    //private File file;
    private Map<String, Brand> brandMap;
    private Map<String, Category> categoryMap;

    public ProductController() {
       // this.file = new File("products.txt");
        this.brandMap = new HashMap<>();
        this.categoryMap = new HashMap<>();
    }
    @Override
    public void add() {
        while (true) {
            String id = Form.validId("Input Product Id");
            if (this.containsKey(id)) {
                System.out.println("Duplicate product id!!!");
                continue;
            }

            String name = Tools.inputString("Input Product Name");
            String brandId = Tools.inputString("Input Brand ID");
            String categoryId = Tools.inputString("Input Category ID");
            int modelYear = Form.validYear("Input Model Year");
            double listPrice = Tools.inputDouble("Input Value");


            Brand brand = handleBrand(brandId);
            Category category = handleCategory(categoryId);


            if (brand == null || category == null) {
                System.out.println("Cannot add product due to invalid brand or category.");
                continue;
            }

            // Create a new product
            Product product = new Product(id, name, brandId, categoryId, modelYear, listPrice);
            this.put(id, product);
            System.out.println("Added Successfully!!!");

            if (!Tools.confirmYesNo("Do you want to continue to add new products (Y/N)?")) {
                break;
            }
        }
    }

    private Brand handleBrand(String brandId) {
        if (!brandMap.containsKey(brandId)) {
            String brandName = Tools.inputString("Input Brand Name: ");
            Brand newBrand = new Brand(brandName, brandId);
            brandMap.put(brandId, newBrand);
            return newBrand;
        } else {
            System.out.println("Brand ID already exists. Cannot add or update this brand.");
            return null;
        }
    }

    private Category handleCategory(String categoryId) {
        if (!categoryMap.containsKey(categoryId)) {
            String categoryName = Tools.inputString("Input Category Name: ");
            Category newCategory = new Category(categoryId, categoryName);
            categoryMap.put(categoryId, newCategory);
            return newCategory;
        } else {
            System.out.println("Category ID already exists. Cannot add or update this category.");
            return null;
        }
    }


    @Override
    public void update() {
        String id = Tools.inputString("Input Product ID to update: ");
        Product product = this.get(id);

        if (product == null) {
            System.out.println("Product does not exist.");
        } else {
            String newName = Tools.updateString("Input new Product Name", product.getName());
            product.setName(newName);

            String newBrandId = Tools.updateString("Input new Brand ID", product.getBrandId());
            handleBrand(newBrandId);
            product.setBrandId(newBrandId);

            String newCategoryId = Tools.updateString("Input new Category ID", product.getCategoryId());
            handleCategory(newCategoryId);
            product.setCategoryId(newCategoryId);

            int newModelYear = Tools.updateInt("Input new Model Year", 2000, 9999, product.getModelYear());
            product.setModelYear(newModelYear);
            double newListPrice = Tools.updateDouble("Input new List Price", product.getListPrice());
            product.setListPrice(newListPrice);

            System.out.println("Updated Successfully!");
        }
    }

    @Override
    public List<Product> searchByName() {
        String searchString = Tools.inputString("Input part of Product Name to search: ");
        List<Product> foundProducts = new ArrayList<>();

        for (Product product : this.values()) {
            if (product.getName().toLowerCase().contains(searchString.toLowerCase())) {
                foundProducts.add(product);
            }
        }

        if (foundProducts.isEmpty()) {
            System.out.println("Have no any Product");
        } else {
            // Use Comparator from the Sort class to sort the product list by model year
            foundProducts.sort(Sort.sortByModelYear);
        }

        return foundProducts;
    }

    @Override
    public void printProductList(List<Product> products) {
        // Print out the products
        for (Product product : products) {
            Brand brand = brandMap.get(product.getBrandId());
            Category category = categoryMap.get(product.getCategoryId());
            System.out.printf("%s, %s, %s, %s, %d, %.2f%n",
                    product.getId(),
                    product.getName(),
                    // brand.getBrandName() would cause a NullPointerException and crash the program
                    brand != null ? brand.getBrandName() : "Unknown",
                    category != null ? category.getCategoryName() : "Unknown",
                    product.getModelYear(),
                    product.getListPrice());
        }
    }






    @Override
    public void delete() {
        String id = Tools.inputString("Input Product ID to delete: ");
        Product product = this.get(id);

        if (product == null) {
            System.out.println("Product does not exist.");
        } else {
            // Xóa thông tin brand và category liên quan
            Brand brand = brandMap.get(product.getBrandId());
            Category category = categoryMap.get(product.getCategoryId());

            if (brand != null) {
                brandMap.remove(product.getBrandId());
            }
            if (category != null) {
                categoryMap.remove(product.getCategoryId());
            }


            this.remove(id);
            System.out.println("Deleted Successfully!");


            save();
        }
    }
    //Way 1 : delete (implement in product, brand , category.class ) using str.split and add each attribute
    /*@Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("products.txt"));
                BufferedWriter brandWriter = new BufferedWriter(new FileWriter("brands.txt"));
                BufferedWriter categoryWriter = new BufferedWriter(new FileWriter("categories.txt"))) {
            for (Product product : this.values()) {
                writer.write(String.format("%s,%s,%s,%s,%d,%.2f%n",
                        product.getId(), product.getName(), product.getBrandId(),
                        product.getCategoryId(), product.getModelYear(), product.getListPrice()));
            }
            for (Brand brand : brandMap.values()) {
                brandWriter.write(String.format("%s,%s%n", brand.getBrandId(), brand.getBrandName()));
            }
            for (Category category : categoryMap.values()) {
                categoryWriter.write(String.format("%s,%s%n", category.getCategoryId(), category.getCategoryName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void print() {
        try (BufferedReader productReader = new BufferedReader(new FileReader("products.txt"));
                BufferedReader brandReader = new BufferedReader(new FileReader("brands.txt"));
                BufferedReader categoryReader = new BufferedReader(new FileReader("categories.txt"))) {

            String line;
            List<Product> productList = new ArrayList<>();
            while ((line = productReader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                String name = parts[1];
                String brandId = parts[2];
                String categoryId = parts[3];
                int modelYear = Integer.parseInt(parts[4]);
                double listPrice = Double.parseDouble(parts[5]);

                Product product = new Product(id, name, brandId, categoryId, modelYear, listPrice);
                productList.add(product);
            }

            // Load brands and categories
            while ((line = brandReader.readLine()) != null) {
                String[] parts = line.split(",");
                String brandId = parts[0];
                String brandName = parts[1];
                brandMap.put(brandId, new Brand(brandName, brandId));
            }

            while ((line = categoryReader.readLine()) != null) {
                String[] parts = line.split(",");
                String categoryId = parts[0];
                String categoryName = parts[1];
                categoryMap.put(categoryId, new Category(categoryId, categoryName));
            }

            // Print product table
            Display.printProducts(this, brandMap, categoryMap);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }*/

    /*
    Way 2
    */
    @Override
    public void save() {
        /*try {
            // Define file paths
            String basePath = "D:\\LAB211\\BikeStoreManagement\\";
            String productFilePath = basePath + "products.dat";
            String productBackupFilePath = basePath + "products_backup.dat";
            String brandFilePath = basePath + "brands.dat";
            String categoryFilePath = basePath + "categories.dat";
            String brandBackupFilePath = basePath + "brands_backup.dat";
            String categoryBackupFilePath = basePath + "categories_backup.dat";

            // Create backups of existing files
            backupFile(productFilePath, productBackupFilePath);
            backupFile(brandFilePath, brandBackupFilePath);
            backupFile(categoryFilePath, categoryBackupFilePath);*/
        try (ObjectOutputStream productOut = new ObjectOutputStream(new FileOutputStream("products.txt"));
                ObjectOutputStream brandOut = new ObjectOutputStream(new FileOutputStream("brands.txt"));
                ObjectOutputStream categoryOut = new ObjectOutputStream(new FileOutputStream("categories.txt"))) {
            // Save the product table
            productOut.writeObject(new ArrayList<>(this.values()));
            productOut.flush();
            // Save the brand table
            brandOut.writeObject(new HashMap<>(brandMap));
            brandOut.flush();
            // Save the category table
            categoryOut.writeObject(new HashMap<>(categoryMap));
            categoryOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     *
     */
    @Override
    public void print() {
        /* try {
            // Define file paths
                String basePath = "D:\\LAB211\\BikeStoreManagement\\";
                String productFilePath = basePath + "products.dat";
                String brandFilePath = basePath + "brands.dat";
                String categoryFilePath = basePath + "categories.dat";
                // doi ten phia duoi product.dat = productFilePath
            */
            try (ObjectInputStream productIn = new ObjectInputStream(new FileInputStream("products.txt"));
                ObjectInputStream brandIn = new ObjectInputStream(new FileInputStream("brands.txt"));
                ObjectInputStream categoryIn = new ObjectInputStream(new FileInputStream("categories.txt"))) {

            // Read list products
            List<Product> productList = (List<Product>) productIn.readObject();
            brandMap = (Map<String, Brand>) brandIn.readObject();
            categoryMap = (Map<String, Category>) categoryIn.readObject();
            //Sort products
                productList.sort(Sort.sortByPriceThenName);

            // Display product
            Display.printProducts(productList, brandMap, categoryMap);

            }    catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                            throw new RuntimeException(e);
                            // use try-with-resources,  don’t need to explicitly call close() because it’s handled automatically
            }
        } /*catch (Exception e) {
            e.printStackTrace();
            // if don use try catch exception must ..
            finally {
        // Manually close resources
        try {
            if (productOut != null) {
                productOut.close();
            }
            if (brandOut != null) {
                brandOut.close();
            }
            if (categoryOut != null) {
                categoryOut.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        }*/
    }

    /*private void backupFile(String originalFilePath, String backupFilePath) {
    try {
        File originalFile = new File(originalFilePath);
        if (originalFile.exists()) {
            Files.copy(Paths.get(originalFilePath), Paths.get(backupFilePath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Backup created for: " + originalFilePath);
        }
    } catch (IOException e) {
        System.out.println("Failed to create a backup for " + originalFilePath + ": " + e.getMessage());
    }
}

}*/




