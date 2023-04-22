package it.epicode.progettoSettimanale.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.epicode.progettoSettimanale.auth.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {

	public boolean existsBySerialCode(Long code);
	public Device findBySerialCode(Long code);
	
	@Query(value = "SELECT u.deviceList FROM User u WHERE u.id = :id")
    public List<Device> findDeviceListByUserId(Long id);
}
