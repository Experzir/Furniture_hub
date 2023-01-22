package com.web.furniturehub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.furniturehub.model.Furniture;

public interface FurnitureRepository extends JpaRepository<Furniture, Integer> {

    @Query(value = "SELECT * FROM Furniture f WHERE f.sid = :sid AND f.tid = :tid", nativeQuery = true)
    List<Furniture> findProductsByIds(@Param("sid") Integer sid, @Param("tid") Integer tid);

    @Query(value = "SELECT * FROM Furniture f WHERE f.sid = :sid ", nativeQuery = true)
    List<Furniture> findProductsBySid(@Param("sid") Integer sid);

    @Query(value = "SELECT * FROM Furniture f WHERE f.tid = :tid ", nativeQuery = true)
    List<Furniture> findProductsByTid(@Param("tid") Integer tid);
}
