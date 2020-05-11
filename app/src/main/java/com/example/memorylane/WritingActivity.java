package com.example.memorylane;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.memorylane.TabFragments.AfternoonFragment1;
import com.example.memorylane.TabFragments.AfternoonFragment2;
import com.example.memorylane.TabFragments.EveningFragment1;
import com.example.memorylane.TabFragments.EveningFragment2;
import com.example.memorylane.TabFragments.MorningFragment1;
import com.example.memorylane.TabFragments.MorningFragment2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WritingActivity extends AppCompatActivity {

    ViewPager viewPager1;
    ViewPager viewPager2;
    ViewPager viewPager3;

    PagerAdapter pagerAdapter1;
    PagerAdapter pagerAdapter2;
    PagerAdapter pagerAdapter3;
    MorningFragment1 morningFragment1;
    MorningFragment2 morningFragment2;
    AfternoonFragment1 afternoonFragment1;
    AfternoonFragment2 afternoonFragment2;
    EveningFragment1 eveningFragment1;
    EveningFragment2 eveningFragment2;

    static DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        setupToolbar();
        startFragment();
        myDb = new DatabaseHelper(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void revertViewPagers(int base) {
        setDataFromDB();
        switch(base) {
            case 1:
                if(viewPager2.getCurrentItem() != 0)
                    viewPager2.setCurrentItem(0, true);
                if(viewPager3.getCurrentItem() != 0)
                    viewPager3.setCurrentItem(0 ,true);
                break;
            case 2:
                if(viewPager1.getCurrentItem() != 0)
                    viewPager1.setCurrentItem(0, true);
                if(viewPager3.getCurrentItem() != 0)
                    viewPager3.setCurrentItem(0, true);
                break;
            case 3:
                if(viewPager2.getCurrentItem() != 0)
                    viewPager2.setCurrentItem(0, true);
                if(viewPager1.getCurrentItem() != 0)
                    viewPager1.setCurrentItem(0, true);
                break;
            default:
                break;
        }
    }

    private void setDataFromDB(){
        Cursor res = myDb.getDataById(getDate());
        while (res.moveToNext()) {
            MorningFragment2.editText.setText(res.getString(4),
                    TextView.BufferType.EDITABLE);
            setSpinnerText(MorningFragment2.spinner, res.getString(5));
            AfternoonFragment2.editText.setText(res.getString(6),
                    TextView.BufferType.EDITABLE);
            setSpinnerText(AfternoonFragment2.spinner, res.getString(7));
            EveningFragment2.editText.setText(res.getString(8),
                    TextView.BufferType.EDITABLE);
            setSpinnerText(EveningFragment2.spinner, res.getString(9));
        }
        res.close();
    }

    private void setSpinnerText(Spinner spin, String text)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
            if(spin.getAdapter().getItem(i).toString().contains(text))
                spin.setSelection(i);
    }

    private void setupToolbar(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_writing);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void startFragment(){
        viewPager1 = findViewById(R.id.writing_pager1);
        viewPager2 = findViewById(R.id.writing_pager2);
        viewPager3 = findViewById(R.id.writing_pager3);

        morningFragment1 = new MorningFragment1();
        morningFragment2 = new MorningFragment2();
        afternoonFragment1 = new AfternoonFragment1();
        afternoonFragment2 = new AfternoonFragment2();
        eveningFragment1 = new EveningFragment1();
        eveningFragment2 = new EveningFragment2();

        pagerAdapter1 = new PagerAdapter(getSupportFragmentManager(), morningFragment1, morningFragment2);
        viewPager1.setAdapter(pagerAdapter1);
        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                if(position == 1)
                    revertViewPagers(1);
            }
        });

        pagerAdapter2 = new PagerAdapter(getSupportFragmentManager(), afternoonFragment1, afternoonFragment2);
        viewPager2.setAdapter(pagerAdapter2);
        viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                if(position == 1)
                    revertViewPagers(2);
            }
        });

        pagerAdapter3 = new PagerAdapter(getSupportFragmentManager(), eveningFragment1, eveningFragment2);
        viewPager3.setAdapter(pagerAdapter3);
        viewPager3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                if(position == 1)
                    revertViewPagers(3);
            }
        });
    }

    static class PagerAdapter extends FragmentStatePagerAdapter {

        String[] fragmentNames = {"first","second"};
        Fragment first;
        Fragment second;

        public PagerAdapter(@NonNull FragmentManager fm, Fragment first, Fragment second) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.first = first;
            this.second = second;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return first;
                case 1:
                    return second;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragmentNames.length;
        }
    }

    private String getDate() {
        String pattern = "dd MMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }

    public void submitData(View view) {
        boolean isInserted ;
        String date = getDate();
        String[] date_parts = date.split(" ");
        if(!myDb.isDayRegistered(date))
            isInserted = myDb.insertData(date,date_parts[0],date_parts[1],date_parts[2],
                    MorningFragment2.editText.getText().toString(),
                    MorningFragment2.spinner.getSelectedItem().toString(),
                    AfternoonFragment2.editText.getText().toString(),
                    AfternoonFragment2.spinner.getSelectedItem().toString(),
                    EveningFragment2.editText.getText().toString(),
                    EveningFragment2.spinner.getSelectedItem().toString());
        else
            isInserted = myDb.updateData(date,date_parts[0],date_parts[1],date_parts[2],
                    MorningFragment2.editText.getText().toString(),
                    MorningFragment2.spinner.getSelectedItem().toString(),
                    AfternoonFragment2.editText.getText().toString(),
                    AfternoonFragment2.spinner.getSelectedItem().toString(),
                    EveningFragment2.editText.getText().toString(),
                    EveningFragment2.spinner.getSelectedItem().toString());

        viewPager1.setCurrentItem(0 ,true);
        viewPager2.setCurrentItem(0 ,true);
        viewPager3.setCurrentItem(0 ,true);
        if(isInserted)
            Toast.makeText(this,"Updated your data",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Data could not be updated",Toast.LENGTH_SHORT).show();
    }

}
