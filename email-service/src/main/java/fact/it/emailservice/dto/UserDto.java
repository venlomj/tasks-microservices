package fact.it.emailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String userCode;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String taskCode;
}
