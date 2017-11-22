package pl.devkamil.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.devkamil.app.model.DataFromViewDTO;
import pl.devkamil.app.service.ByteService;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/")
public class ByteController {

    @Autowired
    private ByteService byteService;


    @PostMapping("/search")
    public void searchAndChangeBytesInFilesInGivenDirectoryAndWithGivenExtension(@RequestBody DataFromViewDTO dataFromViewDTO) throws UnsupportedEncodingException, IOException {

        List<File> tab = byteService.searchFilesInGivenDirectoryWithGivenExtension(dataFromViewDTO.getPathToFile(), dataFromViewDTO.getFileExtension());


        for(int i=0; i < tab.size(); i++){
            byteService.searchBytesInFiles(tab.get(i), dataFromViewDTO.getInputBytes(), dataFromViewDTO.getOutputBytes());







//            byteService.searchAndReplaceBytes(tab.get(i), dataFromViewDTO.getInputBytes(), dataFromViewDTO.getOutputBytes());
//            byteService.replaceBytes(tab[i], dataFromViewDTO.getOutputBytes());
        }

    }

}
