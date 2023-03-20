package exhibition.exhibition.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordEncoder {
    private final MessageDigest digest;
    private final static String ALGORITHM = "SHA-512";

    public PasswordEncoder() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance(ALGORITHM);
    }

    public String encode(String password) {
        if (!StringUtils.hasText(password)) {
            return null;
        }
        digest.reset();
        digest.update(password.getBytes(StandardCharsets.UTF_8));
        return String.format("%0128x", new BigInteger(1, digest.digest()));
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
