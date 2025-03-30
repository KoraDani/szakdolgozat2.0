package hu.okosotthon.back;

import hu.okosotthon.back.Mqtt.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableAsync
@EnableScheduling
public class BackApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

	/*@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}*/

	@Autowired
	private MqttService mqttService;

	@Override
	public void run(String... args) throws Exception {
		mqttService.connectToBroker();
//		mqttService.subscribeToTopic("tele/home/szoba/temp/SENSOR");
//		mqttService.subscribeToTopic("tele/home/szoba/plug/SENSOR");
//		mqttService.subscribeToTopic("stat/home/szoba/switch/RESULT");
//		mqttService.subscribeToTopic("stat/home/szoba/switch/RESULT");
	}
}
