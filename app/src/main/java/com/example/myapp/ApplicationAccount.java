package com.example.myapp;

import java.util.ArrayList;
import java.util.HashMap;

public class ApplicationAccount {

     private String FirstName;
     private String LastName;
     private String Email;
     private String Password;
     private String Nickname;
     private int NumOfPublications;

     public ApplicationAccount(String Email,String Password){
          this.Email =Email;
          this.Password = Password;
          this.NumOfPublications = 0;
     }
     public ApplicationAccount(String FirstName, String LastName, String Email, String Password,String Nickname ){
          this.FirstName = FirstName;
          this.LastName =LastName;
          this.Email =Email;
          this.Password = Password;
          this.Nickname = Nickname;
          this.NumOfPublications = 0;
     }

     public int getNumOfPublications() {
          return NumOfPublications;
     }

     public void setNumOfPublications(int numOfPublications) {
          NumOfPublications = numOfPublications;
     }

     public String getNickname() {
          return Nickname;
     }

     public void setNickname(String nickname) {
          Nickname = nickname;
     }

     public String getFirstName() {
          return FirstName;
     }

     public void setFirstName(String firstName) {
          FirstName = firstName;
     }

     public String getLastName() {
          return LastName;
     }

     public void setLastName(String lastName) {
          LastName = lastName;
     }

     public String getEmail() {
          return Email;
     }

     public void setEmail(String email) {
          Email = email;
     }

     public String getPassword() {
          return Password;
     }

     public void setPassword(String password) {
          Password = password;
     }
}
