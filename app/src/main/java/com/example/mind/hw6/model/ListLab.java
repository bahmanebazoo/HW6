package com.example.mind.hw6.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class ListLab {
    private static ListLab instance;
    private LinkedHashMap<UUID, ToDoList> mToDoListLinkedHashMap;

    public static ListLab getInstance() {
        if (instance == null) {
            instance = new ListLab();
        }
        return instance;
    }

    public List<ToDoList> getList(){
        return new ArrayList<>(mToDoListLinkedHashMap.values());
    }
    public ToDoList getToDo(UUID id){

        return mToDoListLinkedHashMap.get(id);
    }
    public int getIndexOfToDo(UUID id){
        List<ToDoList> list = getList();
        for(int i = 0 ; i<mToDoListLinkedHashMap.size() ; i++){
            if(list.get(i).getUUID().equals(id)){
                return i;
            }
        }
        return -1;
    }
}
