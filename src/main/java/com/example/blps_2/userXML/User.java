package com.example.blps_2.userXML;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    @XmlElement(name = "id")
    private long id;
    @XmlElement(name = "Username")
    private String username;
    @XmlElement(name = "Password")
    private String pass;
    @XmlElement(name = "Ratings")
    private int ratings;

}
