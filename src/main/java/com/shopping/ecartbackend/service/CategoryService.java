package com.shopping.ecartbackend.service;

import com.shopping.ecartbackend.dao.CategoryRepository;
import com.shopping.ecartbackend.domain.CategoryModel;
import com.shopping.ecartbackend.exception.EcartExceptionHandler;
import com.shopping.ecartbackend.exception.EmptyInputException;
import com.shopping.ecartbackend.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CategoryService {

    static Logger logger = Logger.getLogger(String.valueOf(CategoryService.class));

    @Autowired
    private CategoryRepository categoryRepository;


    private EcartExceptionHandler ecartExceptionHandler = null;

    public Category addCategory(CategoryModel categoryModel){
        logger.info("starting the : addCategory service");
        if(categoryModel.getCategoryName() == null || categoryModel.getImageURL() == null){
            throw new EmptyInputException("input fields are empty ", HttpStatus.BAD_REQUEST);
        }

        //check if the category already exists :  throw an exception if is already there
        Category category = new Category();
        category.setCategoryName(categoryModel.getCategoryName());
        category.setDescription(categoryModel.getDescription());
        category.setImageURL(categoryModel.getImageURL());
        //adding this line for testcase
        Category categoryObject = categoryRepository.save(category);
        logger.info("ending the service : addCategory service");
        return category;
    }

    public Category getCategory(int categoryId){
            logger.info("starting the : getCategory service");
            Category category = categoryRepository.findById(categoryId).get();
            logger.info("ending the service : getCategory service");
            return category;
    }

    public List<CategoryModel> getAllCategories(){
        logger.info("starting the : getAllCategories service");
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
        logger.info("ending the service : getAllCategories service");
        return categoryModels;
    }

    public Category updateCategory(CategoryModel categoryModel,int id) {
        logger.info("starting the : updateCategory service");
        Category category = categoryRepository.findById(id).get();
        Category categoryObj = new Category();
        categoryObj.setCategoryId(id);
        categoryObj.setCategoryName(categoryModel.getCategoryName());
        categoryObj.setImageURL(categoryModel.getImageURL());
        categoryObj.setDescription(categoryModel.getDescription());
        categoryRepository.save(categoryObj);
        logger.info("ending the service : updateCategory service");
        return  categoryObj;
    }

    public void deleteCategory(int id) {
        logger.info("starting the : deleteCategory service");
        Category category = categoryRepository.findById(id).get();
        categoryRepository.deleteById(id);
        logger.info("ending the service : deleteCategory service");
    }

    public void deleteAllCategory() {
        logger.info("starting the : deleteAllCategory service");
        categoryRepository.deleteAll();
        logger.info("ending the service : deleteAllCategory service");
    }

}
