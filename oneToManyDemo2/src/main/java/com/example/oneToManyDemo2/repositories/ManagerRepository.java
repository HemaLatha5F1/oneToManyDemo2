package com.example.oneToManyDemo2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.oneToManyDemo2.entities.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer>{

}
