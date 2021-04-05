package com.example.increase;

public class Contact {
    int id;
    String ref;
    String attend;
    public Contact(){   }
    public Contact(int id, String name, String _phone_number){
        this.id = id;
        this.ref = name;
        this.attend = _phone_number;
    }

    public Contact(String name, String _phone_number){
        this.ref = name;
        this.attend = _phone_number;
    }
    public int getID(){
        return this.id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getRef(){
        return this.ref;
    }

    public void setRef(String name){
        this.ref = name;
    }

    public String getAttend(){
        return this.attend;
    }

    public void setAttend(String phone_number){
        this.attend = phone_number;
    }
}