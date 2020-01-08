package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class HomeController {
    @Autowired
    Repository repository;

    @RequestMapping("/")
    public String listItems(Model model){
        model.addAttribute("todos", repository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String todoForm(Model model){
        model.addAttribute("todo", new Todo());
        return "todoform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Todo todo, BindingResult result){
        if (result.hasErrors()){
            return "todoform";
        }
        repository.save(todo);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("todo", repository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateTodo(@PathVariable("id") long id,
                               Model model){
        model.addAttribute("todo", repository.findById(id).get());
        return "todoform";
    }

    @RequestMapping("/delete/{id}")
    public String delTodo(@PathVariable("id") long id){
        repository.deleteById(id);
        return "redirect:/";
    }

}
