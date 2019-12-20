package com.example.shiro.cache;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author csh9016
 * @date 2019/12/20
 */
public class PassService implements PasswordService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public PassService() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encryptPassword(Object plaintextPassword) throws IllegalArgumentException {
        return bCryptPasswordEncoder.encode((CharSequence) plaintextPassword);
    }

    @Override
    public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
        return bCryptPasswordEncoder.matches((CharSequence) submittedPlaintext,encrypted);
    }
}
