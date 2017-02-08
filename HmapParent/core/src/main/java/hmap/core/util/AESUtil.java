package hmap.core.util;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by shanhd on 2016/11/7.
 */
public class AESUtil {

    /*******************************************************************************
     * AES加解密算法
     *
     * @author jueyue
     *

    加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
    此处使用AES-128-CBC加密模式，key需要为16位。
    也是使用<span style="font-size: 1em; line-height: 1.5;">0102030405060708</span>
     */

    // 加密
    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(Constant.AES_VI.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());

        return Base64.encodeBase64String(encrypted);//此处使用BAES64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String decrypt(String sSrc, String sKey) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return sSrc;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return sSrc;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(Constant.AES_VI.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decodeBase64(sSrc);//先用bAES64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return sSrc;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return sSrc;
        }
    }


    public static void main(String[] args) throws Exception {

        String content = "5z3A~3Rb^ocJ";
        String password = Constant.AES_KEY;

        System.out.println("加密前：" + content);
        String encryStrptResult = encrypt(content, password);
        System.out.println("加密后：" + encryStrptResult);

        String decryptResult =decrypt("q9Aaj1KHI3eC1uANlSPH2A==", password);
        System.out.println(decryptResult);
    }
}
