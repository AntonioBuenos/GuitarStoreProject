package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping()
public class HomePageController {

    @GetMapping()
    public String index() {
        return "/homepage";
    }
}
