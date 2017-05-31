package com.maxoreau.springboot.carnet.controllers;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxoreau.springboot.carnet.dao.daoGenerique;
import com.maxoreau.springboot.carnet.models.Contact;

@RestController
public class CarnetRestController {
	

	@Autowired
	daoGenerique<Contact> dao;
	
	@GetMapping("/contact/id{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Contact searchById(@PathParam("id") Integer id) {
		return dao.readById(id);
	}
	
	@PutMapping("/contact/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Contact> updateContact(Contact contact) {
		dao.update(contact);
		return dao.getAll();
	}

}
