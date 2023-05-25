package com.example.tasktunesapp.Panel;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tasktunesapp.Adapter.StateFragmentAdapter;
import com.example.tasktunesapp.Fragments.HomeFragment;
import com.example.tasktunesapp.Fragments.MusicFragment;
import com.example.tasktunesapp.R;
import com.realgear.multislidinguppanel.BasePanelView;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.readable_bottom_bar.ReadableBottomBar;

public class RootNavigationBarPanel extends BasePanelView {

    private ViewPager2 rootViewPager;
    private ReadableBottomBar rootNavigationBar;

    public RootNavigationBarPanel(@NonNull Context context, MultiSlidingUpPanelLayout panelLayout) {
        super(context, panelLayout);

        getContext().setTheme(R.style.Theme_App);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_root_navigation_bar, this, true);
    }

    @Override
    public void onCreateView() {
        this.setPanelState(MultiSlidingUpPanelLayout.COLLAPSED);
        this.setSlideDirection(MultiSlidingUpPanelLayout.SLIDE_VERTICAL);

        this.setPeakHeight(getResources().getDimensionPixelSize(R.dimen.navigation_bar_height));

    }

    @Override
    public void onBindView() {
        rootViewPager = getMultiSlidingUpPanel().findViewById(R.id.root_view_pager);
        rootNavigationBar = findViewById(R.id.root_navigation_bar);

        StateFragmentAdapter adapter = new StateFragmentAdapter(getSupportFragmentManager(), getLifecycle());

        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new MusicFragment());
//        adapter.addFragment(new SettingsFragment);

        rootViewPager.setAdapter(adapter);
        rootNavigationBar.setupWithViewPager2(rootViewPager);
    }

    @Override
    public void onPanelStateChanged(int panelSate) {

    }
}
