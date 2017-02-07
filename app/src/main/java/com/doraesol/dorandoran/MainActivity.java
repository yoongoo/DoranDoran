package com.doraesol.dorandoran;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Window;
import android.view.WindowManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vp_main_pager)   ViewPager viewPager;
    @BindView(R.id.tl_main_tabs)    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int[] tabIcons = {
                R.drawable.ic_list_home,
                R.drawable.ic_list_genogram,
                R.drawable.ic_list_map,
                R.drawable.ic_list_social,
                R.drawable.ic_list_tool4
        };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Fragment[] arrFragments = new Fragment[5];
        arrFragments[0] = new HomeFragment();
        arrFragments[1] = new FamilyTreeFragment();
        arrFragments[2] = new MapMainFragment();
        arrFragments[3] = new CmtBoardFragment();
        arrFragments[4] = new SettingFragment();

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), arrFragments);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for(int i=0; i<tabLayout.getTabCount(); i++)
        {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }


    class MainPagerAdapter extends FragmentPagerAdapter {

        Fragment[] arrFragments;

        public MainPagerAdapter(FragmentManager fm, Fragment[] arrFragments) {
            super(fm);
            this.arrFragments = arrFragments;


        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "HOME";
                case 1:
                    return "TREEMAP";
                case 2:
                    return "MAP";
                case 3:
                    return "SOCIAL";
                case 4:
                    return "SETTING";
                default:
                    return "";
            }
        }

        @Override
        public Fragment getItem(int position) {
            return arrFragments[position];
        }

        @Override
        public int getCount() {
            return arrFragments.length;
        }
    }
}
