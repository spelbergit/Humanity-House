package nl.spelberg.brandweer.model.impl;

import nl.spelberg.brandweer.dao.PersonDAO;
import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.Photo;
import nl.spelberg.brandweer.model.PhotoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {

    @Mock
    private PersonDAO personDAO;

    @Mock
    private PhotoService photoService;

    @InjectMocks
    private PersonServiceImpl personService = new PersonServiceImpl();

    @Test
    public void testGetMostRecentPersonExists() throws Exception {
        Photo photo = mock(Photo.class);
        Person person = mock(Person.class);
        when(person.photo()).thenReturn(photo);

        when(photoService.findMostRecentPhoto()).thenReturn(photo);
        when(personDAO.find(photo)).thenReturn(person);

        Person p = personService.getMostRecentPerson();

        assertEquals(person, p);

        verify(personDAO, never()).persist(Matchers.<Person>any());
    }

    @Test
    public void testGetMostRecentPersonNew() throws Exception {
        Photo photo = mock(Photo.class);

        when(photoService.findMostRecentPhoto()).thenReturn(photo);
        when(personDAO.find(photo)).thenReturn(null);

        Person p = personService.getMostRecentPerson();

        assertEquals(photo, p.photo());

        verify(personDAO).persist(Matchers.<Person>any());
    }
}
