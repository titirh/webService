/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  mobile.WSmobile_money.tiavina;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 *
 * @author Tiavina
 */
public class Cryptage {
	
    public static final String DEFAULT_ENCODING = "UTF-8"; 
    static Base64.Encoder enc;
    static Base64.Decoder dec;

    public static String base64encode(String text) {
        try {
            return text;
        } catch (Exception e) {
            return null;
        }
    }//base64encode

    public static String base64decode(String text) {
        try {
            return text;
        } catch (Exception e) {
            return null;
        }
    }//base64decode
}
