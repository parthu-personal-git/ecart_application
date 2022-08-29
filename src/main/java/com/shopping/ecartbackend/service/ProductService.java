package com.shopping.ecartbackend.service;

import com.shopping.ecartbackend.dao.CategoryRepository;
import com.shopping.ecartbackend.dao.ProductRepository;
import com.shopping.ecartbackend.domain.ProductModel;
import com.shopping.ecartbackend.exception.CategoryNotFoundException;
import com.shopping.ecartbackend.exception.ProductNotFoundException;
import com.shopping.ecartbackend.model.Category;
import com.shopping.ecartbackend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProductService {

    static Logger logger = Logger.getLogger(String.valueOf(ProductService.class));

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product findById(int id) {
        logger.info("starting the : findById service");
        Product product = productRepository.findById(id).get();
        //if product is empty : Throw an exception product doesn't exist
        if(product.getId() == 0 || product.getProductName() == null){
            logger.log(Level.WARNING, "no product found  with given id : findById service");
            throw new ProductNotFoundException(id);
        }
        //ProductModel productModel =convertProductToProductModel(product);
        logger.info("ending the : findById service");
        return product;
    }

    public ProductModel addProduct(ProductModel productModel) {
        logger.info("starting the : addProduct service");
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
        logger.info("ending : addProduct service");
        return productModelObject;
    }

    public List<ProductModel> getAllProductObjects() {
        logger.info("starting the : getAllProductObjects service");
        List<Product> productList = productRepository.findAll();
        List<ProductModel> productModelList = new ArrayList<>();
        for (Product product : productList) {
            ProductModel productModel = convertProductToProductModel(product);
            productModelList.add(productModel);
        }
        logger.info("ending : geAllProductObjects service");
        return productModelList;
    }

    public ProductModel updateProductObject(ProductModel productModel, int id) {
        logger.info("starting the : updateProductObject service");
        Product product = productRepository.findById(id).get();
        //throw an exception if product is empty
        if(product.getId() == 0 || product.getProductName() == null){
            logger.log(Level.WARNING, "no product found  with given id : findById service");
            throw new ProductNotFoundException(id);
        }
        Category category = categoryRepository.findById(product.getCategory().getCategoryId()).get();
        //throw an exception if category is empty
        if(category.getCategoryId() == 0 || category.getCategoryName() == null){
            logger.log(Level.WARNING, "no category found  with given id : getCategory service");
            throw new CategoryNotFoundException(category.getCategoryId());
        }
        product.setProductName(productModel.getProductName());
        product.setCategory(category);
        product.setPrice(productModel.getPrice());
        product.setDescription(productModel.getDescription());
        product.setImageURL(productModel.getImageURL());
        productRepository.save(product);
        ProductModel productModelObj = convertProductToProductModel(product);
        logger.info("ending the : updateProductObject service");
        return productModelObj;

    }

    public ProductModel deleteProductObject(int id) {
        logger.info("starting the : deleteProductObject service");
        Product product = productRepository.findById(id).get();
        //if product doesn't exist , throw an error
        if(product.getId() == 0 || product.getProductName() == null){
            logger.log(Level.WARNING, "no product found  with given id : findById service");
            throw new ProductNotFoundException(id);
        }
        ProductModel productModel = convertProductToProductModel(product);
        productRepository.deleteById(id);
        logger.info("ending the : deleteProductObject service");
        return productModel;
    }

    public void deleteAllProductObjects() {
        logger.info("starting the : deleteAllProductObjects service");
        productRepository.deleteAll();
        logger.info("ending the : deleteAllProductObject service");
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
