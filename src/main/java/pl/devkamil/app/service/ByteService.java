package pl.devkamil.app.service;


import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.riversun.bigdoc.bin.BigFileSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.devkamil.app.model.DataFromViewDTO;
import sun.misc.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.riversun.finbin.BinaryUtil.getBytes;


@Service
public class ByteService {


    List<File> fileList = new ArrayList<>();


    public List<File> searchFilesInGivenDirectoryWithGivenExtension(String path, String extension){

        File dir = new File(path);

        if(dir.isDirectory()){
            for(File file: dir.listFiles()){
                searchFilesInGivenDirectoryWithGivenExtension(file.getAbsolutePath(), extension);
            }
        }else if(dir.isFile() && dir.getName().endsWith(extension)){
            fileList.add(dir);
        }
        return fileList;
    }



    public void searchBytesInFiles(File file, String searchBytes, String outputBytes) throws IOException {
        BigFileSearcher bigFileSearcher = new BigFileSearcher();
        byte[] searchBytesTab = searchBytes.getBytes("UTF8");
        List<Long> findList = bigFileSearcher.searchBigFile(file, searchBytesTab);
        System.out.println("positions: " + file.getName() + findList);
        long numer;

        replaceBytes123(file, findList, searchBytes,outputBytes);







//        for(int i=0; i < findList.size(); i++){
//            numer = findList.get(i);
//            System.out.println(numer);
////            replaceBytes(file, numer, outputBytes);
//
//            replaceBytes123(file, numer, searchBytes, outputBytes);
//        }

    }


//    public void replaceBytes(File file, long positionOfBytes, String outputBytes) throws IOException {
//        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
//        byte[] outputBytesTab = outputBytes.getBytes();
////        System.out.println(file.getName() + " ODCZYT " + randomAccessFile.seek(4));
//        randomAccessFile.seek(positionOfBytes);
//        System.out.println(outputBytesTab.length);
//        randomAccessFile.write(outputBytesTab);
//
//
////        randomAccessFile.write(outputBytesTab, (int) positionOfBytes, ((int) positionOfBytes + outputBytesTab.length));
////        randomAccessFile.write(outputBytesTab, 2,3);
//
////        randomAccessFile.write(outputBytesTab, 0, 4);
//    }




    public void replaceBytes123(File file, List findList, String inputBytes, String outputBytes) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        byte[] fileBytesTab = Files.readAllBytes(path);
        byte[] searchBytesTab = inputBytes.getBytes("UTF8");
        byte[] outputBytesTab = outputBytes.getBytes("UTF8");
        


        List<byte[]> byteArrays = new LinkedList<>();

        int begin = 0;

        outer:
        for(int i=0; i < (fileBytesTab.length - searchBytesTab.length + 1); i++){
            for(int j=0; j < searchBytesTab.length; j++){
                if(fileBytesTab[i+j] != searchBytesTab[j]){
                    continue outer;
                }
            }
            byteArrays.add(Arrays.copyOfRange(fileBytesTab, begin, i));
            byteArrays.add(Arrays.copyOfRange(outputBytesTab, 0, outputBytesTab.length));
            begin = i + searchBytesTab.length;
        }
        byteArrays.add(Arrays.copyOfRange(fileBytesTab, begin, fileBytesTab.length));


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


        for(int i=0; i < byteArrays.size(); i++){
            byteArrayOutputStream.write(byteArrays.get(i));
        }

        byte [] allTab = byteArrayOutputStream.toByteArray();

        System.out.println(allTab);


        RandomAccessFile randomAccessFile = new RandomAccessFile("pliknowyplik.txt", "rwd");



        randomAccessFile.write(allTab);





//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBytesTab);
//        System.out.println("BYTEARRAY: " + byteArrayInputStream);
//        BufferedInputStream input = new BufferedInputStream(byteArrayInputStream);
//        System.out.println("BUFFERED: " + input);

//        System.out.println("FINDLIST:  " + findList);

    }



}
