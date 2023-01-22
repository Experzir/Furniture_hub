package com.web.furniturehub.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.web.furniturehub.model.Category;
import com.web.furniturehub.model.CategoryFurniture;
import com.web.furniturehub.model.Ftype;
import com.web.furniturehub.model.Furniture;
import com.web.furniturehub.model.Style;
import com.web.furniturehub.model.User;
import com.web.furniturehub.repository.CateFurRepository;
import com.web.furniturehub.repository.CategoryRepository;
import com.web.furniturehub.repository.FtypeRepository;
import com.web.furniturehub.repository.FurnitureRepository;
import com.web.furniturehub.repository.StyleRepository;
import com.web.furniturehub.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private FurnitureRepository furnitureRepository;
    private FtypeRepository ftypeRepository;
    private StyleRepository styleRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private CateFurRepository cateFurRepository;
  
    public UserController(FurnitureRepository furnitureRepository, FtypeRepository ftypeRepository,
            StyleRepository styleRepository, UserRepository userRepository, CategoryRepository categoryRepository,
            CateFurRepository cateFurRepository) {
        this.furnitureRepository = furnitureRepository;
        this.ftypeRepository = ftypeRepository;
        this.styleRepository = styleRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.cateFurRepository = cateFurRepository;
    }

    @GetMapping(value = "/user/getby")
    public String getFurByID(@RequestParam(value = "sid", required = true) Integer sid,
            @RequestParam(value = "tid", required = true) Integer tid, Model model, HttpServletRequest request) {
        List<Furniture> fList;
        if (sid == 0 && tid == 0) {
            fList = furnitureRepository.findAll();
        } else if (sid == 0) {
            fList = furnitureRepository.findProductsByTid(tid);
        } else if (tid == 0) {
            fList = furnitureRepository.findProductsBySid(sid);
        } else {
            fList = furnitureRepository.findProductsByIds(sid, tid);
        }
        // type,style
        List<Ftype> tList = ftypeRepository.findAll();
        List<Style> sList = styleRepository.findAll();

        User user = findUser(request);
        List<Category> category = user.getCategorys();
        model.addAttribute("fList", fList);
        model.addAttribute("tList", tList);
        model.addAttribute("sList", sList);
        model.addAttribute("category", category);
        return "home";
    }

    @PostMapping(value = "/user/add/category")

    public String addCate(@RequestParam(value = "cateName", required = true) String cateName,
            Model model, HttpServletRequest request) {
        Category category = new Category();
        User user = findUser(request);
        category.setName(cateName);
        category.setUser(user);
        categoryRepository.save(category);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    private User findUser(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userRepository.findByEmail(principal.getName());
        return user;
    }

    @GetMapping("/user/mycategory/{category_id}")
    public String getFurnitureByCategory(@PathVariable("category_id") int cid, Model model,
            HttpServletRequest request) {
    
        Category cf = categoryRepository.findById(cid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + cid));
        User user = findUser(request);
        List<Category> category = user.getCategorys();
        List<CategoryFurniture> cfList = new ArrayList<CategoryFurniture>();

        cfList = cf.getCf();

        if (cf.getCf().isEmpty()) {
            model.addAttribute("category", category);
            return "mycategoryitemempty";
        }

        model.addAttribute("category", category);
        model.addAttribute("cfList", cfList);
        return "mycategory";
    }

    @GetMapping(value = "/user/add/{fur_id}/to/{category_id}")
    public String postMethodName(@PathVariable("fur_id") int fid, @PathVariable("category_id") int cid,
            Model model, HttpServletRequest request) {
        CategoryFurniture categoryFurniture = new CategoryFurniture();
        Category c = categoryRepository.findById(cid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + cid));
        Furniture f = furnitureRepository.findById(fid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + fid));

        categoryFurniture.setFurniture(f);
        categoryFurniture.setCategory(c);
        cateFurRepository.save(categoryFurniture);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

}
