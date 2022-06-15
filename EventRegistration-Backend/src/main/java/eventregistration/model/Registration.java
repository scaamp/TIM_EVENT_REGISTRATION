package eventregistration.model;

import javax.persistence.*;

@Entity
public class Registration {

    private int id;

    public void setId(int value) {
        this.id = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    private Person person;

    @ManyToOne(optional = false)
    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    private Event event;

    @ManyToOne(optional = false)
    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
