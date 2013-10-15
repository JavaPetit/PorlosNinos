package pe.javapetit.apps.porlosNinos.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import pe.javapetit.apps.porlosNinos.R;

/**
 * Created with IntelliJ IDEA.
 * User: JavaPetit
 * Date: 15/10/13
 * Time: 12:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChildMapActivity extends SherlockFragmentActivity {

    Marker mark = null;
    private GoogleMap map;
    private double latitude;
    private double longitude;
    private boolean isCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.child_map);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get a handle to the Map Fragment


        map = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        Bundle bundle = getIntent().getExtras();
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");

        if (latitude != 0 || longitude != 0) {
            LatLng location = new LatLng(latitude, longitude);


            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));

            map.addMarker(new MarkerOptions()
                    .title("el niño")
                    .snippet("Ayudar.")
                    .position(location));
        }


        map.setOnMapLongClickListener(mapLongClickListener);

    }

    private GoogleMap.OnMapLongClickListener mapLongClickListener = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            //To change body of implemented methods use File | Settings | File Templates.
            if (mark != null) {
                mark.remove();
            }

            latitude = latLng.latitude;
            longitude = latLng.longitude;
            mark = map.addMarker(new MarkerOptions()
                    .title("el niño")
                    .snippet("Ayudar.")
                    .position(latLng));
            isCheck = true;

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, 102, Menu.FIRST + 2, "Grabar").setIcon(R.drawable.menu_save).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case 102:
                saveData();
                return true;
        }

        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void saveData() {
        if (isCheck) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putDouble("latitude", latitude);
            bundle.putDouble("longitude", longitude);
            intent.putExtras(bundle);
            setResult(1000, intent);
        }
        finish();
    }
}
