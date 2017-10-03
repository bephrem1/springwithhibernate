package com.teamtreehouse.giflib.dao;

import com.teamtreehouse.giflib.model.Category;
import com.teamtreehouse.giflib.model.Gif;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class GifDaoImpl implements GifDao{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Gif> findAll() {
        //Open session
        Session session = sessionFactory.openSession();

        //Get Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        //Create CriteriaQuery
        CriteriaQuery<Gif> criteria = builder.createQuery(Gif.class);

        // Specify criteria root
        criteria.from(Gif.class);

        //Use criteria to query with session to fetch all gifs
        List<Gif> gifs = session.createQuery(criteria).getResultList();

        //Close session
        session.close();

        return gifs;
    }

    @Override
    public Gif findById(Long id) {
        //Open session
        Session session = sessionFactory.openSession();

        //Get the gif with the id "id"
        Gif gif = session.get(Gif.class, id);

        //Close session
        session.close();

        return gif;
    }

    @Override
    public void save(Gif gif) {
        //Open session
        Session session = sessionFactory.openSession();

        //Begin a transaction
        session.beginTransaction();

        //Save the gif
        session.saveOrUpdate(gif);

        //Commit the transaction
        session.getTransaction().commit();

        //Close session
        session.close();
    }

    @Override
    public void delete(Gif gif) {
        //Open session
        Session session = sessionFactory.openSession();

        //Begin a transaction
        session.beginTransaction();

        //Delete the gif
        session.delete(gif);

        //Commit the transaction
        session.getTransaction().commit();

        //Close session
        session.close();
    }
}
