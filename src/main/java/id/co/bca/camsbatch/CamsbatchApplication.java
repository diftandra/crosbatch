package id.co.bca.camsbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamsbatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamsbatchApplication.class, args);
	}

	// @Bean
    // public CommandLineRunner commandLineRunner(BatchService jobLauncherService) {
    //     return args -> {
    //         jobLauncherService.runBatchJob();
    //     };
    // }

}
