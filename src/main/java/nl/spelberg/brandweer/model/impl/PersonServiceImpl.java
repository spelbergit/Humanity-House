package nl.spelberg.brandweer.model.impl;

import nl.spelberg.brandweer.dao.PersonDAO;
import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.PersonService;
import nl.spelberg.brandweer.model.Photo;
import nl.spelberg.brandweer.model.PhotoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("personService")
@Transactional
public class PersonServiceImpl implements PersonService {

    private static final Log log = LogFactory.getLog(PersonServiceImpl.class);

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private PhotoService photoService;

    @Override
    public Person findPerson(Long id) {
        return personDAO.find(id);
    }

    @Override
    public Person getMostRecentPerson() {

        // check if there is a new photo
        Photo mostRecentPhoto = photoService.findMostRecentPhoto();

        Person person = personDAO.find(mostRecentPhoto);

        if (person == null) {

            // new photo detected, create person
            Person newPerson = new Person(mostRecentPhoto);
            personDAO.persist(newPerson);
            log.info("Most Recent Person created: " + person);
            return newPerson;

        } else {

            log.info("Most Recent Person already exists: " + person);
            return person;
        }
    }

}
