package com.slg.G3.sos;

public class ContactModel {
    private int id;
    private String phoneNo;
    private String name;
    private String relation;
    private String address;

    //constructor


    public ContactModel(int id, String phoneNo, String name, String relation, String address) {
        this.id = id;
        this.phoneNo = phoneNo;
        this.name = name;
        this.relation = relation;
        this.address = address;
    }

    //validate the phone number, and reformat is necessary
    private String validate(String phone){
        //creating StringBuilder for both the cases
        StringBuilder case1=new StringBuilder("+509");
        StringBuilder case2=new StringBuilder("");

        //check if the string already has a "+"
        if(phone.charAt(0)!='+'){
            for(int i=0; i<phone.length(); i++){
                //remove any spaces or "-"
                if(phone.charAt(i)!='-' && phone.charAt(i)!=' '){
                    case1.append(phone.charAt(i));
                }
            }
            return case1.toString();
        }else{
            for(int i=0; i<phone.length(); i++){
                //remove any spaces or "-"
                if(phone.charAt(i)!='-' || phone.charAt(i)!=' '){
                    case2.append(phone.charAt(i));
                }
            }
            return case2.toString();
        }

    }

    public int getId() {
        return id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getName() {
        return name;
    }

    public String getRelation() {
        return relation;
    }

    public String getAddress() {
        return address;
    }
}