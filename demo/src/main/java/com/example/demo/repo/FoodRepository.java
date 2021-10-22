package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.dao.FoodObj;

public interface FoodRepository extends CrudRepository<FoodObj, Integer>{
	Optional<FoodObj> findByTitle(String title);
}
