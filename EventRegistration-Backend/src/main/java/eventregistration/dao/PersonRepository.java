package eventregistration.dao;

import eventregistration.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String>{
	
	Person findPersonByName(String name);
	
}
