package com.namuh.assignment.Activity.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import com.namuh.assignment.Activity.Adapter.SpinnerQlAdapter;
import com.namuh.assignment.Activity.Adapter.Xemsvadapter;
import com.namuh.assignment.Activity.Model.Studentquanly;
import com.namuh.assignment.Activity.Model.Studentxemds;
import com.namuh.assignment.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

public class XemQLActivity extends AppCompatActivity {
    Spinner spinner;
    SpinnerQlAdapter spinnerQlAdapter;
    ArrayList<Studentquanly> arrstudent=new ArrayList<>();
    ArrayList<Studentxemds> arrspinner=new ArrayList<>();
    int idclass;
    int REQUEST_CODE = 123;
//    ListView listViewql;
    SwipeMenuListView listview;
    Xemsvadapter xemsvadapter;
    EditText edtdatecapnhat;
    String sdt,gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_ql);
        spinner=findViewById(R.id.spinnerxemql);
         listview = findViewById(R.id.lisviewql);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                //detail
                SwipeMenuItem detail = new SwipeMenuItem(
                        getApplicationContext());
                detail.setWidth(150);
                detail.setIcon(R.drawable.rsz_detailcyccle);
                detail.setTitleColor(Color.WHITE);
                menu.addMenuItem(detail);
                //phone
                SwipeMenuItem phone = new SwipeMenuItem(getApplicationContext());
                phone.setWidth(150);
                phone.setIcon(R.drawable.phone);
                menu.addMenuItem(phone);
                //gmail
                SwipeMenuItem gmail = new SwipeMenuItem(getApplicationContext());
                gmail.setWidth(150);
                gmail.setIcon(R.drawable.rsz_cyclegmail);
                menu.addMenuItem(gmail);
            }
        };
        listview.setMenuCreator(creator);

        // Close Interpolator
        listview.setCloseInterpolator(new BounceInterpolator());
// Open Interpolator
        listview.setOpenInterpolator(new DecelerateInterpolator());
    // bắt sự kiện

   listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
       @Override
       public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
           switch (index){
               case 0:
                    diaglogxemthongtin(position);
                   break;
               case 1:
                   ActivityCompat.requestPermissions(XemQLActivity.this,new String[] {Manifest.permission.CALL_PHONE},REQUEST_CODE );
                   Studentquanly studentquanly = arrstudent.get(position);
                    sdt = studentquanly.getSdt();
                    break;
               case 2:
                   Studentquanly studentquanly1 = arrstudent.get(position);
                   gmail = studentquanly1.getEmail();
                   Intent intent=new Intent(Intent.ACTION_SEND);
                   String[] recipients={""+gmail};
                   intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                   intent.putExtra(Intent.EXTRA_SUBJECT,"Subject text here...");
                   intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
                   intent.putExtra(Intent.EXTRA_CC,""+gmail);
                   intent.setType("text/html");
                   intent.setPackage("com.google.android.gm");
                   startActivity(Intent.createChooser(intent, "Send mail"));
           }
           return false;
       }
   });
        spinnerql();
        spinnergetData();
        xemsvadapter=new Xemsvadapter(XemQLActivity.this,R.layout.customsxemql,arrstudent);
        listview.setAdapter(xemsvadapter);
        getdatasv();
        Toast.makeText(this, "Đã cập nhật danh sách", Toast.LENGTH_SHORT).show();
