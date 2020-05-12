package com.example.memorylane;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.memorylane.TabFragments.BlankFragment;
import com.example.memorylane.TabFragments.BlankFragment2;
import com.example.memorylane.TabFragments.BlankFragment3;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String[] MONTHS =
            {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final String MORNING_TEXT = "I started the day feeling ";
    public static final String AFTERNOON_TEXT = "The events of this afternoon make me really ";
    public static final String EVENING_TEXT = "At the end of the day I'm ";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private BlankFragment blankFragment;
    private BlankFragment2 blankFragment2;
    private BlankFragment3 blankFragment3;

    static DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        startFragment();
        myDb = new DatabaseHelper(this);
    }

    private String getDate() {
        String pattern = "dd MMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }

    public void showDatePicker(View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                MainActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = Integer.valueOf(dayOfMonth).toString() + " " +
                                MONTHS[month] + " " + year;
                        showData(date);
                    }
                },
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void showTodaysData(View view) {
        showData(getDate());
    }

    public void showAllData(View view) { showData("all"); }

    private void showData(String id) {
        Cursor res;
        if (id.equals("all"))
            res = myDb.getAllData();
        else
            res = myDb.getDataById(id);
        if(res.getCount() == 0) {
            showMessage("Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("\nDate : " + res.getString(0) + "\n\n");
            if(!res.getString(4).equals(""))
                buffer.append("Morning\n" + MORNING_TEXT + res.getString(5) +
                        ". " + res.getString(4) + "\n\n");
            if(!res.getString(6).equals(""))
                buffer.append("Afternoon\n" + res.getString(6)
                        + " " + AFTERNOON_TEXT + res.getString(7) + ".\n\n");
            if(!res.getString(8).equals(""))
                buffer.append("Evening\n" + EVENING_TEXT + res.getString(9)
                        + ". " + res.getString(8)+"\n\n");
        }
        res.close();
        showMessage(buffer.toString());
    }

    public void showMessage(String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(Message);
        AlertDialog alert = builder.create();
        alert.show();
        alert.getWindow().getAttributes();
        TextView textViewMessage = (TextView) alert.findViewById(android.R.id.message);
        textViewMessage.setTextSize(20);
    }

    public void setupToolbar(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void openWritingActivity(View view) {
        Intent intent = new Intent(this, WritingActivity.class);
        startActivity(intent);
    }

    public void startFragment(){
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout_main);
        blankFragment = new BlankFragment();
        blankFragment2 = new BlankFragment2();
        blankFragment3 = new BlankFragment3();
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        String[] fragmentNames = {"first","second","third"};

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return blankFragment;
                case 1:
                    return blankFragment2;
                case 2:
                    return blankFragment3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragmentNames.length;
        }
    }

}