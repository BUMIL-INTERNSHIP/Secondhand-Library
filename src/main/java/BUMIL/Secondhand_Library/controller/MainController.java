package BUMIL.Secondhand_Library.controller;


import BUMIL.Secondhand_Library.domain.book.Service.BookRepositoryService;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    BookRepositoryService bookRepositoryService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page){
        Page<BookEntity> bookEntities =  bookRepositoryService.findAllBookEntity(page);
        model.addAttribute("bookEntities" ,bookEntities);
        return "index";
    }

}
