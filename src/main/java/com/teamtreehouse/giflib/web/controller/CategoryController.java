package com.teamtreehouse.giflib.web.controller;

import com.teamtreehouse.giflib.model.Category;
import com.teamtreehouse.giflib.service.CategoryService;
import com.teamtreehouse.giflib.web.Color;
import com.teamtreehouse.giflib.web.FlashMessge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Index of all categories
    @RequestMapping("/categories")
    public String listCategories(Model model) {
        // TODO: Get all categories
        List<Category> categories = categoryService.findAll();

        model.addAttribute("categories",categories);
        return "category/index";
    }

    // Single category page
    @RequestMapping("/categories/{categoryId}")
    public String category(@PathVariable Long categoryId, Model model) {
        // TODO: Get the category given by categoryId
        Category category = categoryService.findById(categoryId);

        model.addAttribute("category", category);
        return "category/details";
    }

    // Form for adding a new category
    @RequestMapping("categories/add")
    public String formNewCategory(Model model) {
        // TODO: Add model attributes needed for new form
        if(!model.containsAttribute("category")){
            model.addAttribute("category", new Category()); //We pass in this empty category object since
                                                                         // it'll be filled out in the form in the case model is empty
        }
        model.addAttribute("colors", Color.values());
        model.addAttribute("action", "/categories");
        model.addAttribute("heading", "New Category");
        model.addAttribute("submit", "Add");

        return "category/form";
    }

    // Form for editing an existing category
    @RequestMapping("categories/{categoryId}/edit")
    public String formEditCategory(@PathVariable Long categoryId, Model model) {
        // TODO: Add model attributes needed for new form
        if(!model.containsAttribute("category")){
            model.addAttribute("category", categoryService.findById(categoryId)); //We pass in this empty category object since
            // it'll be filled out in the form in the case model is empty
        }
        model.addAttribute("colors", Color.values());
        model.addAttribute("action", String.format("/categories/%s", categoryId));
        model.addAttribute("heading", "Edit Category");
        model.addAttribute("submit", "Save");

        return "category/form";
    }

    // Update an existing category
    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.POST)
    public String updateCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) { //Spring will reassemble the category data when added as a parameter here
        // TODO: Update category if valid data was received
        if(result.hasErrors()) {
            //Include validation errors upon redirect so thymeleaf can have access to it
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);

            //Add category if invalid data received to refill data where user left off
            redirectAttributes.addFlashAttribute("category", category); //Model data survives 1 redirect

            //Redirect back to form
            return String.format("redirect:/categories/%s/edit", category.getId());
        }

        categoryService.save(category);

        redirectAttributes.addFlashAttribute("flash", new FlashMessge("Category successfully updated", FlashMessge.Status.SUCCESS));

        // TODO: Redirect browser to /categories
        return "redirect:/categories";
    }

    // Add a category
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String addCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        //@valid annotation makes spring check the constraints input variable
        // TODO: Add category if valid data was received
        if(result.hasErrors()) {
            //Include validation errors upon redirect so thymeleaf can have access to it
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);

            //Add category if invalid data received to refill data where user left off
            redirectAttributes.addFlashAttribute("category", category); //Model data survives 1 redirect

            //Redirect back to form
            return "redirect:/categories/add";
        }

        categoryService.save(category);

        redirectAttributes.addFlashAttribute("flash", new FlashMessge("Category successfully added", FlashMessge.Status.SUCCESS));

        // TODO: Redirect browser to /categories
        return "redirect:/categories"; //If Spring sees a "redirect:" it'll send a 302 response code and location header
                                       //to /categories. This is a get request at the end and not rendering a view directly
    }

    // Delete an existing category
    @RequestMapping(value = "/categories/{categoryId}/delete", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable Long categoryId, RedirectAttributes redirectAttributes) {
        Category cat = categoryService.findById(categoryId);

        // TODO: Delete category if it contains no GIFs
        if(cat.getGifs().size() > 0){ //Can't access a mapped selection outside of a hibernate session so we force it with Hibernate.initialize()
            redirectAttributes.addFlashAttribute("flash", new FlashMessge("Only empty categories can be deleted", FlashMessge.Status.FAILURE));
            return String.format("redirect:/categories/%s/edit", categoryId);
        }

        categoryService.delete(cat);
        redirectAttributes.addFlashAttribute("flash", new FlashMessge("Category successfully deleted", FlashMessge.Status.SUCCESS));

        // TODO: Redirect browser to /categories
        return "redirect:/categories";
    }
}
