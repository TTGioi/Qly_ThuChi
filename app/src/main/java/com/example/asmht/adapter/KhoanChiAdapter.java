package com.example.asmht.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.asmht.MainActivity;
import com.example.asmht.R;
import com.example.asmht.dao.KhoanThuChiDAO;
import com.example.asmht.fragment.KhoanChiFragment;
import com.example.asmht.model.KhoanThuChi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class KhoanChiAdapter extends BaseAdapter {

    private ArrayList<KhoanThuChi> list;
    private Context context;
    private KhoanThuChiDAO thuChiDAO;
    private ArrayList<HashMap<String, Object>> listSpinner;

    public KhoanChiAdapter(ArrayList<KhoanThuChi> list, Context context, KhoanThuChiDAO thuChiDAO, ArrayList<HashMap<String, Object>> listSpinner) {
        this.list = list;
        this.context = context;
        this.thuChiDAO = thuChiDAO;
        this.listSpinner = listSpinner;
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

    public static class ViewOfitem{
        TextView tvTen, tvTien;
        ImageView ivDelete, ivEdit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        ViewOfitem viewOfitem;

        if (convertView == null){
            convertView= inflater.inflate(R.layout.item_khoanchi, parent, false);
            viewOfitem = new ViewOfitem();
            viewOfitem.tvTen = convertView.findViewById(R.id.tv_tenKC);
            viewOfitem.tvTien = convertView.findViewById(R.id.tv_tienKC);
            viewOfitem.ivEdit = convertView.findViewById(R.id.iv_editKC);
            viewOfitem.ivDelete = convertView.findViewById(R.id.iv_deleteKC);
            convertView.setTag(viewOfitem);
        }else {
            viewOfitem = (ViewOfitem) convertView.getTag();
        }

        viewOfitem.tvTen.setText(list.get(position).getTenKhoan());
        viewOfitem.tvTien.setText(String.valueOf(list.get(position).getTien()));

        viewOfitem.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSua(list.get(position));
            }
        });

        viewOfitem.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int makhoan = list.get(position).getMaKhoan();
                if (thuChiDAO.deleteKhoanThuChi(makhoan)){
                    reloadData();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    private void showDialogSua(KhoanThuChi khoanThuChi){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialod_suakhoanchi, null);
        Spinner spnLoaiChi = view.findViewById(R.id.spnLoaiChi);
        EditText edTien = view.findViewById(R.id.edTienKC);
        EditText edtenKhoanChi = view.findViewById(R.id.edTenKhoanChi);
        EditText dateKC = view.findViewById(R.id.edNgayChi);
        ImageView ivChi = view.findViewById(R.id.ivChi);
        builder.setView(view);

        SimpleAdapter adapter = new SimpleAdapter(
                context,
                listSpinner,
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiChi.setAdapter(adapter);

        edTien.setText(String.valueOf(khoanThuChi.getTien()));
        edtenKhoanChi.setText(khoanThuChi.getTenKhoan());
        dateKC.setText(khoanThuChi.getNgay());

        int index = 0;
        int vitri = -1;
        for (HashMap<String, Object> item : listSpinner){
            if ((int)item.get("maLoai") == khoanThuChi.getMaLoai())
                vitri = index;

            index++;
        }
        spnLoaiChi.setSelection(vitri);

        ivChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis( System.currentTimeMillis() );

                // nguyên mẫu hàm khởi tạo DatePickerDialgo như sau:
                //DatePickerDialog(@NonNull Context context, @Nullable DatePickerDialog.OnDateSetListener listener, int year, int month, int dayOfMonth)
                DatePickerDialog dialog = new DatePickerDialog(
                        context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                // xử lý sự kiện
                                int nam = i;
                                int thang = i1; // nhận giá trị từ 0 -> 11
                                int ngay = i2;

                                dateKC.setText(nam + "-" + (thang + 1) + "-" + ngay );
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DATE)
                );

                dialog.show();
            }
        });

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tien = edTien.getText().toString();
                HashMap<String, Object> selected = (HashMap<String, Object>) spnLoaiChi.getSelectedItem();
                String tenKhoan = edtenKhoanChi.getText().toString();
                String ngaychi = dateKC.getText().toString();

                int maloai = (int) selected.get("maLoai");

                khoanThuChi.setTien(Integer.parseInt(tien));
                khoanThuChi.setMaLoai(maloai);
                khoanThuChi.setTenKhoan(tenKhoan);
                khoanThuChi.setNgay(ngaychi);
                if (thuChiDAO.updateKhoanThuChi(khoanThuChi)){
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
        list = thuChiDAO.getDSKhoanThuChi("chi");
        notifyDataSetChanged();
    }


}
