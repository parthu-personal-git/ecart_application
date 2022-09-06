package com.shopping.ecartbackend.service;

import com.shopping.ecartbackend.dao.OrderRepository;
import com.shopping.ecartbackend.domain.OrderModel;
import com.shopping.ecartbackend.model.Order;
import com.shopping.ecartbackend.model.Product;
import com.shopping.ecartbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class OrderService {

    static Logger logger = Logger.getLogger(String.valueOf(OrderService.class));

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    public Order addOrderObject(OrderModel orderModel){
        logger.info("starting the : addOrderObject service");
        Order order = new Order();
        Product product = productService.findById(orderModel.getProductId());
        User user = userService.getUserById(orderModel.getUserId());
//        int orderNumber = 1;
        //created_date, order_number, price, product_id, quantity, user_id
        order.setId(orderModel.getId());
        order.setCreatedDate(new Date());
        order.setOrderNumber(orderModel.getOrderNumber());
        //to get the total price multiply it with quantity
        order.setPrice(product.getPrice() * orderModel.getQuantity());
        order.setQuantity(orderModel.getQuantity());
        order.setUser(user);
        order.setProduct(product);
        orderRepository.save(order);
        logger.info("ending the : addOrderObject service");
        return order;
    }

    public Order getOrderById(int id){
        logger.info("starting the : getOrderById service");
        Order order = orderRepository.findById(id).get();
//        OrderModel orderModel = new OrderModel();
//        orderModel.setOrderNumber(order.getOrderNumber());
//        orderModel.setId(id);
//        orderModel.setQuantity(order.getQuantity());
//        orderModel.setProductId(order.getProduct().getId());
//        orderModel.setUserId(order.getUser().getUserId());
        logger.info("ending the : getOrderById service");
        return order;
    }

    public List<Order> getAllOrders(){
        logger.info("starting the : getAllOrders service");
        List<Order> orderList = orderRepository.findAll();
        logger.info("ending the : getAllOrders service");
        return orderList;
    }

    public Order updateOrderById(int id, OrderModel orderModel) {
        logger.info("starting the : updateOrderById service");
        Order order = orderRepository.findById(id).get();
        //In case product is deleted or user account is deleted :
        //exception handler will throw errors if either of them fails
        Product product = productService.findById(orderModel.getProductId());
        User user = userService.getUserById(orderModel.getUserId());
        order.setPrice(orderModel.getQuantity() * product.getPrice());
        order.setUser(user);
        order.setProduct(product);
        //there should be another filed called updated_date
        order.setCreatedDate(new Date());
        order.setOrderNumber(orderModel.getOrderNumber());
        order.setQuantity(orderModel.getQuantity());
        orderRepository.save(order);
        logger.info("ending the : updateOrderById service");
        return  order;
    }

    public void deleteAnOrderById(int id) {
        logger.info("starting the : deleteAnOrderById service");
        Order order = orderRepository.findById(id).get();
        orderRepository.deleteById(id);
        logger.info("ending the : deleteAnOrderById service");
    }

    public void deleteAllOrders() {
        logger.info("starting the : deleteAllOrders service");
        orderRepository.deleteAll();
        logger.info("ending the : deleteAllOrders service");
    }
}

