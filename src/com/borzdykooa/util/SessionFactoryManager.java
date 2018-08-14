package com.borzdykooa.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
Класс, отвечающий за создание единственного экземпляра SessionFactory и предоставление глобальной точки доступа к нему
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SessionFactoryManager {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
