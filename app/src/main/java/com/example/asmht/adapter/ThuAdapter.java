package com.example.asmht.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.asmht.fragment.KhoanThuFragment;
import com.example.asmht.fragment.LoaiThuFragment;

public class ThuAdapter extends FragmentStateAdapter {
    public ThuAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new KhoanThuFragment();
        return new LoaiThuFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
