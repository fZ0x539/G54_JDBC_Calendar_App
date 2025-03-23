package se.lexicon.dao;

import se.lexicon.model.MyCalendar;

import java.util.List;
import java.util.Optional;

// Interface for MyCalendar DAO
public interface MyCalendarDAO {
    MyCalendar save(MyCalendar calendar);
    Optional<MyCalendar> findById(int id);
    List<MyCalendar> findAllByOwnerId(int ownerId);
    void update(MyCalendar calendar);
    void delete(int id);
}