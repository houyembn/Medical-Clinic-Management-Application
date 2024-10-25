package com.example.mini_projet_cabinet;

public class Doctor_class {
    int doctor_id;
    String firstname,lastname,username,password,working_days,workind_hours;

    public void setDoctor_id(int doctor_id){
        this.doctor_id = doctor_id;
    }
    public void setfirstname(String firstname){
        this.firstname = firstname;
    }
    public void setlastname(String lastname){
        this.lastname = lastname;
    }
    public void setusername(String username){
        this.username = username;
    }
    public void setpassword(String password){
        this.password = password;
    }
    public void setworking_days(String working_days){
        this.working_days = working_days;
    }
    public void setworkind_hours(String workind_hours){
        this.workind_hours = workind_hours;
    }

    public int getDoctor_id() {return doctor_id;}
    public String getFirstname(){return firstname;}
    public String getLastname(){
        return lastname;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getWorking_days(){
        return working_days;
    }
    public String getWorkind_hours(){
        return workind_hours;
    }


    public Doctor_class(String username, int doctor_id, String firstname, String lastname, String password, String working_days, String workind_hours){
        this.doctor_id = doctor_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.working_days = working_days;
        this.workind_hours = workind_hours;
    }
}
