package com.slxy.terweb.model;


import java.io.Serializable;

public class Other implements Serializable {

  private String otherName;
  private String tsn;
  private String otherDate;
  private String publisher;
  private String accessory;
  private String audit;
  private String message;
  private String otherType;
  private String otherDescribe;
  private String tname;
  private String cname;
  private String dnane;

  public Other(String otherName, String otherDate, String publisher, String accessory, String audit, String message, String otherType, String otherDescribe, String tname, String cname, String dnane) {
    this.otherName = otherName;
    this.otherDate = otherDate;
    this.publisher = publisher;
    this.accessory = accessory;
    this.audit = audit;
    this.message = message;
    this.otherType = otherType;
    this.otherDescribe = otherDescribe;
    this.tname = tname;
    this.cname = cname;
    this.dnane = dnane;
  }


    public Other(String otherName, String tsn, String otherDate, String publisher, String accessory, String audit, String message, String otherType, String otherDescribe, String tname, String cname, String dnane) {
        this.otherName = otherName;
        this.tsn = tsn;
        this.otherDate = otherDate;
        this.publisher = publisher;
        this.accessory = accessory;
        this.audit = audit;
        this.message = message;
        this.otherType = otherType;
        this.otherDescribe = otherDescribe;
        this.tname = tname;
        this.cname = cname;
        this.dnane = dnane;
    }



    public Other(String otherName, String tsn, String otherDate, String publisher, String accessory, String audit, String message, String otherType, String otherDescribe,String tname) {
    this.otherName = otherName;
    this.tsn = tsn;
    this.otherDate = otherDate;
    this.publisher = publisher;
    this.accessory = accessory;
    this.audit = audit;
    this.message = message;
    this.otherType = otherType;
    this.otherDescribe = otherDescribe;
    this.tname = tname;
  }
    //selectOne
    public Other(String otherName, String otherDate, String publisher, String accessory, String audit, String otherType, String otherDescribe,String tname,String tsn) {
    this.otherName = otherName;
    this.otherDate = otherDate;
    this.publisher = publisher;
    this.accessory = accessory;
    this.audit = audit;
    this.otherType = otherType;
    this.otherDescribe = otherDescribe;
    this.tname = tname;
    this.tsn = tsn;
  }

  //insert
    public Other(String otherName, String otherDate, String otherType, String publisher, String otherDescribe, String tsn, String audit,String message) {
        this.otherName = otherName;
        this.tsn = tsn;
        this.otherDate = otherDate;
        this.publisher = publisher;
        this.audit = audit;
        this.otherType = otherType;
        this.otherDescribe = otherDescribe;
        this.message = message;
    }

//    update

  public Other(String otherName, String otherDate, String publisher, String audit, String otherType, String otherDescribe) {
    this.otherName = otherName;
    this.otherDate = otherDate;
    this.publisher = publisher;
    this.audit = audit;
    this.otherType = otherType;
    this.otherDescribe = otherDescribe;
  }

  public Other() {
  }

  public Other(String otherName, String tsn, String otherDate, String publisher, String accessory, String otherType, String otherDescribe) {
    this.otherName = otherName;
    this.tsn = tsn;
    this.otherDate = otherDate;
    this.publisher = publisher;
    this.accessory = accessory;
    this.otherType = otherType;
    this.otherDescribe = otherDescribe;
  }

  public String getOtherName() {
    return otherName;
  }

  public void setOtherName(String otherName) {
    this.otherName = otherName;
  }

  public String getTsn() {
    return tsn;
  }

  public void setTsn(String tsn) {
    this.tsn = tsn;
  }

  public String getOtherDate() {
    return otherDate;
  }

  public void setOtherDate(String otherDate) {
    this.otherDate = otherDate;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getAccessory() {
    return accessory;
  }

  public void setAccessory(String accessory) {
    this.accessory = accessory;
  }

  public String getAudit() {
    return audit;
  }

  public void setAudit(String audit) {
    this.audit = audit;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getOtherType() {
    return otherType;
  }

  public void setOtherType(String otherType) {
    this.otherType = otherType;
  }

  public String getOtherDescribe() {
    return otherDescribe;
  }

  public void setOtherDescribe(String otherDescribe) {
    this.otherDescribe = otherDescribe;
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

  @Override
  public String toString() {
    return "Other{" +
            "otherName='" + otherName + '\'' +
            ", tsn='" + tsn + '\'' +
            ", otherDate='" + otherDate + '\'' +
            ", publisher='" + publisher + '\'' +
            ", accessory='" + accessory + '\'' +
            ", audit='" + audit + '\'' +
            ", message='" + message + '\'' +
            ", otherType='" + otherType + '\'' +
            ", otherDescribe='" + otherDescribe + '\'' +
            ", tname='" + tname + '\'' +
            ", cname='" + cname + '\'' +
            ", dnane='" + dnane + '\'' +
            '}';
  }
}
