package pe.javapetit.apps.porlosNinos.gui;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import pe.javapetit.apps.porlosNinos.BaseApplication;
import pe.javapetit.apps.porlosNinos.R;
import pe.javapetit.apps.porlosNinos.gui.adapter.PhotoItemAdapter;
import pe.javapetit.apps.porlosNinos.model.dao.ChildDao;
import pe.javapetit.apps.porlosNinos.model.dao.PhotoDao;
import pe.javapetit.apps.porlosNinos.model.entity.Child;
import pe.javapetit.apps.porlosNinos.model.entity.Photo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JavaPetit
 * Date: 06/10/13
 * Time: 05:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class NewChildActivity extends BaseActivity {

    private String myCameraPath = null;
    private int idChild;


    private EditText tbName = null;
    private EditText tbDescription = null;
    private ListView listView = null;
    private PhotoItemAdapter photoAdapter = null;
    private double latitude;
    private double longitude;


    public NewChildActivity() {
        super(true);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        getSherlock().setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        setContentView(R.layout.new_child);
        settingControls();
        loadData();
    }

    private void settingControls() {
        tbName = (EditText) findViewById(R.id.tbName);
        tbDescription = (EditText) findViewById(R.id.tbDescription);
        listView = (ListView) findViewById(R.id.listView);
        photoAdapter = new PhotoItemAdapter(this);
        listView.setAdapter(photoAdapter);
        listView.setOnItemLongClickListener(onItemLongClickListener);
    }

    private void loadData() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            idChild = bundle.getInt("id");
            ChildDao childDao = new ChildDao(getDbHelper());
            PhotoDao photoDao = new PhotoDao(getDbHelper());
            Child child = childDao.getChildById(idChild);

            if (child != null) {
                tbName.setText(child.getName());
                tbDescription.setText(child.getDescription());
                latitude = child.getLatitude();
                longitude = child.getLongitude();
                photoAdapter.getPhotos().addAll(photoDao.getAllPhotosInChild(child.getIdChild()));
                photoAdapter.notifyDataSetChanged();
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        menu.add(Menu.NONE, 100, Menu.FIRST, "Foto").setIcon(R.drawable.menu_camara).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, 101, Menu.FIRST + 1, "Gps").setIcon(R.drawable.menu_gps).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, 102, Menu.FIRST + 2, "Grabar").setIcon(R.drawable.menu_save).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, 103, Menu.FIRST + 3, "Enviar por correo").setIcon(R.drawable.menu_email).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case 100:
                makeImageFromCamera();
                return true;
            case 101:
                showMap();
                return true;
            case 102:
                saveData();
                return true;
            case 103:
                sendEmail();
                return true;
        }

        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void sendEmail() {

        String fileName = null;
        if (photoAdapter.getPhotos().size() > 0) {
            fileName = photoAdapter.getPhotos().get(0).getFilePath();
            File mediaStorageDir = new File(BaseApplication.getApplicationResourceRoot()
                    + "/Files");
            fileName = mediaStorageDir.getPath() + File.separator + fileName;
        }


        String content = "Ayudar al niño" + "\n";
        content += tbName
                .getText().toString();

        if (latitude != 0 || longitude != 0) {
            content += "\n";
            content += "https://maps.google.com/?q=" + latitude + "," + longitude;

        }


        Intent gmailIntent = new Intent();
        gmailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        gmailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ayudar al niño");
        gmailIntent.putExtra(Intent.EXTRA_TEXT, content);

        if (fileName != null) {
            File mediaFile = new File(fileName);
            gmailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mediaFile));
            fileName = null;
        }
        try {
            startActivity(gmailIntent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, "No tiene gmail instalado", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMap() {
        Intent intent = new Intent(this, ChildMapActivity.class);
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1000, null);
    }

    private void saveData() {
        ChildDao childDao = new ChildDao(getDbHelper());
        PhotoDao photoDao = new PhotoDao(getDbHelper());
        Child child = childDao.getChildById(idChild);
        if (child == null) {
            child = new Child();
        }

        child.setName(tbName.getText().toString());
        child.setDescription(tbDescription.getText().toString());
        child.setLatitude(latitude);
        child.setLongitude(longitude);

        if (photoAdapter.getPhotos().size() > 0) {
            child.setDefaultPhotoPath(photoAdapter.getPhotos().get(0).getFilePath());
            Log.d("Photo", "tiene " + child.getDefaultPhotoPath());
        } else {
            child.setDefaultPhotoPath(null);
            Log.d("Photo", "no tiene");
        }

        if (child.getIdChild() == 0) {
            childDao.createChild(child);
        } else {
            childDao.updateChild(child);
        }

        idChild = child.getIdChild();

        photoDao.deleteAllPhotoByChildId(child.getIdChild());

        for (Photo photo : photoAdapter.getPhotos()) {
            photo.setChild(child);
            photoDao.createPhoto(photo);
        }


    }

    private void makeImageFromCamera() {


        if (Environment.getExternalStorageState().equals("mounted")) {
            File file = getOutputPhotoFile();
            myCameraPath = file.getName();
            Uri uriSavedImage = Uri.fromFile(file);

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

            startActivityForResult(cameraIntent, 1);

        }


    }


    private File getOutputPhotoFile() {
        File mediaStorageDir = new File(BaseApplication.getApplicationResourceRoot()
                + "/Files");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            addPhotoToContainer();

            File mediaStorageDir = new File(BaseApplication.getApplicationResourceRoot()
                    + "/Files");


            Bitmap myPhoto = BitmapFactory.decodeFile(mediaStorageDir.getPath() + File.separator + myCameraPath);
            myPhoto = Bitmap.createScaledBitmap(myPhoto, 48, 48, false);

            try {
                FileOutputStream outputStream = new FileOutputStream(mediaStorageDir.getPath() + File.separator + "thumb" + myCameraPath);
                myPhoto.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
                myPhoto = null;
                outputStream = null;

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            myCameraPath = null;
        }

        if (requestCode == 1000 && resultCode == 1000) {

            Bundle bundle = data.getExtras();
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
        }


    }

    private void addPhotoToContainer() {

        Photo photo = new Photo();
        photo.setFilePath(myCameraPath);
        photo.setCreationDate(new Date());
        photoAdapter.getPhotos().add(photo);
        photoAdapter.notifyDataSetChanged();
    }

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            AlertDialog.Builder builder = new AlertDialog.Builder(NewChildActivity.this);
            builder.setTitle("Opciones");
            final int position = i;

            builder.setItems(new String[]{"Eliminar"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //To change body of implemented methods use File | Settings | File Templates.
                    photoAdapter.getPhotos().remove(i);
                    photoAdapter.notifyDataSetChanged();
                }
            });

            builder.show();

            return true;  //To change body of implemented methods use File | Settings | File Templates.
        }
    };


}
