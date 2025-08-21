package com.chat.backend;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// @RestController
@Controller
public class PageController {

    @GetMapping(path = "/")
    public String Home() throws IOException {
        // return Files.readString(Path.of(
        //     "../../../../../../../front-end/home/home.html"));
        return "home";
    }
}
