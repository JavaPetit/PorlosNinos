package pe.javapetit.apps.porlosNinos.gui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import pe.javapetit.apps.porlosNinos.BaseApplication;
import pe.javapetit.apps.porlosNinos.R;
import pe.javapetit.apps.porlosNinos.model.entity.Photo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JavaPetit
 * Date: 07/10/13
 * Time: 10:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhotoItemAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater = null;
    private List<Photo> photos;

    public List<Photo> getPhotos() {
        return photos;
    }

    public PhotoItemAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        photos = new ArrayList<Photo>();
    }

    @Override
    public int getCount() {
        return photos.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getItem(int i) {
        return photos.get(i);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getItemId(int i) {
        return photos.get(i).getIdPhoto();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_photo_new, viewGroup, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imgPhoto);
            TextView photoName = (TextView) view.findViewById(R.id.photoName);
            TextView photoDate = (TextView) view.findViewById(R.id.photoDate);

            Photo photo = (Photo) getItem(i);

            photoName.setText("Photo " + (i + 1));

            if (photo.getCreationDate() != null) {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy hh:mm a");
                photoDate.setText(format.format(photo.getCreationDate()));
            }
            File file = new File(BaseApplication.getApplicationResourceRoot() + "/Files/thumb" + photo.getFilePath());
            if (file.exists()) {
                Bitmap myPhoto = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageView.setImageBitmap(myPhoto);
                myPhoto=null;
            }
        }

        return view;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
