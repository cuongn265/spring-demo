package com.eugene.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Eugene on 11/29/2016.
 */
@Entity
public class Employee {

  private @Id
  @GeneratedValue
  Long id;
  private String firstName;
  private String lastName;
  private String description;

  private Employee() {}

  public Employee(String firstName, String lastName, String description) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
