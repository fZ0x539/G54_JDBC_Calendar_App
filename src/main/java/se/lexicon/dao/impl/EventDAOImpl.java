package se.lexicon.dao.impl;

import se.lexicon.dao.EventDAO;
import se.lexicon.model.Event;

import java.sql.Connection;
import java.util.List;

public class EventDAOImpl implements EventDAO {

    Connection connection;

    public EventDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Event save(Event event) {
        return null;
    }

    @Override
    public List<Event> findAllByCalendarId(int calendarId) {
        return null;
    }

    @Override
    public void update(Event event) {

    }

    @Override
    public void delete(int id) {

    }
    // TODO: Needs completion

}