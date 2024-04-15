package com.example.myapp;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;

public class ApplicationAccount {

     private String FirstName;
     private String LastName;
     private String Email;
     private String Password;
     private String Nickname;

     private String pictureUri;
     private String uid;
     private int NumOfPublications;

     public ApplicationAccount(String Email,String Password){
          this.Email =Email;
          this.Password = Password;
          this.NumOfPublications = 0;
     }
     public ApplicationAccount(String FirstName, String LastName,String Nickname,String uid,String pictureUri){
          this.FirstName = FirstName;
          this.LastName =LastName;
          this.Nickname = Nickname;
          this.uid = uid;
          this.pictureUri = pictureUri;

     }

     public ApplicationAccount(String FirstName, String LastName, String Email, String Password,String Nickname,String pictureUri ){
          this.FirstName = FirstName;
          this.LastName =LastName;
          this.Email =Email;
          this.Password = Password;
          this.Nickname = Nickname;
          this.NumOfPublications = 0;
          this.pictureUri = pictureUri;

     }

     public String getPictureUri() {
          return pictureUri;
     }

     public void setPictureUri(String pictureUri) {
          this.pictureUri = pictureUri;
     }

     public String getUid() {
          return uid;
     }

     public void setUid(String uid) {
          this.uid = uid;
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
