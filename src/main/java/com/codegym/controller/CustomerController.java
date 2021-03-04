package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("")
    public ModelAndView listCustomer(){
        ModelAndView modelAndView = new ModelAndView("index");
        List customerList = customerService.findAll();
        modelAndView.addObject("customers",customerList);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView save(@ModelAttribute Customer customer){
        int id = customerService.findAll().size();
        customer.setId(id);
        customerService.update(id,customer);
        ModelAndView modelAndView= new ModelAndView("create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm (@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        Customer customer = customerService.findById(id);
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }
    @PostMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable int id, @ModelAttribute Customer customer){
        customer.setId(id);
        customerService.update(id,customer);
        ModelAndView modelAndView = new ModelAndView("redirect:/customers");
        modelAndView.addObject("customer", customerService.findById(id));
        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public ModelAndView ShowDeleteForm (@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("/delete");
        Customer customer = customerService.findById(id);
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }
    @PostMapping("delete")
    public String delete(Customer customer, RedirectAttributes redirectAttributes){
        customerService.remove(customer.getId());
        redirectAttributes.addFlashAttribute("success","Removed customer successfully!");
        return "redirect:/";
    }

    @GetMapping("view/{id}")
    public ModelAndView view(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("/view");
        Customer customer = customerService.findById(id);
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }


}