package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.FoodObj;
import com.example.demo.service.Service;

@RestController
public class Controller {

	Service serv = new Service();
	FoodObj fObj = new FoodObj();
    
    
    @GetMapping("/viewList")
    public List<FoodObj> viewList() 
    {
    	return serv.getList();
    }
    
    @PostMapping("/add") 
    public void add(@RequestBody FoodObj fObj ) 
    {
    	serv.addItem(fObj);
    }
    
    @DeleteMapping("/delete/{Item}")
    public void delete(@PathVariable("Item") FoodObj fObj ) {
    	serv.deleteItem(fObj);
    }
 
}