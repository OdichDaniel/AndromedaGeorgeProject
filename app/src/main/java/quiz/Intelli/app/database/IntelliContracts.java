package quiz.Intelli.app.database;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

/**
 * Created by Dan on 4/4/2015.
 */
public class IntelliContracts
{
    public static String AUTHORITY = "quiz.intelli.app.database";
    public static String AUTHORITY_URI = "content://" + AUTHORITY;

    public static final class Math implements BaseColumns
    {
        public static final String MathUpload = "MathUpload";
        public static final String MathQuery = "MathQuery";

        public static final Uri MathUploadUri = Uri.parse(AUTHORITY_URI + MathUpload);
        public static final Uri MathQueryUri = Uri.parse(AUTHORITY_URI + MathQuery);

        public static final String tableName = "math";
        public static final String question = "question";
        public static final String correctAnswer = "correctAnswer";
        public static final String firstIncorrect = "firstIncorrect";
        public static final String secondIncorrect = "secondIncorrect";
        public static final String thirdIncorrect = "thirdIncorrect";
    }
}
