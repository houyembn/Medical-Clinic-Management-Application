package com.example.mini_projet_cabinet;

public class DetPat_class {
    int patient_id,cin,phone;
    String firstname,lastname,username,Disease,symptom,bloodGroup,birth,address,gendre;


    public DetPat_class(String username, int patient_id, String firstname, String lastname, String Disease, String symptom, String bloodGroup, String gendre, int cin, int phone, String address, String birth) {
        this.patient_id = patient_id;
        this.Disease = Disease;
        this.symptom = symptom;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.cin = cin;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.gendre = gendre;
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
    public void setCin(int cin){this.cin = cin;}
    public void setPhone(int phone){this.phone = phone;}
    public void setAddress(String address){this.address = address;}
    public void setGendre(String gendre){this.gendre = gendre;}
    public void setBirth(String birth){this.birth = birth;}
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
    public int getCin(){
        return cin;
    }
    public int getPhone(){
        return phone;
    }
    public String getAddress(){
        return address;
    }
    public String getGendre(){
        return gendre;
    }
    public String getBirth(){
        return birth;
    }
    public String getBloodGroup(){
        return bloodGroup;
    }
}
