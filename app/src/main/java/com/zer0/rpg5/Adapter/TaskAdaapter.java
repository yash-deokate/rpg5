package com.zer0.rpg5.Adapter;

import com.zer0.rpg5.data.MyDbHandler;
import com.zer0.rpg5.ui.Fragments.Todo;

public class TaskAdaapter extends Todo {

    MyDbHandler db=new MyDbHandler(getContext());

    public void deleteChecked(int id){
        db.deleteTaskByid(id);
    }


}
