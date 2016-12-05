package com.jopss.exemploauth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;

public class FormatadorUtil {

        public static <T> T parseJSON(String json, Class clazz) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                        return (T) mapper.readValue(json, clazz);
                } catch (IOException ex) {
                        ex.printStackTrace();
                        throw new RuntimeException("parseJSON: " + ex.getMessage());
                }
        }

        public static String encryptMD5(String text) {
                if (text == null) {
                        return null;
                }
                return encryptMD5(text.getBytes());
        }

        public static String encryptMD5(byte[] text) {
                try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(text);
                        BigInteger hash = new BigInteger(1, md.digest());
                        return org.apache.commons.lang.StringUtils.leftPad(hash.toString(16), 32, '0');
                } catch (NoSuchAlgorithmException ex) {
                        throw new RuntimeException(ex);
                }
        }

        public static String encryptDES(String texto, String chave) {
                try {
                        Cipher ecipher;
                        SecretKey key;
                        key = new SecretKeySpec(chave.getBytes("UTF-8"), 0, 8, "DES");
                        ecipher = Cipher.getInstance("DES");
                        ecipher.init(Cipher.ENCRYPT_MODE, key);

                        byte[] utf8 = texto.getBytes("UTF8");
                        byte[] crip = ecipher.doFinal(utf8);
                        return new String(Hex.encodeHex(crip));
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
                        throw new RuntimeException(ex);
                }
        }

        public static String decryptDES(String texto, String chave) {
                try {
                        Cipher dcipher;
                        SecretKey key;
                        key = new SecretKeySpec(chave.getBytes(), 0, 8, "DES");
                        dcipher = Cipher.getInstance("DES");
                        dcipher.init(Cipher.DECRYPT_MODE, key);
                        byte[] dec = Hex.decodeHex(texto.toCharArray());
                        byte[] utf8 = dcipher.doFinal(dec);
                        return new String(utf8, "UTF8");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | DecoderException | UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
                        throw new RuntimeException(ex);
                }
        }

        public static String convertToBase64(byte[] bytes) {
                return Base64.encodeBase64String(bytes);
        }

        public static byte[] decodeBase64(String base64) {
                return Base64.decodeBase64(base64);
        }

        public static String readResourceFile(String filename) throws IOException {
                InputStream in = FormatadorUtil.class.getResourceAsStream(filename);
                if (in == null) {
                        throw new FileNotFoundException("Arquivo '" + filename + "' inexistente em '/class/resources/'.");
                }
                return IOUtils.toString(in, Charset.forName("UTF-8"));
        }

        public static void addLogInfo(String s) {
                System.out.println(s); //TODO: mudar para log4j ou equivalente.
        }

        public static void addLogError(String s, Exception... e) {
                System.out.println(s); //TODO: mudar para log4j ou equivalente.
        }

        public static void addLogDebug(String s) {
                System.out.println(s); //TODO: mudar para log4j ou equivalente.
        }
}
