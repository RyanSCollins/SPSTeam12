package com.google.sps.servlets;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.Blob;
import com.google.cloud.datastore.BlobValue;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.KeyFactory;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

@WebServlet("/pet/post")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                maxFileSize=1024*1024*10,      // 10MB
                maxRequestSize=1024*1024*50)   // 50MB
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
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    //Information requested in the form
    String petType = Jsoup.clean(request.getParameter(PET_TYPE), Safelist.none()) ;
    String breed = Jsoup.clean(request.getParameter(BREED), Safelist.none());
    String age = Jsoup.clean(request.getParameter(AGE), Safelist.none());
    String location = Jsoup.clean(request.getParameter(LOCATION), Safelist.none());
    String name = Jsoup.clean(request.getParameter(NAME), Safelist.none()); 
    String email = Jsoup.clean(request.getParameter(EMAIL), Safelist.none()); 
    String phone = Jsoup.clean(request.getParameter(PHONE), Safelist.none()); 



    //Get the picPart of the data
    Part pic = request.getPart(PIC);
    //Convert pic to blob data
    String picString = getValue(pic);
    byte[] byteData = picString.getBytes();
    Blob picBlob = Blob.copyFrom(byteData);

    //Save the information in the database
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind(POST);
    FullEntity postEntity = 
    Entity.newBuilder(keyFactory.newKey()) 
        .set(PET_TYPE, petType)
        .set(BREED, breed)
        .set(AGE, age)
        .set(LOCATION, location)
        .set(PIC, BlobValue.newBuilder(picBlob).setExcludeFromIndexes(true).build())
        .set(NAME, name)
        .set(EMAIL, email)
        .set(PHONE, phone)
        .build();
    datastore.put(postEntity);

    //Redirect to home page
    String baseUrl = request.getRequestURL().substring(0, request.getRequestURL().length() - request.getRequestURI().length()) + request.getContextPath();
    response.sendRedirect(baseUrl);
  }

  //Gets the strign value of a part
  private static String getValue(Part part) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
    StringBuilder value = new StringBuilder();
    char[] buffer = new char[1024];
    for (int length = 0; (length = reader.read(buffer)) > 0;) {
      value.append(buffer, 0, length);
    }
    return value.toString();
  }
}