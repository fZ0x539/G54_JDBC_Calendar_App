package se.lexicon;

import se.lexicon.dao.impl.EventDAOImpl;
import se.lexicon.dao.impl.MyCalendarDAOImpl;
import se.lexicon.dao.impl.PersonDAOImpl;
import se.lexicon.db.DatabaseManager;
import se.lexicon.model.Event;
import se.lexicon.model.MyCalendar;
import se.lexicon.model.Person;
import se.lexicon.view.ConsoleUI;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseManager.getConnection()) {
            PersonDAOImpl personDao = new PersonDAOImpl(connection);
            MyCalendarDAOImpl calendarDAO = new MyCalendarDAOImpl(connection);
            EventDAOImpl eventDAO = new EventDAOImpl(connection);
            Person person1 = new Person("Sven", "Hehe@gmail.com");
            Person person2 = new Person("Ruben", "rubenson@gmail.com");
            person1 = personDao.save(person1);
            person2 = personDao.save(person2);

            person1 = personDao.findById(person1.getId()).get();
            person1.setName("Adam");
            personDao.update(person1);
            personDao.findById(person1.getId()).ifPresent(System.out::println);

            MyCalendar calendar1 = new MyCalendar("Q1", "Q1 2025", person1);
            MyCalendar calendar2 = new MyCalendar("Q2", "Q2 2025", person1);
            calendar1 = calendarDAO.save(calendar1);
            calendar2 = calendarDAO.save(calendar2);
            Event event1 = new Event(
                    "Spring Break",
                    "2w break from school",
                    LocalDateTime.parse("2025-02-20T09:00:00"),
                    calendar1);
            Event event2 = new Event(
                    "Shrove Tuesday",
                    "Pancake Tuesday",
                    LocalDateTime.parse("2025-03-04T00:00:00"),
                    calendar1);

            ConsoleUI.printInfo("findById:");
            System.out.println(calendarDAO.findById(calendar1.getId()).get());

            ConsoleUI.printInfo("findAllByOwnerId:");
            calendarDAO.findAllByOwnerId(person1.getId()).forEach(System.out::println);

            ConsoleUI.printInfo("MyCalendarDAO.update:");
            calendar1.setPerson(person2);
            calendarDAO.update(calendar1);
            System.out.println(calendarDAO.findById(calendar1.getId()).get());


            ConsoleUI.printInfo("EventDAO.save:");
            event1 = eventDAO.save(event1);
            event2 = eventDAO.save(event2);

            ConsoleUI.printInfo("EventDAO.findAllByCalendarId:");
            eventDAO.findAllByCalendarId(event1.getCalendar().getId()).forEach(System.out::println);

            ConsoleUI.printInfo("EventDAO.update:");
            event1.setDescription("1w break from school");
            event1.setCalendar(calendar2);
            eventDAO.update(event1); //Changing foreign key
            eventDAO.findAllByCalendarId(event1.getCalendar().getId()).forEach(System.out::println);





            eventDAO.delete(event1.getId());
            eventDAO.delete(event2.getId());
            calendarDAO.delete(calendar1.getId());
            calendarDAO.delete(calendar2.getId());
            personDao.delete(person1.getId());
            personDao.delete(person2.getId());
            personDao.findById(person1.getId()).ifPresent(System.out::println);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}