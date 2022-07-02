package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
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
  public static final String CONTACT_INFO = "contactInfo";

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //Information requested in the form
    String petType = Jsoup.clean(request.getParameter(PET_TYPE), Safelist.none()) ;
    String breed = Jsoup.clean(request.getParameter(BREED), Safelist.none());
    String age = Jsoup.clean(request.getParameter(AGE), Safelist.none());
    String location = Jsoup.clean(request.getParameter(LOCATION), Safelist.none());
    String contactInfo = Jsoup.clean(request.getParameter(CONTACT_INFO), Safelist.none()); 

    //Save the information in the database
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind(POST);
    FullEntity contacEntity = 
    Entity.newBuilder(keyFactory.newKey()) 
        .set(PET_TYPE, petType)
        .set(BREED, breed)
        .set(AGE, age)
        .set(LOCATION, location)
        .set(CONTACT_INFO, contactInfo)
        .build();
    datastore.put(contacEntity);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    response.getWriter().println("<h1>Hello world!</h1>");
  }
}