package pe.javapetit.apps.porlosNinos.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import pe.javapetit.apps.porlosNinos.model.entity.Child;
import pe.javapetit.apps.porlosNinos.model.entity.Photo;
import pe.javapetit.apps.porlosNinos.model.provider.NinosOpenHelper;
import pe.javapetit.apps.porlosNinos.model.provider.NinosOpenHelper.PhotoTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JavaPetit
 * Date: 06/10/13
 * Time: 11:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhotoDao {

    private SQLiteOpenHelper dbHelper = null;

    public PhotoDao(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void createPhoto(Photo photo) {

        ContentValues values = new ContentValues();
        values.put(PhotoTable.Columns.NAME, photo.getName());
        values.put(PhotoTable.Columns.FILEPATH, photo.getFilePath());
        if (photo.getCreationDate() != null) {
            values.put(PhotoTable.Columns.CREATIONDATE, photo.getCreationDate().getTime());

        }

        if (photo.getChild() != null) {
            values.put(PhotoTable.Columns.IDCHILD, photo.getChild().getIdChild());
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int id = (int) db.insert(PhotoTable.TABLE_NAME, null, values);
        photo.setIdPhoto(id);

        db.close();

    }

    public void updatePhoto(Photo photo) {

        ContentValues values = new ContentValues();
        values.put(PhotoTable.Columns.NAME, photo.getName());
        values.put(PhotoTable.Columns.FILEPATH, photo.getFilePath());
        if (photo.getChild() != null) {
            values.put(PhotoTable.Columns.IDCHILD, photo.getChild().getIdChild());
        }

        if (photo.getCreationDate() != null) {
            values.put(PhotoTable.Columns.CREATIONDATE, photo.getCreationDate().getTime());

        } else {
            values.putNull(PhotoTable.Columns.CREATIONDATE);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.update(PhotoTable.TABLE_NAME, values, PhotoTable.Columns._ID + " = ?", new String[]{String.valueOf(photo.getIdPhoto())});

        db.close();
    }

    public List<Photo> getAllPhotosInChild(int id) {
        List<Photo> output = new ArrayList<Photo>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(PhotoTable.TABLE_NAME,
                new String[]{PhotoTable.Columns._ID, PhotoTable.Columns.NAME, PhotoTable.Columns.FILEPATH, PhotoTable.Columns.IDCHILD},
                PhotoTable.Columns.IDCHILD + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        while (cursor.moveToNext()) {
            output.add(getObjectFromCursor(cursor));

        }

        if (!cursor.isClosed()) {
            cursor.close();

        }
        db.close();
        return output;
    }


    public Photo getPhotoById(int id) {
        Photo output = new Photo();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(PhotoTable.TABLE_NAME,
                new String[]{PhotoTable.Columns._ID, PhotoTable.Columns.NAME, PhotoTable.Columns.FILEPATH, PhotoTable.Columns.IDCHILD},
                PhotoTable.Columns._ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        while (cursor.moveToNext()) {
            output = getObjectFromCursor(cursor);

        }

        if (!cursor.isClosed()) {
            cursor.close();

        }
        db.close();
        return output;

    }

    public void deletePhotoById(int id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(PhotoTable.TABLE_NAME, PhotoTable.Columns._ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAllPhotoByChildId(int id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(PhotoTable.TABLE_NAME, PhotoTable.Columns.IDCHILD + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    private Photo getObjectFromCursor(Cursor cursor) {
        Photo output = new Photo();

        if (!cursor.isNull(cursor.getColumnIndex(PhotoTable.Columns._ID))) {
            output.setIdPhoto(cursor.getInt(cursor.getColumnIndex(PhotoTable.Columns._ID)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(PhotoTable.Columns.NAME))) {
            output.setName(cursor.getString(cursor.getColumnIndex(PhotoTable.Columns.NAME)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(PhotoTable.Columns.FILEPATH))) {
            output.setFilePath(cursor.getString(cursor.getColumnIndex(PhotoTable.Columns.FILEPATH)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(PhotoTable.Columns.IDCHILD))) {
            output.setChild(new Child());
            output.getChild().setIdChild(cursor.getInt(cursor.getColumnIndex(PhotoTable.Columns.IDCHILD)));
        }

        if (!cursor.isNull(cursor.getColumnIndex(PhotoTable.Columns.CREATIONDATE))) {
            Date date = new Date();
            date.setTime(cursor.getLong(cursor.getColumnIndex(PhotoTable.Columns.CREATIONDATE)));
            output.setCreationDate(date);
        }


        return output;
    }
}
