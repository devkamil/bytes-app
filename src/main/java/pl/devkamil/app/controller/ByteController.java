package pl.devkamil.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.devkamil.app.model.RequestFromView;
import pl.devkamil.app.service.ByteService;

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






    @PostMapping("/search")
    public void searchAndReplace(@RequestBody RequestFromView requestFromView){
        byteService.show(requestFromView);
    }

}
