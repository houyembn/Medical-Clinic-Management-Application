package com.example.mini_projet_cabinet;

public class Patient_class {
    int patient_id;
    String firstname,lastname,username,Disease,symptom,bloodGroup;


    public Patient_class(String username, int patient_id, String firstname, String lastname, String Disease, String symptom, String bloodGroup) {
        this.patient_id = patient_id;
        this.Disease = Disease;
        this.symptom = symptom;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.bloodGroup = bloodGroup;
    }

    public void setPatient_id(int patient_id){
        this.patient_id = patient_id;
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
    public void setDisease(String Disease){
        this.Disease = Disease;
    }
    public void setSymptom(String symptom){
        this.symptom = symptom;
    }
    public void setBloodGroup(String bloodGroup){this.bloodGroup = bloodGroup;}

    public int getPatient_id() {return patient_id;}
    public String getFirstname(){return firstname;}
    public String getLastname(){
        return lastname;
    }
    public String getUsername(){
        return username;
    }
    public String getSymptom(){
        return symptom;
    }
    public String getDisease(){return Disease;}
    public String getBloodGroup(){return bloodGroup;}
}
