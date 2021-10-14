package ru.draelok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.draelok.action.GetGifUrlAction;

@RestController
public class MainController {
    @Autowired
    private GetGifUrlAction getGifUrlAction;

    @GetMapping(value = "/getReaction")
    public String getGifURL(){
        return getGifUrlAction.exec();
    }
}