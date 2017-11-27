package pl.devkamil.app.service;


import org.springframework.stereotype.Service;
import pl.devkamil.app.model.DataFromViewDTO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;


@Service
public class ByteService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private ArrayList<File> fileList = new ArrayList<>();

    public ArrayList<File> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<File> fileList){
        this.fileList = fileList;
    }

    public void clearList(){
        getFileList().clear();
    }

    public List<File> searchFilesInDirectory(String path, String extension){


        File dir = new File(path);

        if(dir.isDirectory()){
            for(File file: dir.listFiles()){
                searchFilesInDirectory(file.getAbsolutePath(), extension);
            }
        }else if(dir.isFile() && dir.getName().endsWith(extension)){
            getFileList().add(dir);
        }
        return getFileList();
    }


    public void iterateFiles(List<File> tab, DataFromViewDTO dataFromViewDTO) throws IOException {
        for (int i = 0; i < tab.size(); i++) {
            searchBytesInFiles(tab.get(i), dataFromViewDTO.getInputBytes(), dataFromViewDTO.getOutputBytes());
        }
        clearList();



    }



    public void searchBytesInFiles(File file, String searchBytes, String outputBytes) throws IOException {

        Path path = Paths.get(file.getAbsolutePath());
        byte[] fileBytesTab = Files.readAllBytes(path);
        byte[] searchBytesTab = searchBytes.getBytes("UTF8");
        LinkedList<Integer> findList = new LinkedList<>();


        int fileBytesTabLength = fileBytesTab.length;
        int searchBytesTabLength = searchBytesTab.length;

        int i = 0;
        int j;

        while(i < fileBytesTabLength - searchBytesTabLength + 1){
            j = 0;
            while (( j < searchBytesTabLength) && (searchBytesTab[j] == fileBytesTab[i+j])){
                j++;
            }
            if ( j == searchBytesTabLength){
                System.out.println(i);
                findList.add(i);
                i++;
            }
            i++;
        }

        System.out.println(findList);

        replaceBytes(file, findList, searchBytes, outputBytes);



    }




    public void replaceBytes(File file, List<Integer> findList, String inputBytes, String outputBytes) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        byte[] fileBytesTab = Files.readAllBytes(path);
        byte[] searchBytesTab = inputBytes.getBytes("UTF8");
        byte[] outputBytesTab = outputBytes.getBytes("UTF8");
        List<byte[]> byteArrays = new LinkedList<>();
        int beginWrite = 0;


        if(findList.size() != 0) {

            for (int i = 0; i < findList.size(); i++) {
                byteArrays.add(Arrays.copyOfRange(fileBytesTab, beginWrite, findList.get(i)));
                byteArrays.add(Arrays.copyOfRange(outputBytesTab, 0, outputBytesTab.length));
                beginWrite = findList.get(i) + searchBytesTab.length;
            }
            byteArrays.add(Arrays.copyOfRange(fileBytesTab, beginWrite, fileBytesTab.length));


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            for (int i = 0; i < byteArrays.size(); i++) {
                byteArrayOutputStream.write(byteArrays.get(i));
            }

            byte[] allTab = byteArrayOutputStream.toByteArray();

            System.out.println(allTab);


            saveBytesTabToFile(file, allTab);

        }

    }



    public void saveBytesTabToFile(File file, byte[] bytesTabToSave){

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        String currentDateTime = DATE_TIME_FORMATTER.format(zonedDateTime);
        String filenameWithoutExtension = FilenameUtils.removeExtension(file.getName());
        String fileExtension = FilenameUtils.getExtension(file.getName());
        String currentPath = System.getProperty("user.dir");

        System.out.println(currentPath);
        System.out.println(currentPath+"\\folder\\saved\\"+ filenameWithoutExtension+currentDateTime+"."+fileExtension);
        System.out.println(currentDateTime);
        System.out.println(filenameWithoutExtension);

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(currentPath + "\\folder\\saved\\"
                + filenameWithoutExtension + currentDateTime + "." + fileExtension, "rw")){

            randomAccessFile.write(bytesTabToSave);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }













}
