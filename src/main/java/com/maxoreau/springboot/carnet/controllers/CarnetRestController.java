package com.maxoreau.springboot.carnet.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		System.out.println("Rest searchById");
		return dao.readById(id);
	}
	
	@GetMapping("/contact")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Contact> getAllContacts() { 
		System.out.println("Rest getAllContacts");
		return dao.getAll();
	}
	
	@PutMapping("/contact")
	public void updateContact(@RequestBody Contact contact) {
		System.out.println("Rest updateContact " + contact);
		dao.update(contact);
	}
	
	@DeleteMapping("/contact")
	public void deleteContacts(List<Integer> list) {
		System.out.println("Rest delete Contacts " + list);
		list.forEach((contactId) -> {
			dao.delete(contactId);
		});
	}
}
