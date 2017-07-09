package at.meroff.itproject.xml;

import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.XQuery;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * Created by fragner on 01.06.17.
 */
public class XMLQueryTemplate<T> {
    final Class<T> typeParameterClass;
    private T result;
    private String query;
    private Context context = new Context();

    public XMLQueryTemplate(String query, Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        this.query = query;
        this.init();
    }

    public XMLQueryTemplate(File file, Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.query = new String(encoded, Charset.defaultCharset());
        this.init();
    }

    private void init() {
        try {
            String execute = new XQuery(query).execute(context);
            JAXBContext jaxbContext = JAXBContext.newInstance(typeParameterClass);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(execute);
            this.result = (T) jaxbUnmarshaller.unmarshal(reader);
        } catch (BaseXException | JAXBException e) {
            e.printStackTrace();
        }
    }

    public T getResult() {
        return result;
    }
}
