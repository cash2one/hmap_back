package hmap.core.code;

import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

/**
 * Created by welcome on 2016/9/21.
 */
public abstract class CustomLengthRandomValueAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {
    private RandomValueStringGenerator generator = new RandomValueStringGenerator(32);

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = this.generator.generate();
        this.store(code, authentication);
        return code;
    }
}
