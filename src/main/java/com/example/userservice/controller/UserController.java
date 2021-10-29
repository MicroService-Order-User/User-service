package com.example.userservice.controller;

import com.example.userservice.VO.ReponseTemplateVO;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    //    @GetMapping({"/id"})
//    public ReponseTemplateVO getUserWithDepartment(@PathVariable("id") Long studentId){
//        return studentService.getUserWithDepartment(studentId);
//    }
    @GetMapping({"/{id}"})
    public ReponseTemplateVO getUserWithOrder(@PathVariable("id") Long userId){
        return userService.getUserWithOrder(userId);
    }

    @Value("${welcome}")
    String a;

    @GetMapping
    public String helloWorld(){
        return a;
    }

}
