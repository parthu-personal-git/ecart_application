package com.shopping.ecartbackend.service;

import com.shopping.ecartbackend.dao.CategoryRepository;
import com.shopping.ecartbackend.dao.ProductRepository;
import com.shopping.ecartbackend.domain.ProductModel;
import com.shopping.ecartbackend.model.Category;
import com.shopping.ecartbackend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product findById(int id) {
        Product product = productRepository.findById(id).get();
        //if product is empty : Throw an exception product doesn't exist
        //ProductModel productModel =convertProductToProductModel(product);
        return product;
    }

    public ProductModel addProduct(ProductModel productModel) {
        Category category = categoryRepository.findById(productModel.getCategoryId()).get();
        //if this is empty : throw an exception that category doesn't exist
        Product product = new Product();
        product.setProductName(productModel.getProductName());
        product.setCategory(category);
        product.setDescription(productModel.getDescription());
        product.setImageURL(productModel.getImageURL());
        product.setPrice(productModel.getPrice());
        productRepository.save(product);
        //since product has Category has entity, we need to show category id: so converting back to base object;
        ProductModel productModelObject = convertProductToProductModel(product);
        return productModelObject;
    }

    public List<ProductModel> getAllProductObjects() {
        List<Product> productList = productRepository.findAll();
        List<ProductModel> productModelList = new ArrayList<>();
        for (Product product : productList) {
            ProductModel productModel = convertProductToProductModel(product);
            productModelList.add(productModel);
        }
        return productModelList;
    }

    public ProductModel updateProductObject(ProductModel productModel, int id) {
        Product product = productRepository.findById(id).get();
        Category category = categoryRepository.findById(product.getCategory().getCategoryId()).get();
        //throw an exception if product is empty
        //throw an exception if category is empty
        product.setProductName(productModel.getProductName());
        product.setCategory(category);
        product.setPrice(productModel.getPrice());
        product.setDescription(productModel.getDescription());
        product.setImageURL(productModel.getImageURL());
        productRepository.save(product);
        ProductModel productModelObj = convertProductToProductModel(product);
        return productModelObj;

    }

    public ProductModel deleteProductObject(int id) {
        Product product = productRepository.findById(id).get();
        ProductModel productModel = convertProductToProductModel(product);
        //if product doesn't exist , throw an error
        productRepository.deleteById(id);
        System.out.println("deleted product with id : " + id);
        return productModel;
    }

    public void deleteAllProductObjects() {
        productRepository.deleteAll();
        System.out.println("deleted all prodcuts");

    }


    public ProductModel convertProductToProductModel(Product product) {
        ProductModel productModel = new ProductModel();
        productModel.setProductId(product.getId());
        productModel.setProductName(product.getProductName());
        productModel.setCategoryId(product.getCategory().getCategoryId());
        productModel.setPrice(product.getPrice());
        productModel.setDescription(product.getDescription());
        productModel.setImageURL(product.getImageURL());
        return productModel;
    }

}
