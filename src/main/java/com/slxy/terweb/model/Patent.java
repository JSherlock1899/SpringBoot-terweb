package com.slxy.terweb.model;


import java.io.Serializable;

public class Patent implements Serializable {

  private String patname;
  private String patsn;
  private String patapdate;
  private String patendate;
  private String patkind; //类型
  private String patremarks;
  private String tsn;
  private String paccessory;
  private String paudit;
  private String message;
  private String inventor;//发明人
  private String tname;
  private String cname;
  private String dnane;


  public Patent() {
  }

  public Patent(String patname, String patsn, String patapdate, String patendate, String patkind, String patremarks, String paccessory, String paudit, String message, String inventor, String tname, String cname, String dnane) {
    this.patname = patname;
    this.patsn = patsn;
    this.patapdate = patapdate;
    this.patendate = patendate;
    this.patkind = patkind;
    this.patremarks = patremarks;
    this.paccessory = paccessory;
    this.paudit = paudit;
    this.message = message;
    this.inventor = inventor;
    this.tname = tname;
    this.cname = cname;
    this.dnane = dnane;
  }

  public Patent(String patname, String patsn, String patapdate, String patendate, String patkind, String patremarks, String paccessory, String paudit, String message, String inventor, String tname) {
    this.patname = patname;
    this.patsn = patsn;
    this.patapdate = patapdate;
    this.patendate = patendate;
    this.patkind = patkind;
    this.paccessory = paccessory;
    this.patremarks = patremarks;
    this.paudit = paudit;
    this.message = message;
    this.inventor = inventor;
    this.tname = tname;
  }

  public Patent(String patname, String patsn, String patapdate, String patendate, String patkind, String patremarks, String inventor, String tsn,String paudit) {
    this.patname = patname;
    this.patsn = patsn;
    this.patapdate = patapdate;
    this.patendate = patendate;
    this.patkind = patkind;
    this.patremarks = patremarks;
    this.inventor = inventor;
    this.tsn = tsn;
    this.paudit = paudit;
  }

  //update
  public Patent(String patname, String patsn, String patapdate, String patendate, String patkind, String patremarks, String inventor, String paudit) {
    this.patname = patname;
    this.patsn = patsn;
    this.patapdate = patapdate;
    this.patendate = patendate;
    this.patkind = patkind;
    this.patremarks = patremarks;
    this.paudit = paudit;
    this.inventor = inventor;
  }

  //alter
  public Patent(String patname, String patsn, String patapdate, String patendate, String patkind, String inventor, String paudit) {
    this.patname = patname;
    this.patsn = patsn;
    this.patapdate = patapdate;
    this.patendate = patendate;
    this.patkind = patkind;
    this.paudit = paudit;
    this.inventor = inventor;
  }

  public String getPatname() {
    return patname;
  }

  public void setPatname(String patname) {
    this.patname = patname;
  }

  public String getPatsn() {
    return patsn;
  }

  public void setPatsn(String patsn) {
    this.patsn = patsn;
  }

  public String getPatapdate() {
    return patapdate;
  }

  public void setPatapdate(String patapdate) {
    this.patapdate = patapdate;
  }

  public String getPatendate() {
    return patendate;
  }

  public void setPatendate(String patendate) {
    this.patendate = patendate;
  }

  public String getPatkind() {
    return patkind;
  }

  public void setPatkind(String patkind) {
    this.patkind = patkind;
  }

  public String getPatremarks() {
    return patremarks;
  }

  public void setPatremarks(String patremarks) {
    this.patremarks = patremarks;
  }

  public String getTsn() {
    return tsn;
  }

  public void setTsn(String tsn) {
    this.tsn = tsn;
  }

  public String getPaccessory() {
    return paccessory;
  }

  public void setPaccessory(String paccessory) {
    this.paccessory = paccessory;
  }

  public String getPaudit() {
    return paudit;
  }

  public void setPaudit(String paudit) {
    this.paudit = paudit;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getInventor() {
    return inventor;
  }

  public void setInventor(String inventor) {
    this.inventor = inventor;
  }

  public String getTname() {
    return tname;
  }

  public void setTname(String tname) {
    this.tname = tname;
  }

  public String getCname() {
    return cname;
  }

  public void setCname(String cname) {
    this.cname = cname;
  }

  public String getDnane() {
    return dnane;
  }

  public void setDnane(String dnane) {
    this.dnane = dnane;
  }
}
