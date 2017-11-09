package pl.devkamil.app.service;

import org.springframework.stereotype.Service;

import java.io.*;


@Service
public class ByteService {

    String everything = "";

    public String readFile(String path) {

        try (BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"))) {

            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();

            }
            everything = stringBuilder.toString();
            System.out.println(everything);



        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return everything;
    }


    public void writeFile(String path){

        try(BufferedWriter bufferedWriter =
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF8"))) {


            bufferedWriter.write(everything);

        } catch (IOException ex){
            ex.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
