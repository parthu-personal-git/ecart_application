package com.shopping.ecartbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.ecartbackend.common.ApiError;
import com.shopping.ecartbackend.common.ApiResponse;
//import com.shopping.ecartbackend.common.Utility;
import com.shopping.ecartbackend.dao.CategoryRepository;
import com.shopping.ecartbackend.domain.CategoryModel;
import com.shopping.ecartbackend.model.Category;
import com.shopping.ecartbackend.service.CategoryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@RunWith(SpringJUnit4ClassRunner.class)

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

    @InjectMocks
    CategoryController categoryController;

    @MockBean
    CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

//    @Autowired
//    private Utility utility;

//    @Before
//    public void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
//    }


//    @Test
//    public void addCategory_returnCategoryObject() throws Exception {
//        Category category = getCategory();
//        String url = "/shopping/category/add/";
//        String inputJson = utility.mapRequestToJson(category);
//        String inputJson = mapRequestToJson(category);
//        when(categoryService.addCategory(any(CategoryModel.class))).thenReturn(category);
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post(url)
//                .accept(MediaType.ALL).content(inputJson)
//                .contentType(MediaType.ALL);
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//        MockHttpServletResponse response = result.getResponse();
//        String outputJson = response.getContentAsString();
//        assertThat(outputJson).isEqualTo(inputJson);
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        verify(category.getCategoryName().equals("description1"));
//        ResponseEntity<ApiResponse> apiResponseResponseEntity = getApiResponse();
        //when(categoryController.addCategory(any(CategoryModel.class))).thenReturn(apiResponseResponseEntity);
//        verify(apiResponseResponseEntity.getStatusCode().toString().equals("HttpStatus.OK"));
//    }

//    @Test
//    public void getAllCategories_returnAllCategories() throws Exception {
//        ArrayList<Integer> arrayList = new ArrayList<>();
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/shopping/category/")
//                ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().);
//    }

    @Test
    public void getCategory_returnCategory() throws Exception {
        Category category = getCategory();
        when(categoryService.getCategory(anyInt())).thenReturn(category);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/shopping/category/6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value("category1"))
                .andExpect(status().isOk());
    }

    public String mapRequestToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private Category getCategory(){
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
