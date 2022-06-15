package eventregistration.service;

import eventregistration.model.Event;
import eventregistration.model.Person;
import eventregistration.model.Registration;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface EventRegistrationServiceInterface {
    public Person createPerson(String name);

    public Person getPerson(String name);

    public List<Person> getAllPersons();

    public Event createEvent(String name, Date date, Time startTime, Time endTime);

    public Event getEvent(String name);

    public List<Event> getAllEvents();

    public List<Registration> getAllRegistrations();

    public Registration getRegistrationByPersonAndEvent(Person person, Event event);

    public <Optional> java.util.Optional<Registration> getRegistrationById(Integer id);

    public List<Registration> getRegistrationsForPerson(Person person);

    public List<Event> getEventsAttendedByPerson(Person person);

    public void deletePerson(String name);

    public void updatePerson(String name, Person person);

    public void deleteEvent(String name);

    public void deleteRegistration(Integer id);

    public <T> List<T> toList(Iterable<T> iterable);

}
