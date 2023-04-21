package it.epicode.progettoSettimanale.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.progettoSettimanale.auth.entity.Device;
import it.epicode.progettoSettimanale.auth.repository.DeviceRepository;
import jakarta.persistence.EntityExistsException;

@Service
public class DeviceService {

	@Autowired DeviceRepository deviceRepository;
	
	public Device addDevice(Device dev) {
		if(deviceRepository.existsBySerialCode(dev.getSerialCode())) {
			throw new EntityExistsException("The device alredy exists");
		}
		deviceRepository.save(dev);
		System.out.println("Device saved");
		return dev;
	}
	
}
