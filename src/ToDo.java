import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToDo {

    private List<String> events;
    private LocalDate date;

    public ToDo(LocalDate date, String event) {

        setDate(date);

        events = new ArrayList<>();
        events.add(event);
    }

    public ToDo(LocalDate date, List<String> events) {

        setDate(date);
        setEvents(events);
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
