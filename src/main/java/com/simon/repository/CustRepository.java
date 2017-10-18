package com.simon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.simon.entity.Cust;

@Repository
public interface CustRepository extends JpaRepository<Cust, Long> {
    @Query("select c from Cust c where UPPER(c.firstName) like %:name% or UPPER(c.lastName) like %:name%")
    List<Cust> findByNameLikeIgnoreCase(@Param("name") String name);
}
