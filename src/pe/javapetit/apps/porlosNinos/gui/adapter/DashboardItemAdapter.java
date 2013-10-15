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
import pe.javapetit.apps.porlosNinos.model.entity.Child;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JavaPetit
 * Date: 08/10/13
 * Time: 08:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class DashboardItemAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater = null;
    private List<Child> children = new ArrayList<Child>();

    public DashboardItemAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        children = new ArrayList<Child>();
    }

    public List<Child> getChildren() {
        return children;
    }

    @Override
    public int getCount() {
        return children.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getItem(int i) {
        return children.get(i);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getItemId(int i) {
        return children.get(i).getIdChild();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_dashboard, viewGroup, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imgPhoto);
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);

            Child child = (Child) getItem(i);

            tvName.setText(child.getName());
            tvDescription.setText(child.getDescription());

            if (child.getDefaultPhotoPath() != null) {
                File file = new File(BaseApplication.getApplicationResourceRoot() + "/Files/thumb" + child.getDefaultPhotoPath());
                if (file.exists()) {
                    Bitmap myPhoto = BitmapFactory.decodeFile(file.getAbsolutePath());
                    imageView.setImageBitmap(myPhoto);
                    myPhoto=null;
                }
            }
        }

        return view;
    }
}
