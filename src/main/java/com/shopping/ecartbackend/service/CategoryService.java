package com.shopping.ecartbackend.service;

import com.shopping.ecartbackend.dao.CategoryRepository;
import com.shopping.ecartbackend.domain.CategoryModel;
import com.shopping.ecartbackend.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(CategoryModel categoryModel){
        Category category = new Category();
        category.setCategoryName(categoryModel.getCategoryName());
        category.setDescription(categoryModel.getDescription());
        category.setImageURL(categoryModel.getImageURL());
        categoryRepository.save(category);
        return category;
    }

    public Category getCategory(int categoryId){
//        Category category = categoryRepository.findById(categoryId).get();
        //if it is empty : throw an exception : category not found
//        Category category = categoryRepository.findByCategoryId(categoryId);
        Category category = categoryRepository.findById(categoryId).get();
        return category;
    }

    public List<CategoryModel> getAllCategories(){
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryModel> categoryModels = new ArrayList<>();
        for(Category catg : categoryList){
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setId(catg.getCategoryId());
            categoryModel.setCategoryName(catg.getCategoryName());
            categoryModel.setDescription(catg.getDescription());
            categoryModel.setImageURL(catg.getImageURL());
            categoryModels.add(categoryModel);
        }
        return categoryModels;
    }

    public Category updateCategory(CategoryModel categoryModel,int id) {
        Category category = categoryRepository.findById(id).get();
        //throw an exception if the category doesn't exist
        Category categoryObj = new Category();
        categoryObj.setCategoryId(id);
        categoryObj.setCategoryName(categoryModel.getCategoryName());
        categoryObj.setImageURL(categoryModel.getImageURL());
        categoryObj.setDescription(categoryModel.getDescription());
        categoryRepository.save(categoryObj);
        return  categoryObj;
    }

    public void deleteCategory(int id) {
        Category category = categoryRepository.findById(id).get();
        //throw an exception if id doesn't exist
        categoryRepository.deleteById(id);
        //replace with LOGGER statements here
        System.out.println("deleted category with id  : "  + id);
    }

    public void deleteAllCategory() {
        categoryRepository.deleteAll();
        //replace with LOGGER statements here
        System.out.println("deleted all categories");
    }

}
