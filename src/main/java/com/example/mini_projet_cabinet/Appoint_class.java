package com.example.mini_projet_cabinet;

public class Appoint_class {

    int app_id;
    String doctoruser,patientuser,workdays,workhours,seldays,selhours;


    public Appoint_class(int app_id , String doctoruser, String patientuser, String seldays , String selhours , String workdays, String workhours ) {
        this.app_id = app_id;
        this.doctoruser = doctoruser;
        this.patientuser = patientuser;
        this.workdays = workdays;
        this.workhours = workhours;
        this.seldays = seldays;
        this.selhours = selhours;
    }

    public void setApp_id(int app_id){
        this.app_id = app_id;
    }
    public void setDoctoruser(String doctoruser){
        this.doctoruser = doctoruser;
    }
    public void setPatientuser(String patientuser){
        this.patientuser = patientuser;
    }
    public void setWorkdays(String workdays){
        this.workdays = workdays;
    }
    public void setWorkhours(String workhours){
        this.workhours = workhours;
    }
    public void setSeldays(String seldays){
        this.seldays = seldays;
    }
    public void setSelhours(String selhours){this.selhours = selhours;}

    public int getApp_id() {return app_id;}
    public String getDoctoruser(){return doctoruser;}
    public String getPatientuser(){
        return patientuser;
    }
    public String getWorkdays(){
        return workdays;
    }
    public String getWorkhours(){
        return workhours;
    }
    public String getSeldays(){
        return seldays;
    }
    public String getSelhours(){
        return selhours;
    }
}
