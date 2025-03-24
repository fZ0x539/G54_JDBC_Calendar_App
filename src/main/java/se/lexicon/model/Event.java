package se.lexicon.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event {
    private int id;
    private int calendar_id;
    private String title;
    private String description;
    private LocalDateTime date_time;

    public Event(int calendar_id, String title, String description, LocalDateTime date_time) {
        setCalendar_id(calendar_id);
        setTitle(title);
        setDescription(description);
        setDate_time(date_time);
    }

    public Event(int id, int calendar_id, String title, String description, LocalDateTime date_time) {
        this(calendar_id, title, description, date_time);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCalendar_id() {
        return calendar_id;
    }

    public void setCalendar_id(int calendar_id) {
        this.calendar_id = calendar_id;
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
                ", calendar_id=" + calendar_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date_time=" + date_time +
                '}';
    }
}
