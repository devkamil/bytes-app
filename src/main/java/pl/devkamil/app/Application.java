package pl.devkamil.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.devkamil.app.service.ByteService;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);



//        ByteService byteService = new ByteService();

//        System.out.println("Working Directory: " + System.getProperty("user.dir"));

//        byteService.readFile("FileToRead11.txt");
//        byteService.writeFile("FileToRead111.txt");

    }

}
