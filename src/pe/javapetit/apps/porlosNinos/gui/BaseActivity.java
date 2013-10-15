package pe.javapetit.apps.porlosNinos.gui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import pe.javapetit.apps.porlosNinos.R;
import pe.javapetit.apps.porlosNinos.model.provider.NinosOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JavaPetit
 * Date: 05/10/13
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseActivity extends SlidingFragmentActivity {

    private boolean isBack = false;
    private NinosOpenHelper dbHelper = null;
    private DisplayImageOptions optionsImageDownload;

    public BaseActivity(boolean isBack) {
        this.isBack = isBack;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setBehindContentView(R.layout.left_menu);
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        ListView listView = (ListView) sm.getMenu().findViewById(R.id.leftMenu);

        List<String> menuData = new ArrayList<String>();
        menuData.add("Inicio");
        menuData.add("Ayuda");

        ArrayAdapter<String> listData = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuData);
        listView.setAdapter(listData);

        getSupportActionBar().setIcon(R.drawable.ic_launcher);

        if (isBack) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {

            getSupportActionBar().setHomeButtonEnabled(true);
        }


        setSlidingActionBarEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
        if (dbHelper != null) {

            dbHelper.close();
            dbHelper = null;
        }


    }


    public NinosOpenHelper getDbHelper() {
        if (dbHelper == null) {
            dbHelper = new NinosOpenHelper(this);

        }
        return dbHelper;
    }

    public DisplayImageOptions getOptionsImageDownload() {
        if (optionsImageDownload == null) {
            optionsImageDownload = new DisplayImageOptions.Builder()
                    .showStubImage(R.drawable.default_image_icon)
                    .showImageForEmptyUri(R.drawable.default_image_icon)
                    .cacheInMemory(true).cacheOnDisc(true).build();
        }
        return optionsImageDownload;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                if (isBack) {
                    finish();
                } else {
                    toggle();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
