package com.shopping.ecartbackend.controller;

import com.shopping.ecartbackend.common.ApiResponse;
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
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductModel productModel){
        ProductModel productModelObject = productService.addProduct(productModel);
        //if not found throw an exception that category doesn't exist
        return new ResponseEntity<>(new ApiResponse(true,"product has been created"), HttpStatus.CREATED);
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
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("id") int id, @RequestBody ProductModel productModel){
        ProductModel productModelObj = productService.updateProductObject(productModel,id);
        return new ResponseEntity<>(new ApiResponse(true,"product has been updated"),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("id") int id){
        ProductModel productModel = productService.deleteProductObject(id);
        return new ResponseEntity<>(new ApiResponse(true,"A product has been deleted"), HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponse> deleteAllProducts(){
        productService.deleteAllProductObjects();
        return new ResponseEntity<>(new ApiResponse(true,"All products have been deleted"), HttpStatus.OK);
    }
}
