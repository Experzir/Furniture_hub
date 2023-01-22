package com.web.furniturehub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.furniturehub.model.CategoryFurniture;


public interface CateFurRepository extends JpaRepository<CategoryFurniture, Integer> {

    @Query(value = "SELECT * FROM CategoryFurniture cf WHERE cf.cid = :cid ", nativeQuery = true)
    List<CategoryFurniture> findItemsByfid(@Param("cid") Integer cid);
}
