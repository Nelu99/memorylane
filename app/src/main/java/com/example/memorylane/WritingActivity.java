package com.example.memorylane;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.memorylane.TabFragments.AfternoonFragment1;
import com.example.memorylane.TabFragments.AfternoonFragment2;
import com.example.memorylane.TabFragments.EveningFragment1;
import com.example.memorylane.TabFragments.EveningFragment2;
import com.example.memorylane.TabFragments.MorningFragment1;
import com.example.memorylane.TabFragments.MorningFragment2;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        setupToolbar();
        startFragment();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void revertViewPagers(int base) {
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

}
