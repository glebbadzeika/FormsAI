package com.example.spring_boot_backend.service;

import com.example.spring_boot_backend.model.Option;
import com.example.spring_boot_backend.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionService {
    @Autowired
    private OptionRepository optionRepository;
//    @Autowired
//    private SectionService sectionService;
    public Option createChoice(Option option) {
        Option c=new Option();
        try {
            c.setOption_text(option.getOption_text());
            c.setIs_open(option.getIs_open());
        }catch (Exception e){
            throw new RuntimeException("Error: "+e.getMessage());
        }

        //if(option.getSpecial_section() != null){c.setSpecial_section(sectionService.createSection(option.getSpecial_section()));}


        return optionRepository.save(c);
    }
}
