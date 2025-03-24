package se.lexicon;

import se.lexicon.dao.impl.PersonDAOImpl;
import se.lexicon.db.DatabaseManager;
import se.lexicon.model.Person;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseManager.getConnection()) {
            PersonDAOImpl personDao = new PersonDAOImpl(connection);
            Person person1 = new Person("Sven", "Hehe@gmail.com");
            person1 = personDao.save(person1);
            person1 = personDao.findById(person1.getId()).get();
            person1.setName("Adam");
            personDao.update(person1);
            personDao.findById(person1.getId()).ifPresent(System.out::println);
            personDao.delete(person1.getId());
            personDao.findById(person1.getId()).ifPresent(System.out::println);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}