package ru.draelok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.draelok.action.GetGifUrlAction;

@RestController
public class MainController {
    @Autowired
    private GetGifUrlAction getGifUrlAction;

    @GetMapping(value = "/getReaction", produces = "image/gif")
    public ResponseEntity<byte[]> getGifURL(){
        try{
            return new ResponseEntity<>(getGifUrlAction.exec(), HttpStatus.OK);
        } catch (Throwable ex){
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}