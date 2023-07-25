package com.example.asmht.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.asmht.R;
import com.example.asmht.adapter.LoaiChiAdapter;
import com.example.asmht.dao.KhoanThuChiDAO;
import com.example.asmht.model.Loai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LoaiChiFragment extends Fragment {

    ListView listViewLoaiChi;
    FloatingActionButton floadAdd;
    LoaiChiAdapter adapter;
    ArrayList<Loai> list;
    KhoanThuChiDAO khoanThuChiDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loaichi_fragment, container, false);
        listViewLoaiChi = view.findViewById(R.id.lv_LoaiChi);
        floadAdd = view.findViewById(R.id.floatAddLC);

        khoanThuChiDAO = new KhoanThuChiDAO(getContext());
        loadData();

        floadAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThem();
            }
        });

        return view;
    }
    private void loadData(){
        list = khoanThuChiDAO.getDSLoai("chi");

        adapter = new LoaiChiAdapter(list, getContext(), khoanThuChiDAO);
        listViewLoaiChi.setAdapter(adapter);
    }

    private void showDialogThem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themloaichi, null);
        EditText edTenLC = view.findViewById(R.id.edTenLoaiChi);
        builder.setView(view);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenLoai = edTenLC.getText().toString();
                Loai loaiThem = new Loai(tenLoai, "chi");
                if(khoanThuChiDAO.insertLoai(loaiThem)){
                    Toast.makeText(getContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
