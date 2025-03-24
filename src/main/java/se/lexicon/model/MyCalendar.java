package se.lexicon.model;


import java.util.Objects;

public class MyCalendar {
    private int id;
    private String name;
    private String description;
    private Person person;


    public MyCalendar(String name, String description, Person person) {
        setName(name);
        setDescription(description);
        setPerson(person);
    }

    public MyCalendar(int id, String name, String description, Person person) {
        this(name, description, person);
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        Objects.requireNonNull(person);
        if(person.getId() == 0){
            throw new IllegalArgumentException("Error MyCalendar.setPerson(): Person ID must be set.");
        }
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name);
        if (name.length() > 100){
            throw new StringIndexOutOfBoundsException("Name cannot exceed 100 characters");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MyCalendar{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", person=" + person +
                '}';
    }
}
