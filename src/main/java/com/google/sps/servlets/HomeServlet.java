package com.google.sps.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import com.google.sps.data.Post;


@WebServlet("/home")
public class HomeServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/html;");
    //request.getRequestDispatcher("../webapp/index.html").forward(request, response); 
    
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    Query<Entity> query =
        Query.newEntityQueryBuilder().setKind("post").setOrderBy(OrderBy.asc("location")).build();
    QueryResults<Entity> results = datastore.run(query);

    List<Post> posts = new ArrayList<>();
    while (results.hasNext()) {
      Entity entity = results.next();
      String petType = entity.getString("petType");
      String breed = entity.getString("breed");
      String age = entity.getString("age");
      String location = entity.getString("location");
      String name = entity.getString("name");
      String email = entity.getString("email");
      String phone = entity.getString("phone");
      String pic = entity.getString("pic");

      Post post = new Post(petType, breed, age, location, name, email, phone, pic);
      posts.add(post);
    }

    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(posts));
  }
}
