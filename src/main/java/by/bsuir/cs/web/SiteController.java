package by.bsuir.cs.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Danilau_MO on 19.12.2016.
 */

@Controller
public class SiteController {

    @PostMapping("/accountPage")
    public String signIn(@RequestParam(value = "email") String email, @RequestParam(value = "pass") String password, Model model) {
        model.addAttribute("email", email);
        model.addAttribute("pass", password);
        model.addAttribute("logged", true);
        return "accountPage";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/signIn")
    public String signIn() {
        return "signIn";
    }

    @RequestMapping("/signUp")
    public String signUp() {
        return "signUp";
    }
}
