package exhibition.exhibition.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Aes128UtilTest {


    @Test
    void test() {
        String plainText = "나는 이명규 입니다.";
        String cipherText = Aes128Util.encrypt(plainText);
        System.out.println(plainText);
        System.out.println(cipherText);
        assertEquals(plainText, Aes128Util.decrypt(cipherText));
    }

}