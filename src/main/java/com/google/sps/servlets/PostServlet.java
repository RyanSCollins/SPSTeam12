package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

@WebServlet("/post")
public class PostServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String typeOfPet = Jsoup.clean(request.getParameter("typeofPet"), Safelist.none()) ;
    String breed = Jsoup.clean(request.getParameter("breed"), Safelist.none());
    String age = Jsoup.clean(request.getParameter("age"), Safelist.none());
    String location = Jsoup.clean(request.getParameter("location"), Safelist.none());
    String contactInfo = Jsoup.clean(request.getParameter("contactInfo"), Safelist.none());
    
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    response.getWriter().println("<h1>Hello world!</h1>");
  }
}
