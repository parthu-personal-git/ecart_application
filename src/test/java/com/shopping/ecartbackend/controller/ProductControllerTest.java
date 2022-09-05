package com.shopping.ecartbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.ecartbackend.domain.ProductModel;
import com.shopping.ecartbackend.model.Category;
import com.shopping.ecartbackend.model.Product;
import com.shopping.ecartbackend.common.Utility;
import com.shopping.ecartbackend.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
    @InjectMocks
    ProductController productController = new ProductController();

    @Mock
    ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    Utility utility;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void addCategory_returnProductObject() throws Exception {
        ProductModel productModel = getProductModelObject();
        String url = "/shopping/product/add";
        String inputJson = mapRequestToJson(productModel);
        when(productService.addProduct(any(ProductModel.class))).thenReturn(productModel);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        verify(productService,timeout(1)).addProduct(any(ProductModel.class));
    }

    @Test
    public void getProduct_returnProductObject() throws Exception {
        Product product = getProduct();
        when(productService.findById(anyInt())).thenReturn(product);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/shopping/product/{id}",4))
                .andExpect(status().isOk());
        assertEquals(product.getCategory().getCategoryName(),"category1");
        assertEquals(product.getProductName(),"product1");
    }

    @Test
    public void getAllProducts_returnProductModelList() throws Exception {
        List<ProductModel> productModelList = getProductModelList();
        when(productService.getAllProductObjects()).thenReturn(productModelList);
        mockMvc.perform(MockMvcRequestBuilders.get("/shopping/product/")).andExpect(status().isOk());
        assertEquals(productModelList.size(),2);
        assertEquals(productModelList.get(0).getProductId(),1);
    }

    @Test
    public void updateProduct_returnProductModelObject() throws Exception {
        ProductModel productModel = getProductModelObject();
        String url = "/shopping/product/update/{id}";
        String inputJson = mapRequestToJson(productModel);
        when(productService.updateProductObject(any(ProductModel.class),anyInt())).thenReturn(productModel);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(url,3)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(productService,times(1)).updateProductObject(any(ProductModel.class),anyInt());
    }

    @Test
    public void deleteProduct_shouldDeleteProduct() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/shopping/product/{id}",4))
                .andExpect(status().isOk());
        verify(productService,times(1)).deleteProductObject(anyInt());
    }

    @Test
    public void deleteAllProducts_shouldDeleteAllProducts() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/shopping/product/"))
                .andExpect(status().isOk());
        verify(productService,times(1)).deleteAllProductObjects();
    }

    private List<ProductModel> getProductModelList(){
        List<ProductModel> productModelList = new ArrayList<>();
        productModelList.add(getProductModelObject());
        productModelList.add(getProductModelObject());
        return productModelList;
    }
    private ProductModel getProductModelObject(){
        ProductModel productModel = new ProductModel();
        productModel.setProductId(1);
        productModel.setProductName("product1");
        productModel.setDescription("description1");
        productModel.setCategoryId(1);
        productModel.setImageURL("www.imageurl.com");
        productModel.setPrice(0.00);
        return productModel;
    }

    private Category getCategoryObject(){
        Category category = new Category();
        category.setCategoryName("category1");
        category.setCategoryId(1);
        category.setDescription("description1");
        category.setImageURL("www.imageurl.com");
        return category;
    }

    private Product getProduct(){
        Product product = new Product();
        product.setPrice(0.00);
        product.setCategory(getCategoryObject());
        product.setId(1);
        product.setImageURL("www.imageurl.com");
        product.setDescription("description");
        product.setProductName("product1");
        return product;
    }

    public String mapRequestToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}
