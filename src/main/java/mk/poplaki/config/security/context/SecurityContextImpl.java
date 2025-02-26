package mk.poplaki.config.security.context;

import mk.poplaki.config.security.user.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextImpl implements SecurityContext {

    @Override
    public Principal getPrincipal() {
        CustomUserDetails ud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Principal(ud.getId(), ud.getUsername(), ud.getRole());
    }
}
