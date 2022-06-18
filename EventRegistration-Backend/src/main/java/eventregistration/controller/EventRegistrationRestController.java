package eventregistration.controller;

import eventregistration.dto.EventDto;
import eventregistration.dto.PersonDto;
import eventregistration.dto.RegistrationDto;
import eventregistration.model.Event;
import eventregistration.model.Person;
import eventregistration.model.Registration;
import eventregistration.service.EventRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class EventRegistrationRestController {

    @Autowired
    private EventRegistrationService service;

    @PostMapping(value = {"/persons/{name}", "/persons/{name}/"})
    public PersonDto createPerson(@PathVariable("name") String name) throws IllegalArgumentException {
        // @formatter:on
        Person person = service.createPerson(name);
        return convertToDto(person);
    }

    @PostMapping(value = {"/events/{name}", "/events/{name}/"})
    public EventDto createEvent(@PathVariable("name") String name, @RequestParam Date date,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime)
            throws IllegalArgumentException {
        // @formatter:on
        Event event = service.createEvent(name, date, Time.valueOf(startTime), Time.valueOf(endTime));
        return convertToDto(event);
    }

    @PostMapping(value = {"/register", "/register/"})
    public RegistrationDto registerPersonForEvent(@RequestParam(name = "person") PersonDto pDto,
                                                  @RequestParam(name = "event") EventDto eDto) throws IllegalArgumentException {
        Person p = service.getPerson(pDto.getName());
        Event e = service.getEvent(eDto.getName());

        Registration r = service.register(p, e);
        return convertToDto(r, p, e);
    }

    // GET Mappings

    @GetMapping(value = {"/events", "/events/"})
    public List<EventDto> getAllEvents() {
        List<EventDto> eventDtos = new ArrayList<>();
        for (Event event : service.getAllEvents()) {
            eventDtos.add(convertToDto(event));
        }
        return eventDtos;
    }

    @GetMapping(value = {"/events/person/{name}", "/events/person/{name}/"})
    public List<EventDto> getEventsOfPerson(@PathVariable("name") PersonDto pDto) {
        Person p = convertToDomainObject(pDto);
        return createAttendedEventDtosForPerson(p);
    }

    @GetMapping(value = {"/persons/{name}", "/persons/{name}/"})
    public PersonDto getPersonByName(@PathVariable("name") String name) throws IllegalArgumentException {
        return convertToDto(service.getPerson(name));
    }

    @GetMapping(value = {"/registrations/all", "/registrations/all/"})
    public List<RegistrationDto> getAllRegistrations() {
        List<RegistrationDto> registrationDtos = new ArrayList<>();
        for (Registration registration : service.getAllRegistrations()) {
            registrationDtos.add(convertToDto(registration));
        }
        return registrationDtos;
    }

    @GetMapping({"/registrations/{id}"})
    public Optional<Registration> getRegistrationById(@PathVariable("id") Integer id) {
        return service.getRegistrationById(id);
    }

    @GetMapping(value = {"/registrations/", "/registrations/"})
    public RegistrationDto getRegistration(@RequestParam(name = "person") PersonDto pDto,
                                           @RequestParam(name = "event") EventDto eDto) throws IllegalArgumentException {
        Person p = service.getPerson(pDto.getName());
        Event e = service.getEvent(eDto.getName());

        Registration r = service.getRegistrationByPersonAndEvent(p, e);
        return convertToDtoWithoutPerson(r);
    }

    @GetMapping(value = {"/registrations/person/{name}", "/registrations/person/{name}/"})
    public List<RegistrationDto> getRegistrationsForPerson(@PathVariable("name") PersonDto pDto)
            throws IllegalArgumentException {
        Person p = service.getPerson(pDto.getName());

        return createRegistrationDtosForPerson(p);
    }

    @GetMapping(value = {"/persons", "/persons/"})
    public List<PersonDto> getAllPersons() {
        List<PersonDto> persons = new ArrayList<>();
        for (Person person : service.getAllPersons()) {
            persons.add(convertToDto(person));
        }
        return persons;
    }

    @GetMapping(value = {"/events/{name}", "/events/{name}/"})
    public EventDto getEventByName(@PathVariable("name") String name) throws IllegalArgumentException {
        return convertToDto(service.getEvent(name));
    }

    // DELETE Mappings

    @DeleteMapping({"/persons/{name}"})
    public ResponseEntity<Person> deletePerson(@PathVariable("name") String name) {
        service.deletePerson(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/events/{name}"})
    public ResponseEntity<Event> deleteEvent(@PathVariable("name") String name) {
        service.deleteEvent(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/registrations/{id}"})
    public ResponseEntity<Registration> deleteRegistration(@PathVariable("id") Integer id) {
        service.deleteRegistration(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/registrations/person/{name}", "/registrations/person/{name}/"})
    public ResponseEntity<Registration> deleteRegistrationsForPerson(@PathVariable("name") PersonDto pDto)
            throws IllegalArgumentException {
        Person p = service.getPerson(pDto.getName());
        List<Registration> r = service.getRegistrationsByPerson(p);
        for (Registration registration : r) {
            service.deleteRegistration(registration.getId());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @DeleteMapping(value = {"/registrations/", "/registrations/"})
//    public ResponseEntity<Registration> deleteRegistration(@RequestParam(name = "person") PersonDto pDto,
//                                           @RequestParam(name = "event") EventDto eDto) throws IllegalArgumentException {
//        Person p = service.getPerson(pDto.getName());
//        Event e = service.getEvent(eDto.getName());
//
//        Registration r = service.getRegistrationByPersonAndEvent(p, e);
//        service.deleteRegistration(r.getId());
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    // PUT Mappings

    @PutMapping(value = {"/persons/{name}", "/persons/{name}/"})
    public ResponseEntity<Person> updatePerson(@PathVariable("name") String name, @RequestBody Person person) throws IllegalArgumentException {
        service.updatePerson(name, person);
        return new ResponseEntity<>(service.getPerson(name), HttpStatus.OK);
    }

    @PutMapping(value = {"/events/{name}", "/events/{name}/"})
    public EventDto updateEvent(@PathVariable("name") String name,  @RequestParam Date date,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime) throws IllegalArgumentException {
        Event eventFromPathVariable = service.getEvent(name);
        eventFromPathVariable.setDate(date);
        eventFromPathVariable.setStartTime(Time.valueOf(startTime));
        eventFromPathVariable.setEndTime(Time.valueOf(endTime));
        return convertToDto(service.getEvent(name));
    }

//    @PutMapping(value = {"/events/{name}", "/events/{name}/"})
//    public EventDto updateEvent(@PathVariable("name") String name, @RequestBody EventDto eDto) throws IllegalArgumentException {
//        service.updateEvent(name, eDto);
//        return convertToDto(service.getEvent(name));
//    }
    
//  @PutMapping(value = {"/persons/{name}", "/persons/{name}/"})
//  public PersonDto updatePerson(@PathVariable("name") String name, @RequestBody PersonDto pDto) throws IllegalArgumentException {
//        Person p = service.getPerson(name);
//        p.setName(pDto.getName());
//
//        return convertToDto(service.getPerson(name));
//    }

//    @PutMapping(value = {"/events/{name}", "/events/{name}/"})
//    public ResponseEntity<Event> updateEvent(@PathVariable("name") String name, @RequestBody Event event) throws IllegalArgumentException {
//        service.updateEvent(name, event);
//        return new ResponseEntity<>(service.getEvent(name), HttpStatus.OK);
//    }


    private EventDto convertToDto(Event e) {
        if (e == null) {
            throw new IllegalArgumentException("There is no such Event!");
        }
        return new EventDto(e.getName(), e.getDate(), e.getStartTime(), e.getEndTime());
    }

    private PersonDto convertToDto(Person p) {
        if (p == null) {
            throw new IllegalArgumentException("There is no such Person!");
        }
        PersonDto personDto = new PersonDto(p.getName());
        personDto.setEvents(createAttendedEventDtosForPerson(p));
        return personDto;
    }

    private RegistrationDto convertToDto(Registration r, Person p, Event e) {
        EventDto eDto = convertToDto(e);
        PersonDto pDto = convertToDto(p);
        return new RegistrationDto(pDto, eDto);
    }

    private RegistrationDto convertToDto(Registration r) {
        EventDto eDto = convertToDto(r.getEvent());
        PersonDto pDto = convertToDto(r.getPerson());
        return new RegistrationDto(pDto, eDto);
    }

    private RegistrationDto convertToDtoWithoutPerson(Registration r) {
        RegistrationDto rDto = convertToDto(r);
        rDto.setPerson(null);
        return rDto;
    }

    private Person convertToDomainObject(PersonDto pDto) {
        List<Person> allPersons = service.getAllPersons();
        for (Person person : allPersons) {
            if (person.getName().equals(pDto.getName())) {
                return person;
            }
        }
        return null;
    }

    private List<EventDto> createAttendedEventDtosForPerson(Person p) {
        List<Event> eventsForPerson = service.getEventsAttendedByPerson(p);
        List<EventDto> events = new ArrayList<>();
        for (Event event : eventsForPerson) {
            events.add(convertToDto(event));
        }
        return events;
    }

    private List<RegistrationDto> createRegistrationDtosForPerson(Person p) {
        List<Registration> registrationsForPerson = service.getRegistrationsForPerson(p);
        List<RegistrationDto> registrations = new ArrayList<RegistrationDto>();
        for (Registration r : registrationsForPerson) {
            registrations.add(convertToDtoWithoutPerson(r));
        }
        return registrations;
    }
}
