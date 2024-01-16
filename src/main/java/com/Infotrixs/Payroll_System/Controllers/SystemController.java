package com.Infotrixs.Payroll_System.Controllers;

import com.Infotrixs.Payroll_System.DTOs.NewAdminRequest;
import com.Infotrixs.Payroll_System.Services.Impl.SystemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class SystemController {
    final SystemServiceImpl systemService;
    @Autowired
    public SystemController(SystemServiceImpl systemService) {
        this.systemService = systemService;
    }

    @PostMapping("/add-new-admin")
    public ResponseEntity<String> addNewAdmin(@RequestBody NewAdminRequest request){
        try{
            String response = systemService.addNewAdmin(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
