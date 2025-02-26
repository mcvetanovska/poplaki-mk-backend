package mk.poplaki.config.security;

public class SecurityPatterns {

    private SecurityPatterns() {
    }

    protected static final String[] SWAGGER = {"/swagger-ui/**", "/swagger-ui.html", "/api-docs/**", "/api-docs.yaml"};
    protected static final String[] ACTUATOR = {"/actuator/**"};
    protected static final String[] AUTH = {"/v1/auth/**"};
    protected static final String[] REGISTER = {"/v1/users"};
    protected static final String[] ERROR = {"/error/**"};
    protected static final String[] ADMIN = {"/v1/admin/complaints/**", "/v1/admin/companies/**"};
    protected static final String[] PUBLIC = {"/v1/complaints/**", "/v1/companies/**", "/v1/users/count"};
    protected static final String[] ALL = {"/**"};
}
