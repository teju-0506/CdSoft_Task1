package com.example.notetakingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditNoteActivity extends AppCompatActivity {

    EditText title;
    EditText description;

    EditText date;
    EditText time;
    Button cancle;
    Button save;
    int Noteid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Note");
        setContentView(R.layout.activity_edit_note);

        title=findViewById(R.id.EditTitle);
        description=findViewById(R.id.editTextDescriptionEdit);
        cancle=findViewById(R.id.cancelUpdate);
        save=findViewById(R.id.saveUpdate);
        date=findViewById(R.id.taskdateUpdate);
        time=findViewById(R.id.tasktimeUpdate);

        getData();

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Nothing Updated",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateNote();
            }
        });
    }
    private void UpdateNote()
    {
        String titleLast=title.getText().toString();
        String descriptionLast=description.getText().toString();
        String Date=date.getText().toString();
        String Time=time.getText().toString();
        Intent intent=new Intent();
        intent.putExtra("titleLast",titleLast);
        intent.putExtra("descriptionLast",descriptionLast);
        intent.putExtra("dateLast",Date);
        intent.putExtra("timeLast",Time);
        if(Noteid!=-1){
            intent.putExtra("noteid",Noteid);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
    public void getData()
    {
        Intent i=getIntent();
        Noteid=i.getIntExtra("id",-1);
        String NoteTitle=i.getStringExtra("title");
        String Notedescription=i.getStringExtra("description");
        String NoteDate=i.getStringExtra("date");
        String NoteTime=i.getStringExtra("time");

        title.setText(NoteTitle);
        description.setText(Notedescription);
        date.setText(NoteDate);
        time.setText(NoteTime);
    }
}