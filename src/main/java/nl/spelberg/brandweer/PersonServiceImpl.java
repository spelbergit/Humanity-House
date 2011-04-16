package nl.spelberg.brandweer;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import nl.spelberg.brandweer.dao.PersonDAO;
import nl.spelberg.util.Utils;
import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.stereotype.Service;

@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Resource
    PersonDAO personDAO;

    @Override
    public Person findPerson(Long id) {
        Person person = personDAO.find(id);
        if (person == null) {
            try {
                ServletContext servletContext = ((WebApplication) Application.get()).getServletContext();
                InputStream inputStream = servletContext.getResourceAsStream(
                        "/images/Amsterdam-overstroming-03.jpg");
                byte[] fotoData = Utils.getBytes(inputStream);
                return new Person("", "rufus@branie.nl", "IMG001", "jpg", fotoData);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        } else {
            return person;
        }


    }

    @Override
    public void addPerson(String fotoName, String fotoType, byte[] fotoData) {
        throw new UnsupportedOperationException("TODO");
    }
}
