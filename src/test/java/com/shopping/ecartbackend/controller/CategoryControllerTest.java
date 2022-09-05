package com.shopping.ecartbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.ecartbackend.common.ApiResponse;
import com.shopping.ecartbackend.domain.CategoryModel;
import com.shopping.ecartbackend.model.Category;
import com.shopping.ecartbackend.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

    @InjectMocks
    CategoryController categoryController = new CategoryController();

    @Mock
    CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }


    @Test
    public void addCategory_returnCategoryObject() throws Exception {
        Category category = getCategoryObject();
        String url = "/shopping/category/add/";
        String inputJson = mapRequestToJson(category);
        when(categoryService.addCategory(any(CategoryModel.class))).thenReturn(category);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        verify(categoryService,timeout(1)).addCategory(any(CategoryModel.class));
    }

    @Test
    public void getAllCategories_returnAllCategories() throws Exception {
        ArrayList<CategoryModel> categories = (ArrayList<CategoryModel>) getListOfCategoryModels();
        when(categoryService.getAllCategories()).thenReturn(categories);
        mockMvc.perform(MockMvcRequestBuilders.get("/shopping/category/")).andExpect(status().isOk());
        assertEquals(categories.size(),2);
        assertEquals(categories.get(0).getCategoryName(),"category2");
    }

    @Test
    public void getCategory_returnCategory() throws Exception {
        Category category = getCategoryObject();
        when(categoryService.getCategory(anyInt())).thenReturn(category);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/shopping/category/{id}",4))
                .andExpect(status().isOk());
        assertEquals(category.getCategoryName(),"category1");
        assertEquals(category.getDescription(),"description1");

    }

    @Test
    public void updateCategory_returnReturnCategory() throws Exception {
        Category category= getCategoryObject();
        String url = "/shopping/category/update/{id}";
        String inputJson = mapRequestToJson(category);
        when(categoryService.updateCategory(any(CategoryModel.class),anyInt())).thenReturn(category);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(url,3)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(categoryService,times(1)).updateCategory(any(CategoryModel.class),anyInt());

    }

    @Test
    public void deleteCategory_shouldDeleteCategory() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/shopping/category/delete/{id}",4))
                .andExpect(status().isOk());
        verify(categoryService,times(1)).deleteCategory(anyInt());
    }

    @Test
    public void deleteAllCategories_shouldDeleteAllCategories() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/shopping/category/delete"))
                .andExpect(status().isOk());
        verify(categoryService,times(1)).deleteAllCategory();
    }



    public String mapRequestToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private List<CategoryModel> getListOfCategoryModels(){
        List<CategoryModel> categoryModelList = new ArrayList<>();
        CategoryModel category1 = getCategoryModel();
        CategoryModel category2 = getCategoryModel();
        categoryModelList.add(category1);
        categoryModelList.add(category2);
        return categoryModelList;
    }

    private Category getCategoryObject(){
        Category category = new Category();
        category.setCategoryName("category1");
        category.setCategoryId(1);
        category.setDescription("description1");
        category.setImageURL("www.imageurl.com");
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

    private ResponseEntity<ApiResponse> getApiResponse(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("message");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

}
