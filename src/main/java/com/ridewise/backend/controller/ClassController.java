package com.ridewise.backend.controller;

import com.ridewise.backend.dto.ClassDto;
import com.ridewise.backend.mapper.ClassMapper;
import com.ridewise.backend.serviceImpl.ClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/class")
record ClassController(ClassService service) {

    @GetMapping("")
    ResponseEntity<List<ClassDto>> getAllClasses () {
        List<ClassDto> list = service.findAll().stream().map(ClassMapper.INSTANCE::mapDto).toList();
        System.out.println(list.get(0).type());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
