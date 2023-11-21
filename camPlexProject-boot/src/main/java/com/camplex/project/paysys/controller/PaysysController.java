package com.camplex.project.paysys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.camplex.project.paysys.model.service.PaysysService;

@Controller
public class PaysysController {
	
	@Autowired
	PaysysService service;

}
