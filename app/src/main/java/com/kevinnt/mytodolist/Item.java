package com.kevinnt.mytodolist;

public class Item {

    private int id;
    private String todoText;
    private boolean isChecked;

    public Item(int id, String todoText, boolean isChecked) {
        this.id = id;
        this.todoText = todoText;
        this.isChecked = isChecked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
