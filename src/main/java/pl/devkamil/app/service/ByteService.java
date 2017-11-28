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

/**
 * This is class which is provide a main methods of program: searching, replacing, and saving to new file
 */

@Service
public class ByteService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    // This is list of all files in given directory
    private ArrayList<File> fileList = new ArrayList<>();

    public ArrayList<File> getFileList() {
        return fileList;
    }

    // A method that clears "ArrayList<File> fileList" after each loop of program
    public void clearList(){
        getFileList().clear();
    }

    /**
     * This method is searching a files with given extension in given directory (and subdirectories)
     * @param path This is path to directory which we want to searching
     * @param extension This is the extension of files that we want to find
     * @return List of all files with given extension in given directory and subdirectories
     */
    public List<File> searchFilesInDirectory(String path, String extension){
        File anotherFileInDirectory = new File(path);

        if(anotherFileInDirectory.isDirectory()){
            for(File file: anotherFileInDirectory.listFiles()){
                searchFilesInDirectory(file.getAbsolutePath(), extension);
            }
        }else if(anotherFileInDirectory.isFile() && anotherFileInDirectory.getName().endsWith(extension)){
            getFileList().add(anotherFileInDirectory);
        }
        return getFileList();
    }


    /**
     * This method is iterating all over the files from List, invoking "searchBytesInFiles" method for each file, and
     * finally clearing the List of files at the end of the loop of program.
     * @param listFiles List of all files with given extension in given directory and subdirectories
     * @param dataFromViewDTO Object from the 'view', containing path to directory, file extension,
     *                        searched sequence of bytes and output sequence of bytes
     * @throws IOException
     */

    public void iterateFiles(List<File> listFiles, DataFromViewDTO dataFromViewDTO) throws IOException {
        for (int i = 0; i < listFiles.size(); i++) {
            Path path = Paths.get(listFiles.get(i).getAbsolutePath());
            byte[] fileBytesTab = Files.readAllBytes(path);

            List <Integer> listOfSearchingBytesInFile = searchBytesInFiles
                    (fileBytesTab, dataFromViewDTO.getInputBytes(), dataFromViewDTO.getOutputBytes());

            if(listOfSearchingBytesInFile.size() != 0) {

                List<byte[]> fileWithReplacedBytes = replaceBytes
                        (fileBytesTab, listOfSearchingBytesInFile, dataFromViewDTO.getInputBytes(), dataFromViewDTO.getOutputBytes());
                saveBytesTabToFile(listFiles.get(i), fileWithReplacedBytes);
            }
        }
        clearList();
    }



    public List<Integer> searchBytesInFiles(byte[] fileBytesTab, String searchBytes, String outputBytes) throws IOException {

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
                findList.add(i);
                i=i+j-1;
            }
            i++;
        }

    return findList;

    }




    public List<byte[]> replaceBytes(byte[] fileBytesTab, List<Integer> findList, String inputBytes, String outputBytes) throws IOException, UnsupportedEncodingException {

        byte[] searchBytesTab = inputBytes.getBytes("UTF8");
        byte[] outputBytesTab = outputBytes.getBytes("UTF8");
        List<byte[]> byteArrays = new LinkedList<>();
        int beginWrite = 0;


        for (int i = 0; i < findList.size(); i++) {
            byteArrays.add(Arrays.copyOfRange(fileBytesTab, beginWrite, findList.get(i)));
            byteArrays.add(Arrays.copyOfRange(outputBytesTab, 0, outputBytesTab.length));
            beginWrite = findList.get(i) + searchBytesTab.length;
        }
        byteArrays.add(Arrays.copyOfRange(fileBytesTab, beginWrite, fileBytesTab.length));

        return byteArrays;
    }



    public void saveBytesTabToFile(File file, List<byte[]> byteArrays) throws IOException {

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        String currentDateTime = DATE_TIME_FORMATTER.format(zonedDateTime);
        String filenameWithoutExtension = FilenameUtils.removeExtension(file.getName());
        String fileExtension = FilenameUtils.getExtension(file.getName());
        String currentPath = System.getProperty("user.dir");
        String directoryPath = currentPath + "\\folder\\savedFiles\\";
        File directory = new File(directoryPath);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        for (int i = 0; i < byteArrays.size(); i++) {
                byteArrayOutputStream.write(byteArrays.get(i));
        }

        byte[] bytesTabToSave = byteArrayOutputStream.toByteArray();

        if(! directory.exists()){
            directory.mkdir();
        }

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(directoryPath
                + filenameWithoutExtension + currentDateTime + "." + fileExtension, "rw")){

            randomAccessFile.write(bytesTabToSave);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }





}
