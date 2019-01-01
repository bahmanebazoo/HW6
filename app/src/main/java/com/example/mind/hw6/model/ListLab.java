package com.example.mind.hw6.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class ListLab {
    private static ListLab instance;
    private LinkedHashMap<UUID, ToDoList> mToDoListLinkedHashMap;
//    private LinkedHashMap<UUID, ToDoList> mTempToDoListLinkedHashMap;


    private ListLab() {
        mToDoListLinkedHashMap = new LinkedHashMap<>();

    }

    public void addToDo(ToDoList mtoDoList) {
        mToDoListLinkedHashMap.put(mtoDoList.getUUID(), mtoDoList);
    }


    public static ListLab getInstance() {
        if (instance == null) {
            instance = new ListLab();
        }
        return instance;
    }


    public List<ToDoList> getList() {

        return new ArrayList<>(mToDoListLinkedHashMap.values());
    }

    public ToDoList getToDo(UUID id) {

        return mToDoListLinkedHashMap.get(id);
    }

    public int getIndexOfToDo(UUID id) {
        List<ToDoList> list = getList();
        for (int i = 0; i < mToDoListLinkedHashMap.size(); i++) {
            if (list.get(i).getUUID().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public int getPosition(UUID uuid){
        List<ToDoList> position = getList();
        for(int i=0; i<position.size();i++){
            if(position.get(i).getUUID()==uuid){
                return i;
            }
        }

       return -1;

    }

    public LinkedHashMap<UUID, ToDoList> getToDoListLinkedHashMap() {
        return mToDoListLinkedHashMap;
    }

    public List<ToDoList> getListForShow(int tab) {
        List<ToDoList> recyclerViewList = getList();


        if (tab == 2) {
            for (int i = recyclerViewList.size() - 1; i >= 0; i--) {
                if (!recyclerViewList.get(i).isDone()) {
                    recyclerViewList.remove(i);
                }
            }
        } else if (tab == 3) {
            for (int i = recyclerViewList.size() - 1; i >= 0; i--) {
                if (recyclerViewList.get(i).isDone()) {
                    recyclerViewList.remove(i);
                }
            }
        }
        Log.d("bahman", "" + recyclerViewList.size());

        return recyclerViewList;

    }

    public UUID mAddToDo(ToDoList mToDoList){
        ListLab.getInstance().addToDo(mToDoList);
        UUID uuid = mToDoList.getUUID();

        return uuid;
    }

    public void removeTask(UUID uuid){
        getToDoListLinkedHashMap().remove(uuid);
    }

    }

