package pl.devkamil.app.service;

import org.springframework.stereotype.Service;
import pl.devkamil.app.model.DataFromViewDTO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


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


//    public void show(DataFromViewDTO dataFromViewDTO){
//        System.out.println(dataFromViewDTO.getPathToFile());
//        System.out.println(dataFromViewDTO.getFileExtension());
//        System.out.println(dataFromViewDTO.getInputBytes());
//        System.out.println(dataFromViewDTO.getOutputBytes());
//
//
//    }


    public File[] searchFilesInGivenDirectoryWithGivenExtension(DataFromViewDTO dataFromViewDTO){
        File dir = new File(dataFromViewDTO.getPathToFile());

        return dir.listFiles((dir1, filename) -> filename.endsWith("." + dataFromViewDTO.getFileExtension()));


    }


//    public List<File> listFiles() throws IOException{
//
//        String onlyPath = "D:\\GAME   OF  Thrones";
//        String completeCmd = "explorer.exe /select," + onlyPath;
//        new ProcessBuilder(("explorer.exe " + completeCmd).split(" ")).start();
//
//        List<File> filesInFolder = Files.walk(Paths.get("D:\\Intellij-workspace\\QBS\\bytes-app"))
//                .filter(Files::isRegularFile)
//                .map(Path::toFile)
//                .collect(Collectors.toList());
//
//        System.out.println(filesInFolder);
//        return filesInFolder;
//    }

//    public void listFiles() throws IOException{
//        Files.walk(Paths.get("D:\\Intellij-workspace\\QBS\\bytes-app"))
//                .filter(Files::isRegularFile)
//                .forEach(System.out::println);
//    }

}
