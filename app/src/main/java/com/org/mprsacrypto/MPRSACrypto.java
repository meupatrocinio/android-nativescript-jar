package com.org.mprsacrypto;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MPRSACrypto {
    private static PublicKey getPublicKey(String base64PublicKey) {
        PublicKey publicKey = null;

        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                    Base64.decode(base64PublicKey.getBytes(), Base64.DEFAULT)
            );
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return publicKey;
    }

    public static String encrypt(
            String pubKey,
            String text
    ) {
        pubKey = pubKey.replace("-----BEGIN PUBLIC KEY-----\n", "");
        pubKey = pubKey.replace("\n-----END PUBLIC KEY-----", "");

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, MPRSACrypto.getPublicKey(pubKey));
            return new String(
                    Base64.encode(
                            cipher.doFinal(text.getBytes()),
                            Base64.DEFAULT
                    )
            );
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return "";
    }
}
