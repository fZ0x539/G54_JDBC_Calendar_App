package se.lexicon.dao.impl;

import se.lexicon.dao.MyCalendarDAO;
import se.lexicon.model.MyCalendar;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class MyCalendarDAOImpl implements MyCalendarDAO {

    Connection connection;

    public MyCalendarDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public MyCalendar save(MyCalendar calendar) {
        return null;
    }

    @Override
    public Optional<MyCalendar> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<MyCalendar> findAllByOwnerId(int ownerId) {
        return null;
    }

    @Override
    public void update(MyCalendar calendar) {

    }

    @Override
    public void delete(int id) {

    }
    // TODO: Needs completion

}
