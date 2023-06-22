package com.devsuperior.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.services.EventService;

@RestController
@RequestMapping(value = "/events")
public class EventController {
	
	@Autowired
	EventService eventService;
	
	
	@PutMapping("/{id}")
	public ResponseEntity<EventDTO>  update ( @PathVariable Long id,  @RequestBody EventDTO dto) {
		 dto  = eventService.update(id, dto);
		return ResponseEntity.ok(dto);
	}	
		

}
