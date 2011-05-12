package nl.spelberg.brandweer.model.impl;

import com.mindprod.csv.CSVWriter;
import java.io.StringWriter;
import java.util.List;
import nl.spelberg.brandweer.dao.PersonDAO;
import nl.spelberg.brandweer.model.ExportService;
import nl.spelberg.brandweer.model.Person;
import nl.spelberg.brandweer.model.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("exportService")
@Transactional
public class ExportServiceImpl implements ExportService {

    @Autowired
    PersonDAO personDAO;

    @Override
    public String exportAsCsv() {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);

        // header
        csvWriter.put("ID");
        csvWriter.put("Naam");
        csvWriter.put("Email");
        csvWriter.put("Foto");
        csvWriter.nl();

        // data
        List<Person> persons = personDAO.all();
        for (Person person : persons) {
            csvWriter.put(String.valueOf(person.id()));
            csvWriter.put(person.name());
            csvWriter.put(person.email());
            csvWriter.put(Photo.format(person.foto()));
            csvWriter.nl();
        }


        return stringWriter.toString();
    }
}
