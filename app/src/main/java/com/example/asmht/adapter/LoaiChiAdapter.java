package com.example.asmht.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.asmht.R;
import com.example.asmht.dao.KhoanThuChiDAO;
import com.example.asmht.model.Loai;

import java.util.ArrayList;

public class LoaiChiAdapter extends BaseAdapter {
    private ArrayList<Loai> list;
    private Context context;
    private KhoanThuChiDAO khoanThuChiDAO;

    public LoaiChiAdapter(ArrayList<Loai> list, Context context, KhoanThuChiDAO khoanThuChiDAO) {
        this.list = list;
        this.context = context;
        this.khoanThuChiDAO = khoanThuChiDAO;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewOfItem{
        TextView tvTen;
        ImageView ivEdit, ivDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        ViewOfItem viewOfItem;

        if (convertView == null){
            viewOfItem = new ViewOfItem();
            convertView = inflater.inflate(R.layout.item_loaichi, parent, false);
            viewOfItem.tvTen = convertView.findViewById(R.id.tv_tenLC);
            viewOfItem.ivEdit = convertView.findViewById(R.id.iv_editLC);
            viewOfItem.ivDelete = convertView.findViewById(R.id.iv_deleteLC);
            convertView.setTag(viewOfItem);
        }else {
            viewOfItem = (ViewOfItem) convertView.getTag();
        }
        viewOfItem.tvTen.setText(list.get(position).getTenLoai());

        viewOfItem.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSuaLoaiChi(list.get(position));
            }
        });

        viewOfItem.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idXoa = list.get(position).getMaLoai();
                if(khoanThuChiDAO.deleteLoai(idXoa)){
                    reloadData();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    public void showDialogSuaLoaiChi(Loai loai){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sualoaichi,null);
        EditText edLoaiChi = view.findViewById(R.id.edTenLoaiChi);
        builder.setView(view);

        //hiển thị LC trên ô sửa
        edLoaiChi.setText(loai.getTenLoai());

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenloai = edLoaiChi.getText().toString();
                loai.setTenLoai(tenloai);
                if(khoanThuChiDAO.updateLoai(loai)){
                    reloadData();
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void reloadData(){
        list.clear();
        list = khoanThuChiDAO.getDSLoai("chi");
        notifyDataSetChanged();
    }
}
