package org.example.util;

import lombok.Value;
import lombok.experimental.UtilityClass;
import org.example.model.Report;
import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class Factory {
    public static final SessionFactory FACTORY = new Configuration()
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(Report.class)
            .buildSessionFactory();

}
