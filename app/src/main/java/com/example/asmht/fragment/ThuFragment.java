package com.example.asmht.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asmht.R;
import com.example.asmht.adapter.ThuAdapter;
import com.example.asmht.R;
import com.example.asmht.adapter.ThuAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ThuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thu_fragment, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayoutThu);
        ViewPager2 viewPager2Thu = view.findViewById(R.id.viewPager2Thu);

        ThuAdapter adapter = new ThuAdapter(getActivity());
        viewPager2Thu.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2Thu, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0)
                    tab.setText("Khoản thu");
                else
                    tab.setText("Loại thu");
            }
        }).attach();

        return view;
    }


}
