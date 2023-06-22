package com.devsuperior.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventService {
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {

		try {

			Event event = eventRepository.getReferenceById(id);

			copyDtoTOEntity(dto, event);

			return new EventDTO(eventRepository.save(event));

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado ao atualizar");
		}

	}
	
	private void copyDtoTOEntity(EventDTO dto, Event event) {
		
		City city = cityRepository.getReferenceById(dto.getCityId());
		
		event.setName(dto.getName());
		event.setDate(dto.getDate());
		event.setUrl(dto.getUrl());
		event.setCity(city);
		
		

	}
}
