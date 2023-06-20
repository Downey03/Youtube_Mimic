package DTO;

import lombok.*;

import java.util.ArrayList;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String name;
    private String userEmail;
    private String password;
    private ArrayList<String> playLists;

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", password='" + password + '\'' +
                ", playLists=" + playLists +
                '}';
    }
}
