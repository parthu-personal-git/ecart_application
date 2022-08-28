package com.shopping.ecartbackend.dao;

import com.shopping.ecartbackend.model.Category;
import com.shopping.ecartbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
//    @Query("SELECT c FROM category c WHERE c.category_id = id")
//    Category findById(@Param("id") int id);


}
