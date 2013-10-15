package pe.javapetit.apps.porlosNinos.model.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created with IntelliJ IDEA.
 * User: JavaPetit
 * Date: 06/10/13
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class NinosOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pe.javapetit.apps.porlosNinos.db";
    private static final int DATABASE_VERSION = 1;

    public static final class ChildTable {

        public static final String TABLE_NAME = "Child";

        public static final class Columns implements BaseColumns {

            public static final String NAME = "name";
            public static final String DESCRIPTION = "description";
            public static final String PHOTODEFAULTPATH = "photoDefaultPath";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
        }

    }

    public static final class PhotoTable {
        public static final String TABLE_NAME = "Photo";

        public static final class Columns implements BaseColumns {

            public static final String NAME = "name";
            public static final String FILEPATH = "filePath";
            public static final String IDCHILD = "idChild";
            public static final String CREATIONDATE = "creationDate";


        }
    }


    public NinosOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //To change body of implemented methods use File | Settings | File Templates.

        StringBuilder sqlCreateChildTable = new StringBuilder();
        StringBuilder sqlCreatePhotoTable = new StringBuilder();

        sqlCreateChildTable.append("CREATE TABLE " + ChildTable.TABLE_NAME + " (" + ChildTable.Columns._ID + " INTEGER PRIMARY KEY,");
        sqlCreateChildTable.append(ChildTable.Columns.PHOTODEFAULTPATH + " TEXT,");
        sqlCreateChildTable.append(ChildTable.Columns.LATITUDE + " REAL,");
        sqlCreateChildTable.append(ChildTable.Columns.LONGITUDE + " REAL,");
        sqlCreateChildTable.append(ChildTable.Columns.NAME + " TEXT," + ChildTable.Columns.DESCRIPTION + " TEXT );");


        sqlCreatePhotoTable.append("CREATE TABLE " + PhotoTable.TABLE_NAME + " (" + PhotoTable.Columns._ID + " INTEGER PRIMARY KEY,");
        sqlCreatePhotoTable.append(PhotoTable.Columns.NAME + " TEXT," + PhotoTable.Columns.FILEPATH + " TEXT,");
        sqlCreatePhotoTable.append(PhotoTable.Columns.CREATIONDATE + " REAL,");
        sqlCreatePhotoTable.append(PhotoTable.Columns.IDCHILD + " INTEGER, FOREIGN KEY(" + PhotoTable.Columns.IDCHILD + ") REFERENCES " + ChildTable.TABLE_NAME + "(" + ChildTable.Columns._ID + "));");

        sqLiteDatabase.execSQL(sqlCreateChildTable.toString());
        sqLiteDatabase.execSQL(sqlCreatePhotoTable.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
