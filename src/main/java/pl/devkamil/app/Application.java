package pl.devkamil.app;

import pl.devkamil.app.service.ByteService;

public class Application {


    public static void main(String[] args) {

        ByteService byteService = new ByteService();

        System.out.println("Working Directory: " + System.getProperty("user.dir"));

        byteService.readFile();

    }

}
