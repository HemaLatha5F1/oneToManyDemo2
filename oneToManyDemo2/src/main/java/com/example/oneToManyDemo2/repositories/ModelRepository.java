package com.example.oneToManyDemo2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.oneToManyDemo2.entities.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer>{

}
