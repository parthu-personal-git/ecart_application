package com.shopping.ecartbackend.dao;

import com.shopping.ecartbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
//    @Query("SELECT c FROM category c WHERE c.category_id = id")
//    Category findById(@Param("id") int id);


}
