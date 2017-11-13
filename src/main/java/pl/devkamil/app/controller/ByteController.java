package pl.devkamil.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.devkamil.app.model.DataFromViewDTO;
import pl.devkamil.app.service.ByteService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/")
public class ByteController {

    @Autowired
    private ByteService byteService;










//    @GetMapping("/{filePath}")
//    public String readFile (@PathVariable ("filePath") String filePath){
//        return byteService.readFile(filePath + ".txt");
//    }

//    @PostMapping("/{filePath}")
//    public void write (@PathVariable ("filePath") String filePath){
//        byteService.writeFile(filePath + ".txt");
//    }






//    @PostMapping("/search")
//    public void searchAndReplace(@RequestBody DataFromViewDTO dataFromViewDTO){
//        byteService.show(dataFromViewDTO);
//    }


    @PostMapping("/search")
    public void searchAndChangeBytesInFilesInGivenDirectoryAndWithGivenExtension(@RequestBody DataFromViewDTO dataFromViewDTO){

        File[] tab = byteService.searchFilesInGivenDirectoryWithGivenExtension(dataFromViewDTO);

        for(Object o: tab){
            System.out.println(o);
        }

    }

//    @GetMapping("/searchdirectory")
//    public List<File> serch() throws IOException{
//        System.out.println("METODA DIRECTORY");
//        return byteService.listFiles();
//
//    }
//
//    @GetMapping("/searchdirectory")
//    public void serch() throws IOException{
//        System.out.println("METODA DIRECTORY");
//        byteService.listFiles();
//
//    }
}
