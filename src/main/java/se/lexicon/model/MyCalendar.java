package se.lexicon.model;


import java.util.Objects;

public class MyCalendar {
    private int id;
    private int person_id;
    private String name;
    private String description;


    public MyCalendar(int person_id, String name, String description) {
        setPerson_id(person_id);
        setName(name);
        setDescription(description);
    }

    public MyCalendar(int id, int person_id, String name, String description) {
        this(person_id, name, description);
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
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
                ", person_id=" + person_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
