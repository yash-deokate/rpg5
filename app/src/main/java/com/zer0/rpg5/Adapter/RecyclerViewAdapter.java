package com.zer0.rpg5.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zer0.rpg5.Model.Task;
import com.zer0.rpg5.R;
import com.zer0.rpg5.data.MyDbHandler;
import com.zer0.rpg5.ui.Fragments.Todo;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> {

    private Context context;
    private List<Task> taskList;
    public int id;
    private OnItemClickedListener mListener;
    public interface OnItemClickedListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickedListener listener){
        mListener=listener;
    }

    public RecyclerViewAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }


    //Where to get single card from?? "Kis chij ko dikhana hai?"
    @NonNull
    @Override
    public RecyclerViewAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo,parent,false);
        return new viewHolder(view,mListener);
    }


    //what will happen after we create viewholder object?
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.viewHolder holder, int position) {

        Task task=taskList.get(position);
        holder.checkBox.setText(task.getTask());

    }

    //how many items?
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        public CheckBox checkBox;

        public viewHolder(@NonNull View itemView, final OnItemClickedListener listener) {
            super(itemView);

            checkBox=itemView.findViewById(R.id.todoCheckBox);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (listener!=null){
                       int position = getAdapterPosition();
                       if (position!=RecyclerView.NO_POSITION){
                           listener.onItemClick(position);
                       }
                   }
               }
           });
        }

//        @Override
//        public void onClick(View v) {
//            Log.d("dbyash","item clicked");
//        }



    }

    public Task getTaskAt(int position){
        return taskList.get(position);
    }
}
