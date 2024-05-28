package com.example.blps_2.userXML;

import com.example.blps_2.controller.QuestionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.channels.ScatteringByteChannel;
import java.util.stream.Collectors;

public class XMLWorker {
    private final Logger logger = LoggerFactory.getLogger(XMLWorker.class);
    public Users xmlRead() {
        Users users = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("./user.xml"));
            String body = br.lines().collect(Collectors.joining());
            StringReader reader = new StringReader(body);
            JAXBContext context = JAXBContext.newInstance(Users.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            users = (Users) unmarshaller.unmarshal(reader);
        } catch (FileNotFoundException e){
            logger.info("Cannot find xml file to read");
        } catch (JAXBException e) {
            logger.info("Error while processing user xml");
            e.printStackTrace();
        }
        return users;
    }

    public void xmlWrite(Users users) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("./user.xml"));
            JAXBContext context = JAXBContext.newInstance(Users.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(users, bw);
        } catch (FileNotFoundException e){
            logger.info("Cannot find xml file to write read file");
        } catch (JAXBException e) {
            e.printStackTrace();
            logger.info("Error while processing user xml write file");
        } catch (IOException e) {
            logger.info("Cannot write to file");
        }
    }
}
