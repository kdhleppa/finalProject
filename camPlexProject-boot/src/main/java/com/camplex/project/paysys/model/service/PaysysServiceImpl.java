package com.camplex.project.paysys.model.service;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:/config.properties")
public class PaysysServiceImpl implements PaysysService{

}
