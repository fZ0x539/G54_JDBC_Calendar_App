package se.lexicon.dao.impl;

import se.lexicon.dao.PersonDAO;
import se.lexicon.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PersonDAOImpl implements PersonDAO {

    Connection connection;

    public PersonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Person save(Person person) {
        String sql = "INSERT INTO person (name, email) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getEmail());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println(person.getName() + " has been added to the DB.");
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        person.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in PersonDAO.save(): " + e.getMessage());
        }
        return person;
    }

    @Override
    public Optional<Person> findById(int id) {
        String sql = "SELECT * from person WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new Person(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in PersonDAO.findById(): " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(Person person) {
        if (person.getId() <= 0) {
            throw new IllegalArgumentException("Person ID must be set for update.");
        }

        String sql = "UPDATE person SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getEmail());
            preparedStatement.setInt(3, person.getId());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Successfully updated Person with ID " + person.getId());
            } else {
                System.out.println("Couldn't find person with ID" + person.getId());
            }


        } catch (SQLException e) {
            System.err.println("Error in PersonDAO.update() " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM person WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Successfully deleted person with ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error in PeopleDao.deleteById() " + e.getMessage());
        }
        System.out.println("Couldn't find person with ID " + id);
    }
}
