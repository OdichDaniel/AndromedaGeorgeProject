package quiz.Intelli.app.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Dan on 4/20/2015.
 */
public class CommonUtils
{
    public static String encodeString(String encode)
    {
        try {
            return URLEncoder.encode(encode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return encode;
        }
    }
}
