package com.namuh.assignment.Activity.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.namuh.assignment.Activity.Adapter.QuanlySvAdapter;
import com.namuh.assignment.Activity.Adapter.SpinnerAdapter;
import com.namuh.assignment.Activity.Model.Studentquanly;
import com.namuh.assignment.Activity.Model.Studentxemds;
import com.namuh.assignment.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class QuanlySvActivity extends AppCompatActivity {
    Button btnthemsv;
    EditText edtten, edtngay;
    ListView listViewquanlysv;
    int idclass;
    ArrayList<Studentquanly> studentquanlyArrayList = new ArrayList<>();
    QuanlySvAdapter quanlySvAdapter;
    Spinner spinner;
    ArrayList<Studentxemds> arrSpinner = new ArrayList<>();
    SpinnerAdapter spinnerAdapter;
    EditText edtdate1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_sv);
        anhXa();
        // đỗ dư liệu adapter
        quanlySvAdapter = new QuanlySvAdapter(QuanlySvActivity.this, R.layout.quanlydongsv, studentquanlyArrayList);
        listViewquanlysv.setAdapter(quanlySvAdapter);

        // tạo sk cho ngta chọn ngày
        edtngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay();

            }
        });
        getDATA();

        // bắt sự kiện và thêm sinh viên
        btnthemsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edtten.getText().toString().trim();
                String ngay = edtngay.getText().toString().trim();

                if (ten.length() == 0) {
                    Toast.makeText(QuanlySvActivity.this, "Vui Lòng Nhập Tên", Toast.LENGTH_SHORT).show();
                    edtten.requestFocus();
                    return;
                }

                if (ngay.length() == 0) {
                    Toast.makeText(QuanlySvActivity.this, "Vui Lòng Chọn Ngày ", Toast.LENGTH_SHORT).show();
                    edtngay.requestFocus();
                    return;
                } else {
                    MainActivity.dataBase.QueryData("INSERT INTO SinhVien VALUES(null,'" + ten + "','" + ngay + "','" + idclass + "',null,null)");
//                    dataBase.QueryData("INSERT INTO SinhVien VALUES(null,'"+ ten + "','"+ ngay +  "'");
                    Toast.makeText(QuanlySvActivity.this, "Đã Thêm!", Toast.LENGTH_SHORT).show();
                    getDATA();
                }
            }
        });

        // tạo Alerdiaglog xóa
        listViewquanlysv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                Studentquanly studentxemds = studentquanlyArrayList.get(position);
                final String tenlop = studentxemds.getTen();
                final AlertDialog.Builder builder = new AlertDialog.Builder(QuanlySvActivity.this);
                builder.setTitle("Thông Báo");
                builder.setIcon(R.drawable.warning);
                builder.setMessage("Bạn Có  Muốn Xóa Lớp " + tenlop + " Này Không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Studentquanly studentquanly = studentquanlyArrayList.get(position);
                        final int mssvget = studentquanly.getMsvv();
                        MainActivity.dataBase.QueryData("DELETE FROM SinhVien WHERE Id='" + mssvget + "'");
                        Toast.makeText(QuanlySvActivity.this, "Đã Xóa " + tenlop, Toast.LENGTH_SHORT).show();
                        getDATA();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                return false;
            }
        });
        Spinner();
        getdataSpinner();

    }

    // tạo ngày cho dialog
    private void chonNgay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(QuanlySvActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                i: năm,i1:tháng,i2;ngày
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtngay.setText(simpleDateFormat.format(calendar.getTime()));

            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    // tạo dialog caaph nhật quản lý sinh viên
    private void chonNgaySua() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(QuanlySvActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                i: năm,i1:tháng,i2;ngày
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtdate1.setText(simpleDateFormat.format(calendar.getTime()));

            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    // lấy dữ liệu
    public void getDATA() {
        Cursor dataclas = MainActivity.dataBase.GetData("SELECT * FROM SinhVien");
        studentquanlyArrayList.clear();
        while (dataclas.moveToNext()) {
            int mssv = dataclas.getInt(0);
            String ten = dataclas.getString(1);
            String ngay = dataclas.getString(2);
            idclass = dataclas.getInt(3);
            String sdt = dataclas.getString(4);
            String email = dataclas.getString(5);
            studentquanlyArrayList.add(new Studentquanly(mssv, ten, ngay, idclass, sdt, email));
        }
        quanlySvAdapter.notifyDataSetChanged();
    }

    // lấy dữ liệu spinner
    public void getdataSpinner() {
        Cursor dataspinner = MainActivity.dataBase.GetData("SELECT * FROM Class");
        while (dataspinner.moveToNext()) {
            int id = dataspinner.getInt(0);
            String ten = dataspinner.getString(1);
            String ngaysinh = dataspinner.getString(2);
            arrSpinner.add(new Studentxemds(id, ten, ngaysinh));
            Toast.makeText(this, "Vui Lòng Chọn 1 Lớp", Toast.LENGTH_SHORT).show();
        }
        spinnerAdapter.notifyDataSetChanged();
    }

    // tạo diaglog edit
    public void Dialogedit(final int msvv, final String ten, final String ngay) {
        final Dialog dialog = new Dialog(QuanlySvActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_suaql);

        // ánh xạ
        final EditText edttensv = dialog.findViewById(R.id.edtnhaptenql);
        edtdate1 = dialog.findViewById(R.id.edtnhapngayql);
        Button btnsubmit = dialog.findViewById(R.id.btncapnhatql);
        Button btncancle = dialog.findViewById(R.id.btnhuyql);
        edttensv.setText(ten);
        edtdate1.setText(ngay);

        edtdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgaySua();
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenlop = edttensv.getText().toString().trim();
                final String ngaythang = edtdate1.getText().toString().trim();
                if (edttensv.length() == 0) {
                    Toast.makeText(QuanlySvActivity.this, "Vui Lòng Nhập Tên", Toast.LENGTH_SHORT).show();
                    edttensv.requestFocus();
                    return;
                }
                if (edtdate1.length() == 0) {
                    Toast.makeText(QuanlySvActivity.this, "Vui Lòng Chọn Ngày", Toast.LENGTH_SHORT).show();
                    edtdate1.requestFocus();
                    return;
                } else {
                    // cập nhật quản lý sv
                    MainActivity.dataBase.QueryData("UPDATE SinhVien SET Ten='" + tenlop + "',Ngay='" + ngaythang + "'WHERE Id='" + msvv + "'");
                    Toast.makeText(QuanlySvActivity.this, "Đã Cập Nhật", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    getDATA();
                }
            }
        });

        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    public void Spinner() {
        spinnerAdapter = new SpinnerAdapter(QuanlySvActivity.this, R.layout.activity_customspinner, arrSpinner);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idclass = arrSpinner.get(position).getStt();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    // ánh xạ
    private void anhXa() {
        listViewquanlysv = findViewById(R.id.listtensv);
        edtten = findViewById(R.id.edtTen);
        edtngay = findViewById(R.id.edtngaythangqlsv);
        btnthemsv = findViewById(R.id.btnthemsv);
        spinner = findViewById(R.id.spinnerlop);
    }
}
