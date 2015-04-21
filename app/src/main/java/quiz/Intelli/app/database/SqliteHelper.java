package quiz.Intelli.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import quiz.Intelli.app.database.IntelliContracts.Math;


/**
 * Created by Dan on 4/4/2015.
 */
public class SqliteHelper extends SQLiteOpenHelper
{
    public static final String DatabaseName = "IntelliDatabase";
    public static final int DatabaseVersion = 1;
    private String DeleteMathTable = "DROP TABLE IF EXISTS " + Math.tableName;

    private final String CreateMathTable = "CREATE TABLE IF NOT EXITS " + Math.tableName + "(" + Math._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Math.question + " TEXT NOT NULL, " + Math.correctAnswer + " TEXT NOT NULL, " + Math.firstIncorrect + " TEXT NOT NULL," +
            Math.secondIncorrect + " TEXT NOT NULL, " + Math.thirdIncorrect + " TEXT NOT NULL )";


    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CreateMathTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
         db.execSQL(DeleteMathTable);
    }
}
