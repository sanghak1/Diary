package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DatePicker datePicker;
    TextView viewDatePick;
    EditText edtDiary;
    Button btnSave;

    String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Simple Diary");

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        viewDatePick= (TextView) findViewById(R.id.viewDatePick);
        edtDiary = (EditText) findViewById(R.id.edtDiary);
        btnSave = (Button) findViewById(R.id.btnSave);

        Calendar c = Calendar.getInstance();
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);

        checkedDay(cYear, cMonth, cDay);

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                checkedDay(year, monthOfYear,dayOfMonth);

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v) {
                saveDiary(fileName);
            }
        });
    }

    private void checkedDay(int year, int monthOfYear, int dayOfMonth) {

        viewDatePick.setText(year + " - " + (monthOfYear+1) + " - " + dayOfMonth);
        fileName = year +"" + monthOfYear+ "" + dayOfMonth +".txt";

        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            String str = new String(fileData, "EUC-KR");

            Toast.makeText(getApplicationContext(), "일기 쓴날", Toast.LENGTH_SHORT).show();
            edtDiary.setText(str);
            btnSave.setText("수정하기");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "일기 안쓴날", Toast.LENGTH_SHORT).show();
            edtDiary.setText("");
            btnSave.setText("저장하기");
            e.printStackTrace();
        }
    }

    private void saveDiary(String readDay) {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = edtDiary.getText().toString();

            fos.write(content.getBytes());
            fos.close();

            Toast.makeText(getApplicationContext(), "저장된 일기", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_SHORT).show();
        }
    }

}
