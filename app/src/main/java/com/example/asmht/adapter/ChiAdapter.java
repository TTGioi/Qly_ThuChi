package com.example.asmht.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.asmht.fragment.KhoanChiFragment;
import com.example.asmht.fragment.LoaiChiFragment;

public class ChiAdapter extends FragmentStateAdapter {
    public ChiAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new KhoanChiFragment();
        return new LoaiChiFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
