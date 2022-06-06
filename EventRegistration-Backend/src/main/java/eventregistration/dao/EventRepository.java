package eventregistration.dao;

import eventregistration.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, String> {
	
	Event findByName(String name);

}
