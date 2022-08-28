package com.shopping.ecartbackend.controller;

import com.shopping.ecartbackend.dao.CategoryRepository;
import com.shopping.ecartbackend.domain.CategoryModel;
import com.shopping.ecartbackend.model.Category;
import com.shopping.ecartbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shopping/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody CategoryModel category){
        Category categoryObj = categoryService.addCategory(category);
        return new ResponseEntity<>(categoryObj, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable(value = "id") int categoryId){
        Category category = categoryService.getCategory(categoryId);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryModel>> getAllCategories(){
        List<CategoryModel> categoryList = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryList,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") int id,@RequestBody CategoryModel categoryModel){
        Category category = categoryService.updateCategory(categoryModel,id);
        return new ResponseEntity<>(category,HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") int id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new Category(),HttpStatus.OK);
    }

    //Not tested
    @DeleteMapping("/delete")
    public ResponseEntity<List<Category>> deleteAllCategories(){
        categoryService.deleteAllCategory();
        return new ResponseEntity<>(new ArrayList<Category>(), HttpStatus.OK);
    }


}
