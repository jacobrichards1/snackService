package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dao.FoodObj;

public class Service {
	List<FoodObj> list = new ArrayList<FoodObj>();
	
	public void addItem(FoodObj fObj)
	{
		this.list.add(fObj);
	}
	
	public void deleteItem(FoodObj fObj)
	{
		this.list.remove(fObj);
	}


	public List<FoodObj> getList() 
	{
		return this.list;
	}
}