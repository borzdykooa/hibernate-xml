package com.borzdykooa.dao;

import com.borzdykooa.entity.Trainer;
import com.borzdykooa.util.SessionFactoryManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/*
Класс, содержащий методы работы с таблицей trainer (реализация с помощью Criteria API)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TrainerDao {

    private static final TrainerDao INSTANCE = new TrainerDao();
    private static final Logger log = Logger.getLogger(TrainerDao.class);
    private static final SessionFactory SESSION_FACTORY = SessionFactoryManager.getSessionFactory();

    public Long save(Trainer trainer) {
        log.info("Method save is called in TrainerDao");
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            Serializable id = session.save(trainer);
            session.getTransaction().commit();
            if (id != null) {
                log.info(trainer.toString() + " has been saved successfully!");
            }
            return (Long) id;
        }
    }

    public Trainer find(Long id) {
        log.info("Method find is called in TrainerDao");
        try (Session session = SESSION_FACTORY.openSession()) {
            Trainer trainer = session.find(Trainer.class, id);
            if (trainer != null) {
                log.info(trainer.toString() + " has been found");
            }
            return trainer;
        }
    }

    public List<Trainer> findAll() {
        log.info("Method findAll is called in TrainerDao");
        try (Session session = SESSION_FACTORY.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Trainer> criteria = cb.createQuery(Trainer.class);
            Root<Trainer> root = criteria.from(Trainer.class);
            criteria.select(root);
            List<Trainer> list = session.createQuery(criteria).list();
            if (list.size() > 0) {
                log.info("List of Trainers: " + list.toString());
            }
            return list;
        }
    }

    public void update(Trainer trainer) {
        log.info("Method update is called in TrainerDao for " + trainer.toString());
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            session.update(trainer);
            session.getTransaction().commit();
            log.info(trainer.toString() + " has been updated successfully!");
        }
    }

    public void delete(Trainer trainer) {
        log.info("Method delete is called in TrainerDao");
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            session.delete(trainer);
            session.getTransaction().commit();
            log.info(trainer.toString() + " has been deleted successfully!");
        }
    }

    public List<Trainer> findByLanguage(String language) {
        log.info("Method findByLanguage is called in TrainerDao");
        try (Session session = SESSION_FACTORY.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Trainer> criteria = cb.createQuery(Trainer.class);
            Root<Trainer> root = criteria.from(Trainer.class);
            criteria.select(root).where(cb.equal(root.get("language"), language));
            List<Trainer> list = session.createQuery(criteria).list();
            if (list.size() > 0) {
                log.info("List of Trainers: " + list.toString());
            } else {
                log.info("List of Trainers is empty");
            }
            return list;
        }
    }

    public static TrainerDao getInstance() {
        return INSTANCE;
    }
}
