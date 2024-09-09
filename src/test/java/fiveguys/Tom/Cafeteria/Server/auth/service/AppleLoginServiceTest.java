package fiveguys.Tom.Cafeteria.Server.auth.service;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import static org.junit.jupiter.api.Assertions.*;

class AppleLoginServiceTest {
    @Test
    void appleKeyReadTest() throws IOException {
        String keyPath = "/Users/wuseong/Downloads/Tom-Cafeteria-Server/src/main/resources/static/key/AuthKey_KM84FK6UX8.p8";
        String privateKey = new String(Files.readAllBytes(Paths.get(keyPath) ) );
        PemReader pemReader = new PemReader(new StringReader(privateKey));
        PemObject pemObject = pemReader.readPemObject();
        byte[] content = pemObject.getContent();
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(content);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey key = keyFactory.generatePrivate(privateKeySpec);
            System.out.println(key);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}