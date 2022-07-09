package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.Blob;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.KeyFactory;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

@WebServlet("/pet/post")
public class PetPostServlet extends HttpServlet {
  //Constants for use across methods
  public static final String POST = "post";
  public static final String PET_TYPE = "petType";
  public static final String BREED = "breed";
  public static final String AGE = "age";
  public static final String LOCATION = "location";
  public static final String PIC = "pic";
  public static final String NAME = "name";
  public static final String EMAIL = "email";
  public static final String PHONE = "phone";



  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //Information requested in the form
    String petType = Jsoup.clean(request.getParameter(PET_TYPE), Safelist.none()) ;
    String breed = Jsoup.clean(request.getParameter(BREED), Safelist.none());
    String age = Jsoup.clean(request.getParameter(AGE), Safelist.none());
    String location = Jsoup.clean(request.getParameter(LOCATION), Safelist.none());
    String pic = Jsoup.clean(request.getParameter(PIC), Safelist.none()); 
    String name = Jsoup.clean(request.getParameter(NAME), Safelist.none()); 
    String email = Jsoup.clean(request.getParameter(EMAIL), Safelist.none()); 
    String phone = Jsoup.clean(request.getParameter(PHONE), Safelist.none()); 


    //Convert pic to blob data
    byte[] byteData = pic.getBytes();
    Blob blobData = Blob.copyFrom(byteData);

    //Save the information in the database
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind(POST);
    FullEntity postEntity = 
    Entity.newBuilder(keyFactory.newKey()) 
        .set(PET_TYPE, petType)
        .set(BREED, breed)
        .set(AGE, age)
        .set(LOCATION, location)
        .set(PIC, blobData)
        .set(NAME, name)
        .set(EMAIL, email)
        .set(PHONE, phone)
        .build();
    datastore.put(postEntity);


    String baseUrl = request.getRequestURL().substring(0, request.getRequestURL().length() - request.getRequestURI().length()) + request.getContextPath();
    response.sendRedirect(baseUrl);
  }
}