package com.web.furniturehub.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.web.furniturehub.model.Ftype;
import com.web.furniturehub.model.Furniture;
import com.web.furniturehub.model.Style;
import com.web.furniturehub.repository.FtypeRepository;
import com.web.furniturehub.repository.FurnitureRepository;
import com.web.furniturehub.repository.StyleRepository;
import com.web.furniturehub.service.FileUploadUtil;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @Autowired
    private FurnitureRepository repo;

    @Autowired
    private FtypeRepository ftypeRepository;
    @Autowired
    private StyleRepository styleRepository;

    @PostMapping("/admin/add/furniture")
    public String saveUser(Furniture furniture,
            @RequestParam("images") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        furniture.setImage(fileName);
        repo.save(furniture);
        String uploadDir = "./src/main/resources/static/img";

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/admin/add";
    }

    @GetMapping(value = "/admin/add")
    public String getMethodName(Model model) {
        List<Ftype> tList = ftypeRepository.findAll();
        List<Style> sList = styleRepository.findAll();
        model.addAttribute("furniture", new Furniture());
        model.addAttribute("tList", tList);
        model.addAttribute("sList", sList);
        return "admin";
    }

}
