package quiz.Intelli.app.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

import quiz.Intelli.app.database.IntelliContracts.Math;

/**
 * Created by Dan on 4/4/2015.
 */
public class IntelliProvider extends ContentProvider
{
    private static UriMatcher uriMatcher;
    private SqliteHelper sqliteHelper;

    private final int uploadMath = 1;
    private int queryMath = 2;

    private static HashMap<String, String> mathColumns;

    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(IntelliContracts.AUTHORITY, Math.MathUpload, 1);
        uriMatcher.addURI(IntelliContracts.AUTHORITY, Math.MathQuery, 2);

        mathColumns = new HashMap<String, String>();

        mathColumns.put(Math._ID, Math._ID);
        mathColumns.put(Math.question, Math.question);
        mathColumns.put(Math.correctAnswer, Math.correctAnswer);
        mathColumns.put(Math.firstIncorrect, Math.firstIncorrect);
        mathColumns.put(Math.secondIncorrect, Math.secondIncorrect);
        mathColumns.put(Math.thirdIncorrect, Math.thirdIncorrect);
    }

    @Override
    public boolean onCreate()
    {
        sqliteHelper = new SqliteHelper(getContext(), SqliteHelper.DatabaseName, null, SqliteHelper.DatabaseVersion);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        SQLiteDatabase database = sqliteHelper.getReadableDatabase();
        int uriMatch = uriMatcher.match(uri);

        if(uriMatch != 1 && uriMatch != 2)
        {
            throw new IllegalArgumentException("Unknown uri " + uri);
        }

        switch(uriMatch)
        {
            case uploadMath:
                sqLiteQueryBuilder.setTables(Math.tableName);
                sqLiteQueryBuilder.setProjectionMap(mathColumns);
                break;
        }

        //Cursor cursor = database.query()
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
