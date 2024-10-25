package com.example.mini_projet_cabinet;

public class DetDoct_class {

    int doctor_id,phone,cin;
    String firstname,lastname,username,password,working_days,workind_hours,department,gendre,address;

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
    public void setphone(int phone){
        this.phone = phone;
    }
    public void setcin(int cin){
        this.cin = cin;
    }
    public void setDepartment(String department){
        this.department = department;
    }
    public void setGendre(String gendre){
        this.gendre = gendre;
    }
    public void setAddress(String address){
        this.address = address;
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
    public int getPhone(){
        return phone;
    }
    public int getCin(){
        return cin;
    }
    public String getAddress(){
        return address;
    }
    public String getDepartment(){
        return department;
    }
    public String getGendre(){
        return gendre;
    }


    public DetDoct_class(String username, int doctor_id, String firstname, String lastname, String password, int cin, String department, String gendre, int phone, String address, String working_days, String workind_hours){
        this.doctor_id = doctor_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.working_days = working_days;
        this.workind_hours = workind_hours;
        this.cin = cin;
        this.phone = phone;
        this.department = department;
        this.address = address;
        this.gendre = gendre;
    }
}
