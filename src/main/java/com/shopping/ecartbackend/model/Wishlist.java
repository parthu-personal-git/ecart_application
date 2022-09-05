//package com.shopping.ecartbackend.model;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Table(name = "wishlist")
//public class Wishlist {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    int id;
//    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
//    User user;
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    Product product;
//    @Column(name = "created_date")
//    Date createdDate;
//
//    public Wishlist(){};
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
////    public User getUser() {
////        return user;
////    }
////
////    public void setUser(User user) {
////        this.user = user;
////    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }
//}
