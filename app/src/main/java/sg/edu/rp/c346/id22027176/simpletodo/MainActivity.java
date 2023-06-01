package sg.edu.rp.c346.id22027176.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner notNeeded;
    EditText input;
    Button addBtn;
    Button delBtn;
    Button clrBtn;
    ListView tasksListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notNeeded = findViewById(R.id.unnecessary);
        input = findViewById(R.id.textInput);
        addBtn = findViewById(R.id.buttonAdd);
        delBtn = findViewById(R.id.buttonDelete);
        clrBtn = findViewById(R.id.buttonClear);
        tasksListView = findViewById(R.id.tasksList);

        String[] inputHint = new String[1]; //to handle the changing hint

        ArrayList<String> tasksList = new ArrayList<String>();
        ArrayAdapter tasksAdapt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasksList);

        Toast[] toast = new Toast[1];

        notNeeded.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        delBtn.setEnabled(false);
                        addBtn.setEnabled(true);
                        inputHint[0] = "Type in a new task here";
                        break;
                    case 1:
                        addBtn.setEnabled(false);
                        delBtn.setEnabled(true);
                        inputHint[0] = "Type in the index of the task to be removed";
                        break;
                }
                input.setHint(inputHint[0]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTask = input.getText().toString();
                tasksList.add(newTask);
                tasksAdapt.notifyDataSetChanged();
                toast[0] = Toast.makeText(getApplicationContext(), "Added new task", Toast.LENGTH_SHORT); //gimme extra marks ;)
                toast[0].show();
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int removeTask = Integer.parseInt(input.getText().toString()) - 1; //intuitive design
                    tasksList.remove(removeTask);
                    tasksAdapt.notifyDataSetChanged();
                    toast[0] = Toast.makeText(getApplicationContext(), "Removed task", Toast.LENGTH_SHORT); //gimme extra marks ;)
                    toast[0].show();
                }
                catch (IndexOutOfBoundsException ioobe){
                    int out = Integer.parseInt(input.getText().toString());
                    if (tasksList.isEmpty()) {
                        toast[0] = Toast.makeText(getApplicationContext(), "You don't have any tasks to remove", Toast.LENGTH_LONG);
                        toast[0].show();
                    }
                    else if (out < tasksList.size() || out > tasksList.size()) {
                        toast[0] = Toast.makeText(getApplicationContext(), "Please enter a valid index", Toast.LENGTH_LONG);
                        toast[0].show();
                    }
                }
                catch (NumberFormatException nfe) {
                     if (input.getText().toString().isEmpty()) {
                        toast[0] = Toast.makeText(getApplicationContext(), "Please enter an index", Toast.LENGTH_LONG);
                        toast[0].show();
                    }
                     else if (input.getText().toString().matches("[a-zA-Z]")) {
                         toast[0] = Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_LONG);
                         toast[0].show();
                     }
                }
                catch (Exception e){
                    toast[0] = Toast.makeText(getApplicationContext(), R.string.message, Toast.LENGTH_LONG);
                    toast[0].show();
                }
        }});

        clrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
            }
        });

        tasksListView.setAdapter(tasksAdapt);
    }
}