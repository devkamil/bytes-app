package pl.devkamil.app.service;

import org.riversun.bigdoc.bin.BigFileSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.devkamil.app.model.DataFromViewDTO;
import sun.misc.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.riversun.finbin.BinaryUtil.getBytes;


@Service
public class ByteService {

    String everything = "";

//    @Autowired
//    private BigFileSearcher bigFileSearcher;

//    @Autowired
//    private KMPMath kmpMath;



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





        int i=0;
    List<File> fileList = new ArrayList<>();
    public List<File> searchFilesInGivenDirectoryWithGivenExtension(String path, String extension){

        File dir = new File(path);
//        File[] fileList = new File[]{};
//        List<File> fileList = new ArrayList<>();


        if(dir.isDirectory()){
            for(File file: dir.listFiles()){
                searchFilesInGivenDirectoryWithGivenExtension(file.getAbsolutePath(), extension);
            }
        }else if(dir.isFile() && dir.getName().endsWith(extension)){
//            fileList[i]=dir;
            fileList.add(dir);
//            i++;
        }

        return fileList;




//        File[] files = null;
//        return dir.listFiles((dir1, filename) -> filename.endsWith("." + dataFromViewDTO.getFileExtension()));

//     if(secondPath == ""){

//
//        if(dir.isDirectory()){
//            files = dir.listFiles(new FileFilter() {
//                @Override
//                public boolean accept(File pathname) {
//                    return pathname.isDirectory() || pathname.getName().toLowerCase().endsWith(extension);
//                }
//            });
//        }
//        return files;





//        return dir.listFiles(new FilenameFilter() {
//            @Override
//            public boolean accept(File dir, String filename) {
//
//                String pathFile = dir + "\\" + filename;
//
//
//                    if (new File(pathFile).isDirectory()) {
//                        String dir2 = pathFile;
//                        searchFilesInGivenDirectoryWithGivenExtension(dir2, extension);
//                    }
////                tabBol.add(filename.endsWith("." + extension));
////                System.out.println(tabBol);
//                return filename.endsWith("." + extension);
//
//            }
//        });







//     }else{
//         return dir.listFiles(new FilenameFilter() {
//             @Override
//             public boolean accept(File secondPath, String filename) {
//
//                 String pathFile = dir + "\\" + filename;
//
//
//                 if (new File(pathFile).isDirectory()) {
//                     String dir2 = pathFile;
//                     searchFilesInGivenDirectoryWithGivenExtension(path, dir2, extension);
//                 }
////                tabBol.add(filename.endsWith("." + extension));
////                System.out.println(tabBol);
//                 return filename.endsWith("." + extension);
//
//             }
//         });
//     }

    }



    public void searchBytesInFiles(File file, String searchBytes, String outputBytes) throws IOException {
        BigFileSearcher bigFileSearcher = new BigFileSearcher();
        byte[] searchBytesTab = searchBytes.getBytes("UTF8");
        List<Long> findList = bigFileSearcher.searchBigFile(file, searchBytesTab);
        System.out.println("positions: " + file.getName() + findList);
        long numer;

        for(int i=0; i < findList.size(); i++){
            numer = findList.get(i);
            System.out.println(numer);
            replaceBytes(file, numer, outputBytes);
        }
    }


    public void replaceBytes(File file, long positionOfBytes, String outputBytes) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
        byte[] outputBytesTab = outputBytes.getBytes();
//        System.out.println(file.getName() + " ODCZYT " + randomAccessFile.seek(4));
        randomAccessFile.seek(positionOfBytes);
        System.out.println(outputBytesTab.length);
        randomAccessFile.write(outputBytesTab);
//        randomAccessFile.write(outputBytesTab, (int) positionOfBytes, ((int) positionOfBytes + outputBytesTab.length));
//        randomAccessFile.write(outputBytesTab, 2,3);

//        randomAccessFile.write(outputBytesTab, 0, 4);
    }




    public void searchAndReplaceBytes(File file, String searchBytes, String outputBytes) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        byte[] fileBytesTab = Files.readAllBytes(path);
        byte[] searchBytesTab = searchBytes.getBytes("UTF8");
        byte[] outputBytesTab = outputBytes.getBytes("UTF8");



        KMPMath kmpMath = new KMPMath();

        kmpMath.indexOf(fileBytesTab, searchBytesTab);




        try (BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {

            StringBuilder stringBuilder = new StringBuilder();
//            byte[] filesTab = bufferedReader.readLine().getBytes("UTF8");
            String line = bufferedReader.readLine();

            while(line!= null){
                if(line == searchBytes){
                    stringBuilder.append(outputBytes);
                } else{
                    stringBuilder.append(line);
                }
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }

            String all = stringBuilder.toString();
            System.out.println("toString: " + all);
            byte[] everything = stringBuilder.toString().getBytes();
            System.out.println("byte[]: " + everything + '\n' + "*************************");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
