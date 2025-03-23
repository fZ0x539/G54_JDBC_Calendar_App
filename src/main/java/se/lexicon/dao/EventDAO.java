package se.lexicon.dao;

import se.lexicon.model.Event;

import java.util.List;
import java.util.Optional;

// Interface for Event DAO
public interface EventDAO {
    Event save(Event event);
    List<Event> findAllByCalendarId(int calendarId);
    void update(Event event);
    void delete(int id);
}