package se.lexicon.dao.impl;

import se.lexicon.dao.MyCalendarDAO;
import se.lexicon.model.MyCalendar;
import se.lexicon.model.Person;
import se.lexicon.view.ConsoleUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyCalendarDAOImpl implements MyCalendarDAO {

    Connection connection;

    public MyCalendarDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public MyCalendar save(MyCalendar calendar) {
        String sql = "INSERT INTO my_calendar (person_id, name, description ) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, calendar.getPerson().getId());
            preparedStatement.setString(2, calendar.getName());
            preparedStatement.setString(3, calendar.getDescription());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                ConsoleUI.printSuccess("Calendar '" + calendar.getName() + "' has been added to the DB.");
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        calendar.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in MyCalendarDAO.save(): " + e.getMessage());
        }
        return calendar;
    }

    @Override
    public Optional<MyCalendar> findById(int id) {
        String sql = "SELECT * from my_calendar mc JOIN person p ON mc.person_id = p.id WHERE mc.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new MyCalendar(
                            rs.getInt("mc.id"),
                            rs.getString("mc.name"),
                            rs.getString("mc.description"),
                            new Person(
                                    rs.getInt("p.id"),
                                    rs.getString("p.name"),
                                    rs.getString("p.email")
                            )));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in MyCalendarDAO.findById(): " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<MyCalendar> findAllByOwnerId(int ownerId) {
        List<MyCalendar> resultList = new ArrayList<>();
        String sql = "SELECT * from my_calendar mc JOIN person p ON mc.person_id = p.id WHERE p.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, ownerId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    resultList.add(new MyCalendar(
                            rs.getInt("mc.id"),
                            rs.getString("mc.name"),
                            rs.getString("mc.description"),
                            new Person(
                                    rs.getInt("p.id"),
                                    rs.getString("p.name"),
                                    rs.getString("p.email")
                            )));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in MyCalendarDAO.findAllByOwnerId(): " + e.getMessage());
        }
        return resultList;
    }

    @Override
    public void update(MyCalendar calendar) {
        if (calendar.getId() == 0) {
            throw new IllegalArgumentException("Calendar ID must be set for update.");
        }

        String sql = "UPDATE my_calendar SET name = ?, description = ?, person_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, calendar.getName());
            preparedStatement.setString(2, calendar.getDescription());
            preparedStatement.setInt(3, calendar.getPerson().getId());
            preparedStatement.setInt(4, calendar.getId());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                ConsoleUI.printSuccess("Successfully updated calendar with ID " + calendar.getId());
            } else {
                ConsoleUI.printError("Couldn't find calendar with ID" + calendar.getId());
            }


        } catch (SQLException e) {
            System.err.println("Error in MyCalendarDAO.update() " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM my_calendar WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0)
                ConsoleUI.printWarn("Successfully deleted calendar with ID " + id);
            else
                ConsoleUI.printError("Couldn't find calendar with ID " + id);
        } catch (SQLException e) {
            System.err.println("Error in MyCalendarDAO.delete() " + e.getMessage());
        }

    }
    // TODO: Needs completion

}
