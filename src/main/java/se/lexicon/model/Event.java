package se.lexicon.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event {
    private int id;
    private String title;
    private String description;
    private LocalDateTime date_time;
    private MyCalendar calendar;

    public Event(String title, String description, LocalDateTime date_time, MyCalendar calendar) {
        setTitle(title);
        setDescription(description);
        setDate_time(date_time);
        setCalendar(calendar);
    }

    public Event(int id, String title, String description, LocalDateTime date_time, MyCalendar calendar) {
        this(title, description, date_time, calendar);
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(MyCalendar calendar) {
        this.calendar = calendar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Objects.requireNonNull(title);
        if (title.length() > 100){
            throw new StringIndexOutOfBoundsException("Name cannot exceed 100 characters");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        Objects.requireNonNull(date_time);
        this.date_time = date_time;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date_time=" + date_time +
                ", calendar=" + calendar +
                '}';
    }
}
