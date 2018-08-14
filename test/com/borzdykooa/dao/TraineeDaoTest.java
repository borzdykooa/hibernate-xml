package com.borzdykooa.dao;

import com.borzdykooa.entity.Trainee;
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
Класс для тестирования методов TraineeDao
 */
public class TraineeDaoTest {

    private TraineeDao traineeDao = TraineeDao.getInstance();
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
        List<Trainee> trainees = traineeDao.findAll();
        assertTrue(trainees.size() == 3);
    }

    @Test
    public void testFind() {
        try (Session session = sessionFactory.openSession()) {
            Trainee trainee = session.createQuery("select t from Trainee t ", Trainee.class)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
            assertNotNull(trainee);
            String name = trainee.getName();

            Trainee theSameTrainee = traineeDao.find(trainee.getId());
            String theSameName = theSameTrainee.getName();
            assertTrue(name.equals(theSameName));
        }
    }

    @Test
    public void testSave() {
        try (Session session = sessionFactory.openSession()) {
            Trainer trainer = session.createQuery("select t from Trainer t where t.name like 'Ivan Ivanov'", Trainer.class)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
            assertNotNull(trainer);

            Trainee alexandrAlexandrov = new Trainee("Alexandr Alexandrov", trainer);
            Long id = traineeDao.save(alexandrAlexandrov);
            assertNotNull("Entity is not saved", id);
        }
    }

    @Test
    public void testDelete() {
        Trainee trainee;
        try (Session session = sessionFactory.openSession()) {
            trainee = session.createQuery("select t from Trainee t where t.name like 'Sergei Sergeev'", Trainee.class)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
            assertNotNull(trainee);
        }
        traineeDao.delete(trainee);
        try (Session session = sessionFactory.openSession()) {
            Trainee theSameTrainee = session.createQuery("select t from Trainee t where t.name like 'Sergei Sergeev'", Trainee.class)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
            assertNull("Entity is not null!", theSameTrainee);
        }
    }

    @Test
    public void testUpdate() {
        Trainee trainee;
        Trainer trainer;
        try (Session session = sessionFactory.openSession()) {
            trainee = session.createQuery("select t from Trainee t where t.name like 'Sergei Sergeev'", Trainee.class)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
            assertNotNull(trainee);
            trainer = session.createQuery("select t from Trainer t where t.name like 'Ivan Ivanov'", Trainer.class)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
            assertNotNull(trainer);
        }
        trainee.setTrainer(trainer);
        traineeDao.update(trainee);
        try (Session session = sessionFactory.openSession()) {
            Trainee updatedTrainee = session.createQuery("select t from Trainee t where t.name like 'Sergei Sergeev' and t.trainer.name='Ivan Ivanov'", Trainee.class)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
            assertNotNull(updatedTrainee);
        }
    }

    @Test
    public void findByTrainerName() {
        List<Trainee> trainees = traineeDao.findByTrainerName("andrei");
        assertTrue(trainees.size() == 3);
    }
}
