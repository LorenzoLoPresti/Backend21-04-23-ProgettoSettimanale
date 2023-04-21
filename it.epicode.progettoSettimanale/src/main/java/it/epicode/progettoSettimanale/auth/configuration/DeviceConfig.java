package it.epicode.progettoSettimanale.auth.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.github.javafaker.Faker;

import it.epicode.progettoSettimanale.auth.entity.Device;
import it.epicode.progettoSettimanale.auth.entity.EDeviceStatus;
import it.epicode.progettoSettimanale.auth.entity.EDeviceType;

@Configuration
public class DeviceConfig {

	@Bean
	@Scope("prototype")
	public Device createDevice(EDeviceType type) {
		Faker f = Faker.instance(new Locale("it-IT"));
		return Device.builder()
				.status(EDeviceStatus.AVAILABLE)
				.type(type)
				.serialCode(f.number().numberBetween(10000l, 20000l))
				.build();
	}
}
