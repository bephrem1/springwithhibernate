package com.teamtreehouse.giflib.dao;

import com.teamtreehouse.giflib.model.Category;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository //Used so that Spring picks up this class as an autowireable implementation of categoryDAO
            //Implementation is annotated and not the interface
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Category> findAll() {
        //Open session
        Session session = sessionFactory.openSession();

        //Get Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        //Create CriteriaQuery
        CriteriaQuery<Category> criteria = builder.createQuery(Category.class);

        // Specify criteria root
        criteria.from(Category.class);

        //Use criteria to query with session to fetch all categories
        List<Category> categories = session.createQuery(criteria).getResultList();

        //Close session
        session.close();

        return categories;
    }

    @Override
    public Category findById(Long id) {
        //Open session
        Session session = sessionFactory.openSession();

        //Get the gif with the id "id"
        Category category = session.get(Category.class, id);

        //Makes sure that the gifs field is initialized before the session is closed
        Hibernate.initialize(category.getGifs()); //Force initialization of a proxy or persistent collection

        //Close session
        session.close();

        return category;
    }

    @Override
    public void save(Category category) {
        //Open a session
        Session session = sessionFactory.openSession();

        //Begin a transaction
        session.beginTransaction();

        //Save the category
        session.saveOrUpdate(category); //save() always makes new entry, saveOrUpdate()

        //Commit the transaction
        session.getTransaction().commit();

        //Close the session
        session.close();

    }

    @Override
    public void delete(Category category) {
        //Open session
        Session session = sessionFactory.openSession();

        //Begin a transaction
        session.beginTransaction();

        //Delete the category
        session.delete(category);

        //Commit the transaction
        session.getTransaction().commit();

        //Close session
        session.close();
    }
}
