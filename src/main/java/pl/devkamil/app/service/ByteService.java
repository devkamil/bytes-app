package pl.devkamil.app.service;


import org.springframework.stereotype.Service;
import pl.devkamil.app.model.DataFromViewDTO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
     * This method searches a files with given extension in given directory (and subdirectories)
     * @param path This is path to directory which we want to search
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
     * This method is iterating all over the files from List, converting every file to byte[] tab,
     * invoking "searchBytesInFiles" method for each file,
     * then invoking "replaceBytes" method for files where above method finding the bytes,
     * and finally clearing the List of files at the end of the loop of program.
     * @param listFiles List of all files with given extension in given directory and subdirectories
     * @param dataFromViewDTO Object from the 'view', containing path to directory, file extension,
     *                        searched sequence of bytes and output sequence of bytes
     */

    public void iterateFiles(List<File> listFiles, DataFromViewDTO dataFromViewDTO) {
        byte[] searchBytesTab = new byte[0];
        byte[] outputBytesTab = new byte[0];

        try{
            searchBytesTab = dataFromViewDTO.getInputBytes().getBytes("UTF8");
            outputBytesTab = dataFromViewDTO.getOutputBytes().getBytes("UTF8");
        }catch(UnsupportedEncodingException ex){
            ex.printStackTrace();
        }

        // For each File found with given extension in given directory and subdirectories, execute code below
        for (int i = 0; i < listFiles.size(); i++) {

            // Converting File to bytes tab
            byte[] fileBytesTab = convertFileToBytesTab(listFiles.get(i));

            // Searching a given searchBytes in fileBytesTab and adding its initial position to List
            List <Integer> listOfSearchingBytesInFile = searchBytesInFiles
                    (fileBytesTab, searchBytesTab);

            // If the File contains the search bytes, execute below code (if above List is not empty)
            if(listOfSearchingBytesInFile.size() != 0) {

                // Creates a new byte array with new values of bytes (searchBytes are replaced by outputBytes)
                LinkedList<byte[]> fileWithReplacedBytes = replaceBytes
                        (fileBytesTab, listOfSearchingBytesInFile, searchBytesTab, outputBytesTab);

                // Change from LinkedList<byte[]> to byte[]
                byte[] bytesTabToSave = changeFromListToByteArray(fileWithReplacedBytes);

                // Save byte array as new File
                saveBytesTabToFile(listFiles.get(i), bytesTabToSave);
            }
        }
        // Clears File list after each loop of program
        clearList();
    }


    /**
     * This method converts File to byte[] tab
     * @param file File to convert
     * @return bytes tab converted from File
     */

    public byte[] convertFileToBytesTab(File file) {

        byte[] fileBytesTab = new byte[0];

        try {
            Path path = Paths.get(file.getAbsolutePath());
            fileBytesTab = Files.readAllBytes(path);
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return fileBytesTab;
    }


    /**
     * This method searches a given sequence of bytes in this byte[] tab
     * @param fileBytesTab Converted file to byte[] tab
     * @param searchBytesTab Bytes that we want to search for in given file
     * @return A list that contains all the indexes of the initial position of searched bytes in the specified file
     */

    public List<Integer> searchBytesInFiles(byte[] fileBytesTab, byte[] searchBytesTab) {

        LinkedList<Integer> findBytesList = new LinkedList<>();


        if(searchBytesTab.length != 0) {

            int i = 0;       // index of File bytes array
            int j;           // index of search bytes array

            while (i < fileBytesTab.length - searchBytesTab.length + 1) {

                j = 0;  // go to start position of the search byte array

                // Comparing consecutive bytes from File bytes array and search bytes array
                while ((j < searchBytesTab.length) && (searchBytesTab[j] == fileBytesTab[i + j])) {

                    j++;    // go to next position in search byte array and File byte array
                }

                // If File bytes array contains a search bytes array, add start position of search bytes array to List
                if (j == searchBytesTab.length) {
                    findBytesList.add(i);

                    i = i + j - 1;   // go to position behind a find bytes
                }

                i++;
            }
        }
    return findBytesList;

    }


    /**
     * This method replaces 'search bytes array' to 'output bytes array' in 'File Bytes Tab'
     * @param fileBytesTab The File converted to byte array
     * @param findList The List of starting positions of 'search bytes' in File
     * @param searchBytesTab Search bytes converted to bytes array
     * @param outputBytesTab Output bytes converted to bytes array
     * @return LinkedList which contains bytes array of new File
     */

    public LinkedList<byte[]> replaceBytes(byte[] fileBytesTab, List<Integer> findList, byte[] searchBytesTab, byte[] outputBytesTab) {

        LinkedList<byte[]> byteListOfNewFile = new LinkedList<>();
        int beginWrite = 0;


        for (int i = 0; i < findList.size(); i++) {
            byteListOfNewFile.add(Arrays.copyOfRange(fileBytesTab, beginWrite, findList.get(i)));
            byteListOfNewFile.add(Arrays.copyOfRange(outputBytesTab, 0, outputBytesTab.length));
            beginWrite = findList.get(i) + searchBytesTab.length;
        }
        byteListOfNewFile.add(Arrays.copyOfRange(fileBytesTab, beginWrite, fileBytesTab.length));

        return byteListOfNewFile;
    }

    /**
     * This method changing List with bytes array to one bytes array
     * @param byteListOfNewFile LinkedList which contains bytes array of new File
     * @return Bytes array of new File
     */

    public byte[] changeFromListToByteArray(LinkedList<byte[]> byteListOfNewFile){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        for (int i = 0; i < byteListOfNewFile.size(); i++) {
            try {
                byteArrayOutputStream.write(byteListOfNewFile.get(i));
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        byte[] bytesTabToSave = byteArrayOutputStream.toByteArray();

        return bytesTabToSave;
    }

    /**
     * This method saving new byte array to File
     * @param file Original file
     * @param bytesTabToSave Changed File as a byte array
     */

    public void saveBytesTabToFile(File file, byte[] bytesTabToSave)  {

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        String currentDateTime = DATE_TIME_FORMATTER.format(zonedDateTime);
        String filenameWithoutExtension = FilenameUtils.removeExtension(file.getName());
        String fileExtension = FilenameUtils.getExtension(file.getName());
        String currentPath = System.getProperty("user.dir");
        String directoryPath = currentPath + "\\folder\\savedFiles\\";
        File directory = new File(directoryPath);


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
