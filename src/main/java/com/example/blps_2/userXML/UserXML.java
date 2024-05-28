package com.example.blps_2.userXML;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class UserXML {
    @XmlElement(name = "id")
    private long id;
    @XmlElement(name = "Username")
    private String username;
    @XmlElement(name = "Password")
    private String pass;
    @XmlElement(name = "Ratings")
    private int ratings;

}
