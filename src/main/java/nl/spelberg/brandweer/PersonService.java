package nl.spelberg.brandweer;

public interface PersonService {

    public Person findPerson(Long id);

    public void addPerson(String fotoName, String fotoType, byte[] fotoData);

}
