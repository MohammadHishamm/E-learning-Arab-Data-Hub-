package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.ModelAndView;

import com.example.demo.models.Courses;
import com.example.demo.models.Instructor;
import com.example.demo.models.User;
import com.example.demo.repositories.CoursesRepository;
import com.example.demo.repositories.InstructorRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/courses")
@RestController
public class coursesController {

    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    
    
    @GetMapping("")
    public ModelAndView getcourses() {
        ModelAndView mav = new ModelAndView("viewCourses.html");
        List<Courses> courses = this.coursesRepository.findAll();
        mav.addObject("courses", courses);
         return mav;

    }






    @GetMapping("add-course")
    public ModelAndView courses() {
       ModelAndView mav = new ModelAndView("courses.html");
       Courses course= new Courses();
       mav.addObject("course", course);
       return mav;
    }

    @PostMapping("save-course") 
    public ModelAndView saveCourse(@ModelAttribute @Valid Courses course, BindingResult bindingResult ,HttpSession session) {
        ModelAndView mav = new ModelAndView("courses.html");
        
        if (bindingResult.hasErrors()) {
            mav.addObject("errors", bindingResult.getAllErrors());
            return mav;
        }

        if (session.getAttribute("email") != null)
        {
            // int id  = (int)session.getAttribute("id");
            // Instructor instructor = instructorRepository.findbyUser_id(id);

            // if (instructor != null) {
              
            //     List<Courses> instructorCourses = instructor.getCourses();
            //     instructorCourses.add(course);
            //     instructor.setCourses(instructorCourses);
    
              
            //     instructorRepository.save(instructor);
                
               
            //     return new ModelAndView("redirect:/courses/add-course");
            // } else {
              
            //   //  mav.addObject("error", "Instructor not found");
                return mav;
            // }
        } else {
          
         //   mav.addObject("error", "User session not found");
            return mav;
        }
      
    }



    @GetMapping("view-course")
    public ModelAndView view_course(@RequestParam("courseid") int id) {
       ModelAndView mav = new ModelAndView("view-course.html");
       Courses course =  this.coursesRepository.findById(id);
       if(course != null) 
       {
            Instructor instructor =  course.getInstructor();
            User user = instructor.getUser();
            mav.addObject("course" , course );
            mav.addObject("User" , user );
       }
       else
       {
            mav = new ModelAndView("index.html");
       }

       return mav;
    }



    




}
