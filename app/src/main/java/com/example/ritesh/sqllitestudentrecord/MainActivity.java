package com.example.ritesh.sqllitestudentrecord;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydatabaseHelper;
    EditText editTextid,editTextname,editTextemail,editTextcoursecount;
    Button buttonadd,buttonGetData,buttonUpdate,buttondelete,buttonViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydatabaseHelper=new DatabaseHelper(this);

        editTextid=findViewById(R.id.edittext_id);
        editTextname=findViewById(R.id.edittext_name);
        editTextemail=findViewById(R.id.editText_email);
        editTextcoursecount=findViewById(R.id.editText_CC);

        buttonadd=findViewById(R.id.button_add);
        buttonGetData=findViewById(R.id.button_view);
        buttonUpdate=findViewById(R.id.button_update);
        buttondelete=findViewById(R.id.button_delete);
        buttonViewAll=findViewById(R.id.button_viewAll);

        addData();
        getData();
        viewAll();
        updateData();
        deleteData();
    }

    public void addData(){

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted=mydatabaseHelper.insertData(editTextname.getText().toString(),editTextemail.getText().toString(),editTextcoursecount.getText().toString());
                if (isInserted == true){

                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getData(){
        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=editTextid.getText().toString();

                if (id.equals(String.valueOf(""))){
                    editTextid.setError("Enter ID");
                    return;
                }

                Cursor cursor=mydatabaseHelper.getData(id);
                if (!cursor.isBeforeFirst()){
                    Toast.makeText(getApplicationContext(),"Entered Wrong ID???",Toast.LENGTH_SHORT).show();
                }
                String Data=null;

                if (cursor.moveToNext()){
                    Data=   "ID: "+cursor.getString(0)+"\n"+
                            "Name: "+cursor.getString(1)+"\n"+
                            "Email: "+cursor.getString(2)+"\n"+
                            "Course Count:"+cursor.getString(3)+"\n";
                }

                ShowMessage("Data",Data);

            }
        });
    }

    public void viewAll(){

        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor=mydatabaseHelper.getAllData();

                if (cursor.getCount()==0){
                    ShowMessage("Error","Nothing found in Database");
                    return;
                }

                StringBuffer buffer=new StringBuffer();

                while (cursor.moveToNext()){

                    buffer.append("ID: "+cursor.getString(0)+"\n");
                    buffer.append("Name: "+cursor.getString(1)+"\n");
                    buffer.append("Email: "+cursor.getString(2)+"\n");
                    buffer.append("Course Count: "+cursor.getString(3)+"\n\n");
                }
                ShowMessage("All Data",buffer.toString());
            }
        });
    }

    public void updateData(){

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate=mydatabaseHelper.updateData(editTextid.getText().toString(),
                                                                editTextname.getText().toString(),
                                                                editTextemail.getText().toString(),
                                                                editTextcoursecount.getText().toString());

                if (isUpdate==true){
                    Toast.makeText(MainActivity.this,"Updated SuccesFully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Ooops!!",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void deleteData(){
        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRow=mydatabaseHelper.deleteData(editTextid.getText().toString());
                if (deleteRow.equals(String.valueOf(""))){
                    editTextid.setError("Enter Id");
                }

                if (deleteRow>0){
                    Toast.makeText(MainActivity.this,"Delete Success",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"OOPSS!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ShowMessage(String tittle,String message){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.create();
        builder.setCancelable(true);
        builder.setTitle(tittle);
        builder.setMessage(message);
        builder.show();
    }
}
