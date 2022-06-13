package ca.mcgill.ecse321.eventregistration;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private String error = null;
    private List<String> personNames = new ArrayList<>();
    private ArrayAdapter<String> personAdapter;
    private ArrayAdapter<String> eventAdapter;
    private List<String> eventNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Spinner personSpinner = (Spinner) findViewById(R.id.personspinner);
        Spinner eventSpinner = (Spinner) findViewById(R.id.eventspinner);

        personAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, personNames);
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personSpinner.setAdapter(personAdapter);

        eventAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventNames);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(eventAdapter);

        refreshErrorMessage();

    }

    private void refreshErrorMessage() {
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addPerson(View v) {
        error = "";

        final TextView tv = (TextView) findViewById(R.id.newperson_name);



    }
    public void addEvent(View v) {
        TextView tv = (TextView) findViewById(R.id.starttime);
        String text = tv.getText().toString();
        String comps[] = text.split(":");

        int startHours = Integer.parseInt(comps[0]);
        int startMinutes = Integer.parseInt(comps[1]);

        tv = (TextView) findViewById(R.id.endtime);
        text = tv.getText().toString();
        comps = text.split(":");

        int endHours = Integer.parseInt(comps[0]);
        int endMinutes = Integer.parseInt(comps[1]);

        tv = (TextView) findViewById(R.id.newevent_date);
        text = tv.getText().toString();
        comps = text.split("-");

        int year = Integer.parseInt(comps[2]);
        int month = Integer.parseInt(comps[1]);
        int day = Integer.parseInt(comps[0]);

        tv = (TextView) findViewById(R.id.newevent_name);
        String name = tv.getText().toString();

        RequestParams rp = new RequestParams();

        NumberFormat formatter = new DecimalFormat("00");
        rp.add("date", year + "-" + formatter.format(month) + "-" + formatter.format(day));
        rp.add("startTime", formatter.format(startHours) + ":" + formatter.format(startMinutes));
        rp.add("endTime", formatter.format(endHours) + ":" + formatter.format(endMinutes));

    }
}
