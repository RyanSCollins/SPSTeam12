package com.google.sps.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/home")
public class HomeServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/html;");
    request.getRequestDispatcher("webapp/index.html").forward(request, response); 
    //I'm not sure how to forward it to an HTML page in a different directory
  }
}
