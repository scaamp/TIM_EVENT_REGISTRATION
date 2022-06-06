package eventregistration.dao;


import eventregistration.model.Event;
import eventregistration.model.Person;
import eventregistration.model.Registration;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegistrationRepository extends CrudRepository<Registration, Integer> {
    
    List<Registration> findByPerson(Person personName);
    
    boolean existsByPersonAndEvent(Person person, Event eventName);
    
    Registration findByPersonAndEvent(Person person, Event eventName);
    
}
