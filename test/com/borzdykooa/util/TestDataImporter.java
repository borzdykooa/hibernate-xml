package com.borzdykooa.util;

import com.borzdykooa.entity.Trainee;
import com.borzdykooa.entity.Trainer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/*
Класс для импортирования тестовых данных
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestDataImporter {

    private static TestDataImporter INSTANCE = new TestDataImporter();

    public static TestDataImporter getInstance() {
        return INSTANCE;
    }

    public void importTestData(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Trainer andreiReut = new Trainer("Andrei Reut", "Java", 6);
            Trainer ivanIvanov = new Trainer("Ivan Ivanov", "C++", 4);
            Trainer petrPetrov = new Trainer("Petr Petrov", "C#", 5);
            session.save(andreiReut);
            session.save(ivanIvanov);
            session.save(petrPetrov);

            Trainee olgaBorzdyko = new Trainee("Olga Borzdyko", andreiReut);
            Trainee denisByhovsky = new Trainee("Denis Byhovsky", andreiReut);
            Trainee sergeiSergeev = new Trainee("Sergei Sergeev", andreiReut);
            session.save(olgaBorzdyko);
            session.save(denisByhovsky);
            session.save(sergeiSergeev);


            session.getTransaction().commit();
        }
    }

    public void deleteTestData(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from Trainee ").executeUpdate();
            session.createQuery("delete from Trainer ").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
