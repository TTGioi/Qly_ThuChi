package com.example.asmht.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.asmht.R;
import com.example.asmht.adapter.KhoanThuChiAdapter;
import com.example.asmht.dao.KhoanThuChiDAO;
import com.example.asmht.model.KhoanThuChi;
import com.example.asmht.model.Loai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class KhoanThuFragment extends Fragment {
    ListView listViewKhoanThu;
    ArrayList<KhoanThuChi> list;
    KhoanThuChiDAO khoanThuChiDAO;
    ArrayList<HashMap<String, Object>> listSpinner;
    KhoanThuChiAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.khoanthu_fragment, container, false);

        listViewKhoanThu = view.findViewById(R.id.lv_KhoanThu);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAddKT);

        khoanThuChiDAO = new KhoanThuChiDAO(getContext());
        getData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                showDialgAdd();
            }
        });
        return view;
    }
    private void getData(){
        list = khoanThuChiDAO.getDSKhoanThuChi("thu");

        adapter = new KhoanThuChiAdapter(list, getContext(), khoanThuChiDAO, getDataSpinner());
        listViewKhoanThu.setAdapter(adapter);
    }
    private void showDialgAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_themkhoanthu, null);
        Spinner spnLoaiThu = view.findViewById(R.id.spnLoaiThu);
        EditText edTien = view.findViewById(R.id.edTien);
        EditText edtenKT = view.findViewById(R.id.edTenKhoanThuT);
        EditText dateKT = view.findViewById(R.id.edNgayThuT);
        ImageView ivThu = view.findViewById(R.id.ivThuT);

        builder.setView(view);

        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                getDataSpinner(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiThu.setAdapter(adapter);

        ivThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis( System.currentTimeMillis() );

                // nguyên mẫu hàm khởi tạo DatePickerDialgo như sau:
                //DatePickerDialog(@NonNull Context context, @Nullable DatePickerDialog.OnDateSetListener listener, int year, int month, int dayOfMonth)
                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                // xử lý sự kiện
                                int nam = i;
                                int thang = i1; // nhận giá trị từ 0 -> 11
                                int ngay = i2;

                                dateKT.setText(nam + "-" + (thang + 1) + "-" + ngay );
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DATE)
                );

                dialog.show();
            }
        });

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tien = edTien.getText().toString();
                String tenKhoan = edtenKT.getText().toString();
                String ngay = dateKT.getText().toString();
                HashMap<String, Object> selected = (HashMap<String, Object>) spnLoaiThu.getSelectedItem();
                int maLoai = (int) selected.get("maLoai");
                KhoanThuChi khoanThuChi = new KhoanThuChi(Integer.parseInt(tien), maLoai);
                khoanThuChi.setTenKhoan(tenKhoan);
                khoanThuChi.setNgay(ngay);
                if (khoanThuChiDAO.insertKhoanThuChi(khoanThuChi)){
                    getData();
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }

    private ArrayList<HashMap<String, Object>> getDataSpinner(){
        ArrayList<Loai> listLoai = khoanThuChiDAO.getDSLoai("thu");
        listSpinner = new ArrayList<>();

        for (Loai loai : listLoai){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maLoai",loai.getMaLoai());
            hashMap.put("tenLoai",loai.getTenLoai());
            listSpinner.add(hashMap);
        }
        return listSpinner;
    }

}
