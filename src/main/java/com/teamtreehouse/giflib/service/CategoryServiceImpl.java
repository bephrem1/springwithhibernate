package com.teamtreehouse.giflib.service;

import com.teamtreehouse.giflib.dao.CategoryDao;
import com.teamtreehouse.giflib.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //So that spring can pick this up as a bean and it can be autowired in the Controllers where this will be used
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDAO; //Spring will find the only implementation of CategoryDao which is CategoryDaoImpl marked @Repository

    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryDAO.findById(id);
    }

    @Override
    public void save(Category category) {
        categoryDAO.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryDAO.delete(category);
    }
}
