package com.shopping.ecartbackend.service;

import com.shopping.ecartbackend.dao.CategoryRepository;
import com.shopping.ecartbackend.domain.CategoryModel;
import com.shopping.ecartbackend.exception.EmptyInputException;
import com.shopping.ecartbackend.model.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {
    @InjectMocks
    CategoryService categoryService = new CategoryService();

    @Mock
    CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

//    @Rule
//    public EmptyInputException emptyInputException ;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryService).build();
    }

    @Test
    public void addCategory_returnsCategory(){
        Category category = getCategoryObject();
        CategoryModel categoryModel= getCategoryModel();
        lenient().when(categoryRepository.save(category)).thenReturn(category);
        Category categoryObject = categoryService.addCategory(categoryModel);
        assertEquals(categoryObject.getCategoryName(),"category2");
    }

    @Test(expected = EmptyInputException.class)
    public void addCategory_throwsEmptyInputException() throws EmptyInputException{
        CategoryModel categoryModel = getEmptyCategoryModel();
        Category category = getEmptyCategory();
        lenient().when(categoryRepository.save(category)).thenThrow(new EmptyInputException("EMPTY",HttpStatus.BAD_REQUEST));
        categoryService.addCategory(categoryModel);
    }

    @Test
    public void getCategory_returnsCategory(){
        Category category = getCategoryObject();
        lenient().when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        categoryService.getCategory(anyInt());
        assertEquals(category.getCategoryName(),"category1");
    }

    @Test
    public void getAllCategories_returnsAllCategories(){
        List<Category> categoryList = getListOfCategories();
        when(categoryRepository.findAll()).thenReturn(categoryList);
        categoryService.getAllCategories();
        assertEquals(categoryList.size(),2);
        assertEquals(categoryList.get(0).getCategoryName(),"category1");
    }

    @Test
    public void updateCategory_shouldUpdateCategory(){
        Category category = getCategoryObject();
        CategoryModel categoryModel = getCategoryModel();
        lenient().when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        lenient().when(categoryRepository.save(category)).thenReturn(category);
        categoryService.updateCategory(categoryModel,anyInt());
        assertEquals(category.getCategoryName(),"category1");
    }

    @Test(expected = NoSuchElementException.class)
    public void updateCategory_throwsNoSuchElementException(){
        CategoryModel categoryModel = getCategoryModel();
        when(categoryRepository.findById(anyInt())).thenThrow(new NoSuchElementException());
        categoryService.updateCategory(categoryModel,anyInt());
    }
    
    @Test
    public void deleteCategory_shouldDeleteCategory(){
        Category category = getCategoryObject();
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        //lenient().doNothing().when(categoryRepository.findById(anyIntˀnyInt()));
//        doNothing().when(categoryRepository.findById(anyInt()));
        categoryService.deleteCategory(anyInt());
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteCategory_throwsNoSuchElementExeption(){
        Category category = getCategoryObject();
        when(categoryRepository.findById(anyInt())).thenThrow(new NoSuchElementException());
        categoryService.deleteCategory(anyInt());
    }
    
    @Test
    public void deleteAllCategory_shouldDeleteAllCategories(){
        categoryService.deleteAllCategory();
        verify(categoryRepository,times(1)).deleteAll();
    }

    private List<Category> getListOfCategories(){
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(getCategoryObject());
        categoryList.add(getCategoryObject());
        return categoryList;
    }

    private CategoryModel getEmptyCategoryModel(){
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setImageURL("www.abc.com");
        return categoryModel;
    }

    private Category getEmptyCategory(){
        Category category = new Category();
        category.setImageURL("www.abc.com");
        return category;
    }

    private CategoryModel getCategoryModel(){
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryName("category2");
        categoryModel.setId(2);
        categoryModel.setDescription("description2");
        categoryModel.setImageURL("www.imageurl2.com");
        return categoryModel;
    }

    private Category getCategoryObject(){
        Category category = new Category();
        category.setCategoryName("category1");
        category.setCategoryId(1);
        category.setDescription("description1");
        category.setImageURL("www.imageurl.com");
        return category;
    }
}
