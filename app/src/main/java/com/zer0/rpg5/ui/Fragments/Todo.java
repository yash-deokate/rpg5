package com.zer0.rpg5.ui.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zer0.rpg5.Adapter.RecyclerViewAdapter;
import com.zer0.rpg5.Model.Task;
import com.zer0.rpg5.R;
import com.zer0.rpg5.data.MyDbHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Todo extends Fragment  {

    View view;
   private RecyclerView recyclerView;
   private RecyclerViewAdapter recyclerViewAdapter;
   ArrayList<Task> taskArrayList;
   private ArrayAdapter<String> arrayAdapter;
    MyDbHandler db;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final int INT = 0;


    public Todo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_todo, container, false);

        //RecyclerView intialisation
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


       db=new MyDbHandler(getContext());



       ////////////////////////////////////////////////////////

//        //creating task object
//        Task task1=new Task();
//        task1.setTask("do homework");
//
//        //adding task to db
//        db.addTask(task1);

        ///////////////////////////////////////////////////////



        taskArrayList=new ArrayList<>();

        //get all tasks
        final List<Task> taskList=db.getAllTasks();

//        for (int i=21;i<24;i++){
//            db.deleteTaskByid(i);
//        }
        for (Task task: taskList){
            Log.d("dbyash","Id: "+task.getId()+"\n"+"Name: "+task.getTask()+"\n");
            taskArrayList.add(task);
        }



        //use your recyclerView
       recyclerViewAdapter=new RecyclerViewAdapter(getContext(),taskArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

       Log.d("dbyash", "No. of tasks: "+db.getCount());


                FloatingActionButton fab = view.findViewById(R.id.addfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText taskEditText = new EditText(getContext());
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String tasktext = String.valueOf(taskEditText.getText());
                                Task task1=new Task();
                                task1.setTask(tasktext);

                                //adding task to db
                                db.addTask(task1);
                                int insertIndex = taskList.size()+1;
                                updateUI();
                                Log.d("dbyash", "Task to add: " + tasktext);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

            }
        });

        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(int position) {
                String str=taskList.get(position).getTask();
                db.deleteTaskByid(taskList.get(position).getId());
                updateUI();
                Log.d("dbyash","item clicked: "+str);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                db.deleteTaskByTask(recyclerViewAdapter.getTaskAt(viewHolder.getAdapterPosition()));
                updateUI();

            }
        }).attachToRecyclerView(recyclerView);


        return view;
    }


    public void updateUI(){
        taskArrayList=new ArrayList<>();

        //get all tasks
        final List<Task> taskList=db.getAllTasks();

        for (Task task: taskList){
            Log.d("dbyash","Id: "+task.getId()+"\n"+"Name: "+task.getTask()+"\n");
            taskArrayList.add(task);
        }
        //use your recyclerView
        recyclerViewAdapter=new RecyclerViewAdapter(getContext(),taskArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);


        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(int position) {
                String str=taskList.get(position).getTask();
                db.deleteTaskByid(taskList.get(position).getId());
                updateUI();
                Log.d("dbyash","item clicked: "+str);

                int temp=getDefaults("xp",getContext());
                temp=temp+1;
                setDefaults("xp",temp,getContext());



            }
        });




    }


    /////////////////////////SHARED PREFERNCES FOR XP//////////////////////

    public static void setDefaults(String key, int value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key,0);
    }





}
