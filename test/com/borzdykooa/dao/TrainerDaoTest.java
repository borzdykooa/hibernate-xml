package com.borzdykooa.dao;

import com.borzdykooa.entity.Trainer;
import com.borzdykooa.util.SessionFactoryManager;
import com.borzdykooa.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/*
Класс для тестирования методов TrainerDao
 */
public class TrainerDaoTest {

    private TrainerDao trainerDao = TrainerDao.getInstance();
    private SessionFactory sessionFactory;

    @Before
    public void initDb() {
        sessionFactory = SessionFactoryManager.getSessionFactory();
        TestDataImporter.getInstance().deleteTestData(sessionFactory);
        TestDataImporter.getInstance().importTestData(sessionFactory);
    }

    @AfterClass
    public static void finish() {
        SessionFactory sessionFactory = SessionFactoryManager.getSessionFactory();
        sessionFactory.close();
    }

    @Test
    public void testFindAll() {
        List<Trainer> trainers = trainerDao.findAll();
        assertTrue(trainers.size() == 3);
    }

    @Test
    public void testFind() {
        try (Session session = sessionFactory.openSession()) {
            Trainer trainer = session.createQuery("select t from Trainer t ", Trainer.class)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
            assertNotNull(trainer);
            String name = trainer.getName();

            Trainer theSameTrainer = trainerDao.find(trainer.getId());
            String theSameName = theSameTrainer.getName();
            assertTrue(name.equals(theSameName));
        }
    }

    @Test
    public void testSave() {
        Trainer nikolaiNikolaev = new Trainer("Nikolai Nikolaev", "C", 12);
        Long id = trainerDao.save(nikolaiNikolaev);
        assertNotNull("Entity is not saved", id);
    }

    @Test
    public void testDelete() {
        Trainer trainer;
        try (Session session = sessionFactory.openSession()) {
            trainer = session.createQuery("select t from Trainer t where t.name like 'Ivan Ivanov'", Trainer.class)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
            assertNotNull(trainer);
        }
        trainerDao.delete(trainer);
        try (Session session = sessionFactory.openSession()) {
            Trainer theSameTrainer = session.createQuery("select t from Trainer t where t.name like 'Ivan Ivanov'", Trainer.class)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
            assertNull("Entity is not null!", theSameTrainer);
        }
    }

    @Test
    public void testUpdate() {
        Trainer trainer;
        try (Session session = sessionFactory.openSession()) {
            trainer = session.createQuery("select t from Trainer t where t.name like 'Ivan Ivanov' and t.language='C++'", Trainer.class)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
            assertNotNull(trainer);
        }
        trainer.setLanguage("Java");
        trainerDao.update(trainer);
        try (Session session = sessionFactory.openSession()) {
            Trainer updatedTrainer = session.createQuery("select t from Trainer t where t.name like 'Ivan Ivanov' and t.language='Java'", Trainer.class)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
            assertNotNull(updatedTrainer);
        }
    }

    @Test
    public void testFindByLanguage() {
        List<Trainer> trainers = trainerDao.findByLanguage("Java");
        assertTrue(trainers.size() == 1);
    }
}
