package com.google.sps.data;
import com.google.cloud.datastore.Blob;

public final class Post {

  private final String PET_TYPE;
  private final String BREED;
  private final String AGE;
  private final String LOCATION;
  private final String CONTACT_INFO;
  private final String IMAGE;

  public Post(String petType, String breed, String age, String location, String contact_info, String image) {
    this.PET_TYPE = petType;
    this.BREED = breed;
    this.AGE = age;
    this.LOCATION = location;
    this.CONTACT_INFO = contact_info;
    this.IMAGE = image;
  }
}