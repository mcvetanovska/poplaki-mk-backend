package mk.poplaki.config.security.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Principal {
    private String userId;
    private String username;
    private String role;
}
