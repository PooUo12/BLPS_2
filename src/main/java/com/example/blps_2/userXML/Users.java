package com.example.blps_2.userXML;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "UserList")
@XmlAccessorType(XmlAccessType.FIELD)
public class Users {
    @XmlElementWrapper(name="Users")
    @XmlElement(name="User")
    private List<UserXML> userList;
}
