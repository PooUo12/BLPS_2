package com.example.blps_2.userXML;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@AllArgsConstructor
@XmlRootElement(name = "UserList")
@XmlAccessorType(XmlAccessType.FIELD)
public class Users {
    @XmlElementWrapper(name="Users")
    @XmlElement(name="User")
    private List<User> userList;
}
