package com.shopping.ecartbackend.controller;

import com.shopping.ecartbackend.dao.CategoryRepository;
import com.shopping.ecartbackend.domain.ProductModel;
import com.shopping.ecartbackend.model.Product;
import com.shopping.ecartbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/add")
    public ResponseEntity<ProductModel> addProduct(@RequestBody ProductModel productModel){
        ProductModel product = productService.addProduct(productModel);
        //if not found throw an exception that category doesn't exist
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getProduct(@PathVariable(value = "id") int id){
        Product product = productService.findById(id);
        //needed it to convert it back to model to show only Product entity
        ProductModel productModel = productService.convertProductToProductModel(product);
        return new ResponseEntity<>(productModel,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        List<ProductModel> productList = productService.getAllProductObjects();
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable("id") int id, @RequestBody ProductModel productModel){
        ProductModel productModelObj = productService.updateProductObject(productModel,id);
        return new ResponseEntity<>(productModelObj,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductModel> deleteProduct(@PathVariable("id") int id){
        ProductModel productModel = productService.deleteProductObject(id);
        return new ResponseEntity<>(productModel, HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<ProductModel> deleteAllProducts(){
        productService.deleteAllProductObjects();
        return new ResponseEntity<>(new ProductModel(), HttpStatus.OK);
    }
}
