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
    public void searchAndReplaceBytes(@RequestBody DataFromViewDTO dataFromViewDTO) {

        List<File> listFilesInGivenDirectory = byteService.searchFilesInDirectory(dataFromViewDTO.getPathToFile(), dataFromViewDTO.getFileExtension());
        byteService.iterateFiles(listFilesInGivenDirectory, dataFromViewDTO);

    }

}
