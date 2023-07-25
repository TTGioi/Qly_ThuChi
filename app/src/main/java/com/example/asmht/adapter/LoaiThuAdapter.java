package com.example.asmht.adapter;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.example.asmht.R;
import com.example.asmht.dao.KhoanThuChiDAO;
import com.example.asmht.model.Loai;

import java.util.ArrayList;

public class LoaiThuAdapter extends BaseAdapter {
    private ArrayList<Loai> list;
    private Context context;
    private KhoanThuChiDAO khoanThuChiDAO;

    public LoaiThuAdapter(ArrayList<Loai> list, Context context, KhoanThuChiDAO khoanThuChiDAO) {
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
            convertView = inflater.inflate(R.layout.item_loaithu, parent, false);
            viewOfItem.tvTen = convertView.findViewById(R.id.tvTen);
            viewOfItem.ivEdit = convertView.findViewById(R.id.iv_edit);
            viewOfItem.ivDelete = convertView.findViewById(R.id.iv_delete);
            convertView.setTag(viewOfItem);
        }else {
            viewOfItem = (ViewOfItem) convertView.getTag();
        }
        viewOfItem.tvTen.setText(list.get(position).getTenLoai());

        //sualoai
        viewOfItem.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSuaLoaiThu(list.get(position));
            }
        });

        //xoaloai
        viewOfItem.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idXoa = list.get(position).getMaLoai();
                if(khoanThuChiDAO.deleteLoai(idXoa)){
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    reloadData();
                }else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return convertView;
    }
    private void showDialogSuaLoaiThu(Loai loai){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sualoaithu,null);
        EditText edLoaiThu = view.findViewById(R.id.edTenLoaiThu);
        builder.setView(view);

        //hiển thị LT trên ô sửa
        edLoaiThu.setText(loai.getTenLoai());

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenloai = edLoaiThu.getText().toString();
                loai.setTenLoai(tenloai);
                if(khoanThuChiDAO.updateLoai(loai)){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    reloadData();
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
        list = khoanThuChiDAO.getDSLoai("thu");
        notifyDataSetChanged();
    }

}
