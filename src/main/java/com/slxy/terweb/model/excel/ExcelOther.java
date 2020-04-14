package com.slxy.terweb.model.excel;

/**
 * @description:导出excel时的实体类
 * @author: Mr.Jiang
 **/

public class ExcelOther {

    private String otherName;
    private String tsn;
    private String tname;
    private String otherType;
    private String otherDate;
    private String publisher;
    private String cname;
    private String dnane;
    private String Tedubackground;//学历
    private String JobTitle;//职称
    private String otherDescribe;

//    导入excel

    public ExcelOther(String otherName, String tsn, String otherType, String otherDate, String publisher, String otherDescribe) {
        this.otherName = otherName;
        this.tsn = tsn;
        this.otherType = otherType;
        this.otherDate = otherDate;
        this.publisher = publisher;
        this.otherDescribe = otherDescribe;
    }

    public ExcelOther(String otherName, String tsn, String tname, String otherType, String otherDate, String publisher, String cname, String dnane, String tedubackground, String jobTitle, String otherDescribe) {
        this.otherName = otherName;
        this.tsn = tsn;
        this.tname = tname;
        this.otherType = otherType;
        this.otherDate = otherDate;
        this.publisher = publisher;
        this.cname = cname;
        this.dnane = dnane;
        Tedubackground = tedubackground;
        JobTitle = jobTitle;
        this.otherDescribe = otherDescribe;
    }

    public String getTsn() {
        return tsn;
    }

    public void setTsn(String tsn) {
        this.tsn = tsn;
    }

    public String getOther_name() {
        return otherName;
    }

    public void setOther_name(String otherName) {
        this.otherName = otherName;
    }

    public String getOther_date() {
        return otherDate;
    }

    public void setOther_date(String otherDate) {
        this.otherDate = otherDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getOther_type() {
        return otherType;
    }

    public void setOther_type(String otherType) {
        this.otherType = otherType;
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

    public String getTedubackground() {
        return Tedubackground;
    }

    public void setTedubackground(String tedubackground) {
        Tedubackground = tedubackground;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getOther_describe() {
        return otherDescribe;
    }

    public void setOther_describe(String otherDescribe) {
        this.otherDescribe = otherDescribe;
    }
}
