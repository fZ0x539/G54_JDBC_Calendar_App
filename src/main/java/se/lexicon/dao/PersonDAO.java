package se.lexicon.dao;

import se.lexicon.model.Person;

import java.util.Optional;

// Interface for Person DAO
public interface PersonDAO {
    Person save(Person person);
    Optional<Person> findById(int id);
    void update(Person person);
    void delete(int id);
}