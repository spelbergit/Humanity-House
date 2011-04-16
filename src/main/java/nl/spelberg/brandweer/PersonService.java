package nl.spelberg.brandweer;

import nl.spelberg.brandweer.model.Person;

public interface PersonService {

    public Person findPerson(Long id);

    public void addPerson(String fotoName, String fotoType, byte[] fotoData);

}
