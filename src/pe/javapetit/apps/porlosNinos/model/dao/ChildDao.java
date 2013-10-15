package pe.javapetit.apps.porlosNinos.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import pe.javapetit.apps.porlosNinos.model.entity.Child;
import pe.javapetit.apps.porlosNinos.model.provider.NinosOpenHelper;
import pe.javapetit.apps.porlosNinos.model.provider.NinosOpenHelper.ChildTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JavaPetit
 * Date: 06/10/13
 * Time: 11:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChildDao {

    private SQLiteOpenHelper dbHelper = null;

    public ChildDao(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void createChild(Child child) {

        ContentValues values = new ContentValues();
        values.put(ChildTable.Columns.NAME, child.getName());
        values.put(ChildTable.Columns.DESCRIPTION, child.getDescription());
        values.put(ChildTable.Columns.PHOTODEFAULTPATH, child.getDefaultPhotoPath());
        values.put(ChildTable.Columns.LATITUDE, child.getLatitude());
        values.put(ChildTable.Columns.LONGITUDE, child.getLongitude());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int id = (int) db.insert(ChildTable.TABLE_NAME, null, values);
        child.setIdChild(id);

        db.close();

    }

    public void updateChild(Child child) {

        ContentValues values = new ContentValues();
        values.put(ChildTable.Columns.NAME, child.getName());
        values.put(ChildTable.Columns.DESCRIPTION, child.getDescription());
        values.put(ChildTable.Columns.PHOTODEFAULTPATH, child.getDefaultPhotoPath());
        values.put(ChildTable.Columns.LATITUDE, child.getLatitude());
        values.put(ChildTable.Columns.LONGITUDE, child.getLongitude());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.update(ChildTable.TABLE_NAME, values, ChildTable.Columns._ID + " = ?", new String[]{String.valueOf(child.getIdChild())});

        db.close();
    }


    public List<Child> getAllChildren() {
        List<Child> output = new ArrayList<Child>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ChildTable.TABLE_NAME, new String[]{ChildTable.Columns._ID, ChildTable.Columns.NAME, ChildTable.Columns.DESCRIPTION, ChildTable.Columns.PHOTODEFAULTPATH,
                ChildTable.Columns.LATITUDE, ChildTable.Columns.LONGITUDE
        }, null, null, null, null, null);

        while (cursor.moveToNext()) {
            output.add(getObjectFromCursor(cursor));

        }

        if (!cursor.isClosed()) {
            cursor.close();

        }
        db.close();
        return output;
    }


    public Child getChildById(int id) {
        Child output = new Child();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ChildTable.TABLE_NAME,
                new String[]{ChildTable.Columns._ID, ChildTable.Columns.NAME, ChildTable.Columns.DESCRIPTION, ChildTable.Columns.PHOTODEFAULTPATH, ChildTable.Columns.LATITUDE, ChildTable.Columns.LONGITUDE},
                ChildTable.Columns._ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        while (cursor.moveToNext()) {
            output = getObjectFromCursor(cursor);

        }

        if (!cursor.isClosed()) {
            cursor.close();

        }
        db.close();
        return output;

    }

    public void deleteChildById(int id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(ChildTable.TABLE_NAME, ChildTable.Columns._ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    private Child getObjectFromCursor(Cursor cursor) {
        Child output = new Child();

        if (!cursor.isNull(cursor.getColumnIndex(ChildTable.Columns._ID))) {
            output.setIdChild(cursor.getInt(cursor.getColumnIndex(ChildTable.Columns._ID)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(ChildTable.Columns.NAME))) {
            output.setName(cursor.getString(cursor.getColumnIndex(ChildTable.Columns.NAME)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(ChildTable.Columns.DESCRIPTION))) {
            output.setDescription(cursor.getString(cursor.getColumnIndex(ChildTable.Columns.DESCRIPTION)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(ChildTable.Columns.PHOTODEFAULTPATH))) {
            output.setDefaultPhotoPath(cursor.getString(cursor.getColumnIndex(ChildTable.Columns.PHOTODEFAULTPATH)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(ChildTable.Columns.LATITUDE))) {
            output.setLatitude(cursor.getDouble(cursor.getColumnIndex(ChildTable.Columns.LATITUDE)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(ChildTable.Columns.LONGITUDE))) {
            output.setLongitude(cursor.getDouble(cursor.getColumnIndex(ChildTable.Columns.LONGITUDE)));
        }


        return output;
    }
}
