package it.epicode.progettoSettimanale.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.progettoSettimanale.auth.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {

	public boolean existsBySerialCode(Long code);
}
