package it.epicode.progettoSettimanale.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.progettoSettimanale.auth.entity.Device;
import it.epicode.progettoSettimanale.auth.entity.EDeviceStatus;
import it.epicode.progettoSettimanale.auth.entity.EDeviceType;
import it.epicode.progettoSettimanale.auth.repository.DeviceRepository;
import it.epicode.progettoSettimanale.auth.service.DeviceService;
import jakarta.persistence.EntityNotFoundException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/devices")
public class DeviceController {

	@Autowired DeviceRepository deviceRepo;
	@Autowired DeviceService deviceService;
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> findAllDevices() {
		return new ResponseEntity<>(deviceRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> findDeviceById(@PathVariable Long id){
		Device d = deviceRepo.findById(id).get();
		if(d == null) {
			throw new EntityNotFoundException();
		}
		return new ResponseEntity<>(d, HttpStatus.OK);
	}
	
	@GetMapping("/serial/{code}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> findDeviceBySerialNumber(@PathVariable Long code){
		Device d = deviceRepo.findBySerialCode(code);
		if(d == null) {
			throw new EntityNotFoundException();
		}
		return new ResponseEntity<>(d, HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createDevice(@RequestBody Device device){
		if(device.getSerialCode() < 10000 || device.getSerialCode() > 20000) {
			return new ResponseEntity<>("Serial code should be > then 10000 and < then 20000", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(deviceRepo.save(device), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteDeviceById(@PathVariable Long id){
		return new ResponseEntity<>(deviceService.deleteDeviceById(id), HttpStatus.OK);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<?> putDevice(@RequestBody Device device){
		Device d = deviceRepo.findById(device.getId()).get();
		if( d.getSerialCode().intValue() != device.getSerialCode().intValue()
				|| !d.getType().equals(device.getType())
				) {
			return new ResponseEntity<>("You can't edit type or serial code", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(deviceRepo.save(device), HttpStatus.OK);
	}
	
}
