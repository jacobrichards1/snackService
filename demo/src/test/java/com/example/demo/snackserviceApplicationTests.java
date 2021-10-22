package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.annotation.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.example.demo.dao.FoodObj;
import com.example.demo.repo.FoodRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
@AutoConfigureMockMvc
class snackserviceApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
    MockMvc mvc;

	@Autowired
	FoodRepository foodRepo;
	
	ObjectMapper mapper = new ObjectMapper();
	
	FoodObj food = new FoodObj();
	
	List<FoodObj> foodList= new ArrayList<FoodObj>();
	
	public String getJSON(String path) throws Exception {
        Path paths = Paths.get(path);
        return new String(Files.readAllBytes(paths));
    }
	
	private void createMockData() throws Exception {
        String foodStr = getJSON("src/test/resources/oneFood.json");
        FoodObj fObj = mapper.readValue(foodStr, FoodObj.class);
        this.foodRepo.save(fObj);
        
	}
	
	private void createManyMockData() throws Exception {
        String foodStr = getJSON("src/test/resources/manyFoods.json");
        TypeReference<List<FoodObj>> foodLst = new TypeReference<List<FoodObj>>() {
        };
        
        List<FoodObj> jsonToFood = mapper.readValue(foodStr, foodLst);
        foodRepo.saveAll(jsonToFood);
        
	}
	
	@Test
	void testGetEmptyList() throws Exception{
		this.mvc.perform(get("/viewList")).andExpect(status().isOk()).andExpect(content().json("[]"));
	}
	
	@Test
	
	@Rollback
	void testAddFood() throws Exception {
		String foodStr = getJSON("src/test/resources/oneFood.json");
		FoodObj fObj = mapper.readValue(foodStr, FoodObj.class);
		
		RequestBuilder req = post("/snacks").contentType(MediaType.APPLICATION_JSON).content(foodStr);
		
		this.mvc.perform(req).andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is(fObj.getName())))
			.andExpect(jsonPath("$.brand", is(fObj.getBrand())))
			.andExpect(jsonPath("$.calories", is( fObj.getCalories())))
			.andExpect(jsonPath("$.description", is(fObj.getDescription())));
	}
	
	@Test
	
	@Rollback
	void testGetMultipleFood() throws Exception {
		createManyMockData();
		List<FoodObj> fLst = (List<FoodObj>) this.foodRepo.findAll();
		FoodObj fObj = fLst.get(0);
		FoodObj fObj2 = fLst.get(2);
		
		this.mvc.perform(get("/snacks")).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name", is(fObj.getName())))
			.andExpect(jsonPath("$[0].brand", is(fObj.getBrand())))
			.andExpect(jsonPath("$[0].calories", is( fObj.getCalories())))
			.andExpect(jsonPath("$[0].description", is(fObj.getDescription())))
			.andExpect(jsonPath("$[1].name", is(fObj2.getName())))
			.andExpect(jsonPath("$[1].brand", is(fObj2.getBrand())))
			.andExpect(jsonPath("$[1].calories", is( fObj2.getCalories())))
			.andExpect(jsonPath("$[1].description", is(fObj2.getDescription())));
	}
	
	
}