//        diaglogxoa();
        dialogchucnang();

    }
    public void spinnerql(){
        spinnerQlAdapter=new SpinnerQlAdapter(XemQLActivity.this, arrspinner, R.layout.activity_customspinnerql);
        spinner.setAdapter(spinnerQlAdapter);
        spinnerQlAdapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               idclass=arrspinner.get(position).getStt();
                getdatasv();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void spinnergetData(){
        Cursor dataspinnerql=MainActivity.dataBase.GetData("SELECT * FROM Class ");
        while (dataspinnerql.moveToNext()){
            int id = dataspinnerql.getInt(0);
            String malop = dataspinnerql.getString(1);
            String tenlop = dataspinnerql.getString(2);
            arrspinner.add(new Studentxemds(id,malop,tenlop));
        }
        spinnerQlAdapter.notifyDataSetChanged();
    }

    public void getdatasv(){
        Cursor datasv=MainActivity.dataBase.GetData("SELECT * FROM SinhVien WHERE Id_class = '" + idclass + "' ");
        arrstudent.clear();
        while (datasv.moveToNext()){
            int id = datasv.getInt(0);
            String ten = datasv.getString(1);
            String ngay = datasv.getString(2);
            idclass = datasv.getInt(3);
            String sdt = datasv.getString(4);
            String email = datasv.getString(5);
            arrstudent.add(new Studentquanly(id,ten,ngay, idclass,sdt,email));
        }
        xemsvadapter=new Xemsvadapter(XemQLActivity.this,R.layout.customsxemql,arrstudent);
        listview.setAdapter(xemsvadapter);
        xemsvadapter.notifyDataSetChanged();
    }

        public void dialogchucnang(){
            listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                final Dialog dialog= new Dialog(XemQLActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_chucnang);
                dialog.show();
                Button btncapnhatql = dialog.findViewById(R.id.btncapnhatq);
                Button btnxoaql = dialog.findViewById(R.id.btnxoaql);
                final Studentquanly  studentquanly = arrstudent.get(position);
                btncapnhatql.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    diaglogcanhapsv(position);
                    dialog.dismiss();

                    }
                });

                btnxoaql.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String ten = studentquanly.getTen();
                        final int mssv = studentquanly.getMsvv();
                        AlertDialog.Builder builder = new AlertDialog.Builder(XemQLActivity.this);
                        builder.setTitle("Thông Báo!");
                        builder.setIcon(R.drawable.warning);
                        builder.setMessage("Bạn có chắc muốn xóa sinh viên "+ten+" này không?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.dataBase.QueryData("DELETE FROM SinhVien WHERE Id = '" + mssv + "'");
                                getdatasv();
                                Toast.makeText(XemQLActivity.this, "Đã xóa sinh viên "+ten, Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.show();
                    }
                });
                return false;
            }
        });

        }

        // diag cập nhât danh sashc sinh viên
        private   void diaglogcanhapsv(int i ){
            Studentquanly studentquanly = arrstudent.get(i);
            final String ten = studentquanly.getTen();
            final String ngay = studentquanly.getNgay();
            final String sdt = studentquanly.getSdt();
            final String email = studentquanly.getEmail();
            final int id = studentquanly.getMsvv();

            final Dialog dialog1 = new Dialog(XemQLActivity.this);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.setContentView(R.layout.dialog_suadsql);
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.show();
            edtdatecapnhat = dialog1.findViewById(R.id.edtngaycapnhat);
            final EditText edttencapnhat = dialog1.findViewById(R.id.edttencapnhat);
            final EditText edtsdt = dialog1.findViewById(R.id.edtsdt);
            final EditText edtemai = dialog1.findViewById(R.id.edtemail);
            Button btnluucapnhat = dialog1.findViewById(R.id.btnluucapnhat);
            Button btnhuycapnhat = dialog1.findViewById(R.id.btnhuycapnhat);

            edttencapnhat.setText(ten);
            edtdatecapnhat.setText(ngay);
            edtsdt.setText(sdt);
            edtemai.setText(email);

            edtdatecapnhat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    diaglogngay();
                }
            });

            btnluucapnhat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tencapnhat = edttencapnhat.getText().toString().trim();
                    String edtdatecapnha = edtdatecapnhat.getText().toString().trim();
                    String edtSdt = edtsdt.getText().toString().trim();
                    String edtemal = edtemai.getText().toString().trim();

                    if(tencapnhat.length()==0){
                        Toast.makeText(XemQLActivity.this, "Vui Lòng Nhập Tên", Toast.LENGTH_SHORT).show();
                        edttencapnhat.requestFocus();
                    }else if(edtdatecapnhat.length()==0){
                        Toast.makeText(XemQLActivity.this, "Vui Lòng Nhập Ngày", Toast.LENGTH_SHORT).show();
                    }else if (edtSdt.length()>11){
                        Toast.makeText(XemQLActivity.this, "SĐT Tối Đa 11 số!", Toast.LENGTH_SHORT).show();
                        edtsdt.requestFocus();
                    }else if (edtSdt.length()==0) {
                        Toast.makeText(XemQLActivity.this, "Vui Lòng Nhập SĐT", Toast.LENGTH_SHORT).show();
                        edtsdt.requestFocus();
//                    }else if (!checkten(ten)){
//                        Toast.makeText(XemQLActivity.this, "Tên Không Hợp Lệ", Toast.LENGTH_SHORT).show();
//                    }
                    }
                    else if (!checkemail(edtemal)) {
                        Toast.makeText(XemQLActivity.this, "Địa Chỉ Email Không Hợp Lệ", Toast.LENGTH_SHORT).show();
                        edtemai.requestFocus();
                    }else {
                        MainActivity.dataBase.QueryData("UPDATE SinhVien SET Ten = '" + tencapnhat + "',Ngay = '" + edtdatecapnha + "',Sdt = '" + edtSdt + "',Email = '" + edtemal + "' WHERE Id = '" + id + "'");
                        Toast.makeText(XemQLActivity.this, "Đã Thêm " + tencapnhat, Toast.LENGTH_SHORT).show();
                        getdatasv();
                        dialog1.dismiss();
//                    }
//                if (tencapnhat.length()!=0 && edtdatecapnha.length()!=0 && edtSdt.length()!=0 && edtemal.length()!=0 ){
//                    if (edttencapnhat.length()==0){
//                        Toast.makeText(XemQLActivity.this, "Vui Lòng Nhập Tên", Toast.LENGTH_SHORT).show();
//                    }else if (checkemail(edtemal)){
//                        Toast.makeText(XemQLActivity.this, "Email Hợp Lệ", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    MainActivity.dataBase.QueryData("UPDATE SinhVien SET Ten = '" + tencapnhat + "',Ngay = '" + edtdatecapnha + "',Sdt = '" + edtSdt + "',Email = '" + edtemal + "' WHERE Id = '" + id + "'");
//                        Toast.makeText(XemQLActivity.this, "Đã Thêm "+tencapnhat, Toast.LENGTH_SHORT).show();
//                        getdatasv();
//                        dialog1.dismiss();
//                }
                    }
                }
            });
            btnhuycapnhat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                }
            });

        }

        // tạo ngày câp nhật quản lý
        private void diaglogngay(){
             final Calendar calendar = Calendar.getInstance();
            int ngay = calendar.get(Calendar.DATE);
            int thang = calendar.get(Calendar.MONTH);
            int nam = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(XemQLActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                i: năm,i1:tháng,i2;ngày
                    calendar.set(i, i1, i2);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    edtdatecapnhat.setText(simpleDateFormat.format(calendar.getTime()));

                }
            }, nam, thang, ngay);
            datePickerDialog.show();

        }

    //tìm kiếm sinh viên
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_sv123).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                xemsvadapter.serachds(s);
                xemsvadapter.notifyDataSetChanged();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    // diaglog xem thông tin chi tiết sinh viên trong quản lý
    private void diaglogxemthongtin(int i){
        Dialog dialog = new Dialog(XemQLActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_xemthongtin);
        dialog.show();
        Studentquanly studentquanly = arrstudent.get(i);
        String ten = studentquanly.getTen();
        String ngay = studentquanly.getNgay();
        String sdt = studentquanly.getSdt();
        String email = studentquanly.getEmail();
        TextView txtten = dialog.findViewById(R.id.edttensinhvien);
        TextView txtngay = dialog.findViewById(R.id.edtNgaySinh);
        TextView txtsdt = dialog.findViewById(R.id.edtsdt);
        TextView txtemai = dialog.findViewById(R.id.edtEmail);
        txtten.setText(ten);
        txtngay.setText(ngay);
        txtsdt.setText(sdt);
        txtemai.setText(email);

    }

    // cấp quyền gọi
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE &&  grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(XemQLActivity.this);
            builder.setIcon(R.drawable.warning);
            builder.setTitle("Thông Báo!");
            builder.setMessage("Bạn Có Muốn Gọi Số "+sdt+" Này Không?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+sdt));
                    startActivityForResult(intent,REQUEST_CODE);
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }else {
            Toast.makeText(this, "Cấp Quyền Thất Bại!", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // check email
    private boolean checkemail(String email){
        Pattern Email = Pattern.compile( "[a-zA-Z0-9+._%-+]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                "(" +
                "." +
                "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                ")+");
        return Email.matcher(email).matches();
    }


}
