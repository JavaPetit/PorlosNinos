package pe.javapetit.apps.porlosNinos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import pe.javapetit.apps.porlosNinos.gui.BaseActivity;
import pe.javapetit.apps.porlosNinos.gui.NewChildActivity;
import pe.javapetit.apps.porlosNinos.gui.adapter.DashboardItemAdapter;
import pe.javapetit.apps.porlosNinos.model.dao.ChildDao;
import pe.javapetit.apps.porlosNinos.model.entity.Child;

import java.util.List;

public class MainActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */

    public MainActivity() {
        super(false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SettingControl();
    }

    private void SettingControl() {


    }

    private void loadData() {

        ChildDao childDao = new ChildDao(getDbHelper());
        List<Child> children = childDao.getAllChildren();

        DashboardItemAdapter dashboardItemAdapter = new DashboardItemAdapter(this);
        dashboardItemAdapter.getChildren().addAll(children);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(dashboardItemAdapter);
        listView.setOnItemClickListener(onItemClickListener);

    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //To change body of implemented methods use File | Settings | File Templates.

            Intent intent = new Intent(MainActivity.this, NewChildActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", (int) l);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.

        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, 100, Menu.FIRST, "Agregar").setIcon(R.drawable.menu_baby).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case 100:

                Intent intent = new Intent(this, NewChildActivity.class);
                startActivity(intent);

                return true;

        }

        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
