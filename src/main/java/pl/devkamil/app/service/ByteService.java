package pl.devkamil.app.service;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ByteService {

    String filename = "FileToRead.txt";

    public void readFile() {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();

            }
            String everything = stringBuilder.toString();
            System.out.println(everything);


        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
