package com.maxoreau.springboot.carnet.controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.maxoreau.springboot.carnet.dao.daoGenerique;
import com.maxoreau.springboot.carnet.models.Contact;


@Controller
public class CarnetController {
	
	ModelAndView mav;
	
	@Autowired
	daoGenerique<Contact> dao;
	
	@PostConstruct
	public void init(){
		System.out.println("      ----------- CarnetController initialized -----------");
		mav = new ModelAndView();
	}
	
	public ModelAndView mavConstruct(){
		mav.clear();
		mav.setViewName("carnet");
		mav.addObject("contact", new Contact());
		return mav;		
	}
	
	@GetMapping("/search")
	public ModelAndView getByString(@RequestParam(value="search", required=false) String search){
		if (search == null) {
			search = "";
		}
		mavConstruct();
		mav.addObject("contacts", dao.readByName(search));
		return mav;
	}
	
	@GetMapping("/open")
	public ModelAndView openEmptyCarnet(Model model){
		mavConstruct();
		return mav;
	}
	/*
	@GetMapping("/carnet/del")
	public ModelAndView delContact(@RequestParam(value="contactId", required=false) Integer id){
		mavConstruct();
		if (id == null) {
			mav.addObject("contacts", dao.getAll());
			return mav;
		} else {
			dao.delete(id);
			mav.addObject("contacts", dao.getAll());
			return mav;
		}
	}
	*/
	@PostMapping("/carnet")
	public ModelAndView addContact(@ModelAttribute Contact contact){
		dao.create(contact);
		mavConstruct();
		mav.addObject("contacts", dao.getAll());
		return mav;
	}
	
	

}
