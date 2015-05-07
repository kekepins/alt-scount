package org.ambit.altscount.altscountandroid;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.ambit.altscount.altscountandroid.org.ambit.usb.android.AmbitManager;
import org.ambit.altscount.altscountandroid.org.ambit.usb.android.DeviceFactory;
import org.ambit.data.AmbitInfo;
import org.ambit.data.LogInfo;
import org.ambit.usb.UsbException;

import java.util.ArrayList;
import java.util.List;


public class AltScount extends ActionBarActivity {

    private AmbitManager ambitManager;
    private UsbManager usbManager;

    private List<String> moves = new ArrayList<String>();

    private ListView listViewMoves;

    private  ArrayAdapter<String> movesAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alt_scount);

        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        // Init
        this.ambitManager = new AmbitManager(usbManager, this.getApplicationContext());

        /// The checkbox for the each item is specified by the layout android.R.layout.simple_list_item_multiple_choice
        movesAdpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, moves);

        // Getting the reference to the listview object of the layout
        listViewMoves = (ListView) findViewById(R.id.lvMoves);

        // Setting adapter to the listview
        listViewMoves.setAdapter(movesAdpater);

        Button button = (Button) findViewById(R.id.searchBtn);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doSearch();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alt_scount, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void doSearch() {
        //moves.add("move3");
        //moves.add("move4");

        //listViewMoves.deferNotifyDataSetChanged();
        movesAdpater.notifyDataSetChanged();


        try {
            ambitManager.connectDevice();


            Log.i("AUB","Before charge" );
            //ambitManager.getSettings2();

            //ambitManager.getDeviceInfo();
            //ambitManager.getSettings();

            short charge = ambitManager.getDeviceCharge();
            Log.i("AUB","Charge " + charge);

            /*AmbitInfo ambitInfo = ambitManager.getDeviceInfo();
            if ( ambitInfo != null ) {
                Log.i("AUB","ambitInfo Model " + ambitInfo.getModel());
            }
            else {
                Log.i("AUB","ambitInfo NULL ");
            }*/

            List<LogInfo> logInfos = ambitManager.readMoveDescriptions();

            for ( LogInfo logInfo : logInfos ) {
                Log.i("AUB","logInfo " + logInfo.getCompleteName() );
                moves.add(logInfo.getCompleteName() + " " + logInfo.getDistance() + "km");
            }

        } catch (UsbException e) {
            Log.e("AUB", "Error " + e);
        }

    }
}
