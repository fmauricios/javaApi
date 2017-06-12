package me.mauricioserna.javaApi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by mauricio on 12/06/17.
 */

@Controller
public class MainController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        String response = "Hello world! <a href='http://mauricioserna.me'>mauricioserna.me</a>";
        return response;
    }

}
