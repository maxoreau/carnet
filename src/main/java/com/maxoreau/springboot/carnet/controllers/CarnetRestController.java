package com.maxoreau.springboot.carnet.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maxoreau.springboot.carnet.dao.daoGenerique;
import com.maxoreau.springboot.carnet.models.Contact;


@RestController
public class CarnetRestController {
	
	@Autowired
	daoGenerique<Contact> dao;
	
	@GetMapping("/contact/id-{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Contact searchById(@PathVariable("id") Integer id) { 
		return dao.readById(id);
	}
	
	@RequestMapping(value="/contact", method=RequestMethod.PUT)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateContact(Contact contact) {
		System.out.println("Rest updateContact " + contact);
		dao.update(contact);
	}
}
