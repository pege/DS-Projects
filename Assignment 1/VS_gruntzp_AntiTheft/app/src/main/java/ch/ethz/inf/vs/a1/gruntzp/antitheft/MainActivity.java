package ch.ethz.inf.vs.a1.gruntzp.antitheft;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    public static NotificationManager mNotifyMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        refreshToggleButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context, menu);
        return true;
    }

    public boolean onSettingsClicked(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshToggleButton();
    }

    public void onClickToggle(View v){
        ToggleButton t = (ToggleButton) v;
        if (t.isChecked()) {

            Intent service = new Intent(this, AntiTheftService.class);
            startService(service);


        } else {
            stopService(new Intent(this, AntiTheftService.class));
        }
    }

    private void refreshToggleButton(){
        boolean running = AntiTheftService.isRunning();
        Log.d("is running?", Boolean.toString(running));
        ToggleButton t =((ToggleButton) this.findViewById(R.id.tbService));
        t.setChecked(running);
        //t.invalidate();
    }
}
