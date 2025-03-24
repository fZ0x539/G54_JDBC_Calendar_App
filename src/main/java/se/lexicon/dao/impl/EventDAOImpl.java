package se.lexicon.dao.impl;

import se.lexicon.dao.EventDAO;
import se.lexicon.model.Event;
import se.lexicon.model.MyCalendar;
import se.lexicon.model.Person;
import se.lexicon.view.ConsoleUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventDAOImpl implements EventDAO {

    Connection connection;

    public EventDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Event save(Event event) {
        String sql = "INSERT INTO event (calendar_id, title, description, date_time ) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, event.getCalendar().getId());
            preparedStatement.setString(2, event.getTitle());
            preparedStatement.setString(3, event.getDescription());
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(event.getDate_time()));

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                ConsoleUI.printSuccess("Event '" + event.getTitle() + "' has been added to the DB.");
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        event.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in EventDAO.save(): " + e.getMessage());
        }
        return event;
    }

    @Override
    public List<Event> findAllByCalendarId(int calendarId) {
        List<Event> resultList = new ArrayList<>();
        String sql = "SELECT \n" +
                "    e.id AS event_id,\n" +
                "    e.title,\n" +
                "    e.description AS event_description,\n" +
                "    e.date_time,\n" +
                "    c.id AS calendar_id,\n" +
                "    c.name AS calendar_name,\n" +
                "    c.description AS calendar_description,\n" +
                "    p.id AS person_id,\n" +
                "    p.name AS person_name,\n" +
                "    p.email AS person_email,\n" +
                "    ep.email AS participant_email\n" +
                "FROM \n" +
                "    event e\n" +
                "JOIN \n" +
                "    my_calendar c ON e.calendar_id = c.id\n" +
                "JOIN \n" +
                "    person p ON c.person_id = p.id\n" +
                "LEFT JOIN \n" +
                "    event_participants ep ON e.id = ep.event_id\n" +
                "WHERE \n" +
                "    e.calendar_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, calendarId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    resultList.add(new Event(
                            rs.getInt("event_id"),
                            rs.getString("e.title"),
                            rs.getString("event_description"),
                            rs.getTimestamp("e.date_time").toLocalDateTime(),
                            new MyCalendar(
                                    rs.getInt("calendar_id"),
                                    rs.getString("calendar_name"),
                                    rs.getString("calendar_description"),
                                    new Person(
                                            rs.getInt("person_id"),
                                            rs.getString("person_name"),
                                            rs.getString("person_email")
                                    ))));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in EventDAO.findAllByCalendarId(): " + e.getMessage());
        }
        return resultList;
    }

    @Override
    public void update(Event event) {
        if (event.getId() == 0) {
            throw new IllegalArgumentException("Calendar ID must be set for update.");
        }

        String sql = "UPDATE event SET title = ?, description = ?, date_time = ?, calendar_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, event.getTitle());
            preparedStatement.setString(2, event.getDescription());
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(event.getDate_time()));
            preparedStatement.setInt(4, event.getCalendar().getId());
            preparedStatement.setInt(5, event.getId());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                ConsoleUI.printSuccess("Successfully updated event with ID " + event.getId());
            } else {
                ConsoleUI.printError("Couldn't find event with ID" + event.getId());
            }


        } catch (SQLException e) {
            System.err.println("Error in EventDAO.update() " + e.getMessage());
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM event WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0)
                ConsoleUI.printWarn("Successfully deleted event with ID " + id);
            else
                ConsoleUI.printError("Couldn't find event with ID " + id);
        } catch (SQLException e) {
            System.err.println("Error in EventDAO.delete() " + e.getMessage());
        }
    }
    // TODO: Needs completion

}