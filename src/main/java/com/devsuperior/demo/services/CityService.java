package com.devsuperior.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.services.exceptions.DataBaseException;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {
	
	@Autowired
	CityRepository cityRepository;

	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
		List<City> result = cityRepository.findAll(Sort.by("name"));
		return result.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());

	}


	@Transactional
	public CityDTO insert(CityDTO dto) {
		City city = new City();
		copyDtoTOEntity(dto, city);
		return new CityDTO(cityRepository.save(city));
	}
	
	private void copyDtoTOEntity(CityDTO dto, City city) {
		city.setName(dto.getName());
		city.getEvents().clear();


	}


	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {

		if (!cityRepository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}

		try {
			cityRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Falha de integridade referencial");
		}
	}
}
