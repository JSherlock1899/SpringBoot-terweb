package com.slxy.terweb.model.excel;

/**
 * @description: 导出excel时的实体类
 * @author: Mr.Jiang
 **/

public class ExcelPatent {

    private String patname;
    private String patsn;
    private String patapdate;
    private String patendate;
    private String patkind;
    private String inventor;//发明人
    private String tsn;
    private String tname;
    private String cname;
    private String dnane;
    private String Tedubackground;//学历
    private String JobTitle;//职称
    private String patremarks;

//    导出


    public ExcelPatent(String patname, String patsn, String patapdate, String patendate, String patkind, String inventor, String tsn, String tname, String cname, String dnane, String tedubackground, String jobTitle, String patremarks) {
        this.patname = patname;
        this.patsn = patsn;
        this.patapdate = patapdate;
        this.patendate = patendate;
        this.inventor = inventor;
        this.tsn = tsn;
        this.tname = tname;
        this.cname = cname;
        this.dnane = dnane;
        Tedubackground = tedubackground;
        JobTitle = jobTitle;
        this.patremarks = patremarks;
    }

    //导入
    public ExcelPatent(String patname, String patsn, String inventor, String tsn,String patapdate, String patendate, String patkind, String patremarks) {
        this.patname = patname;
        this.patsn = patsn;
        this.patapdate = patapdate;
        this.patendate = patendate;
        this.tsn = tsn;
        this.inventor = inventor;
        this.patremarks = patremarks;
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

    public String getTsn() {
        return tsn;
    }

    public void setTsn(String tsn) {
        this.tsn = tsn;
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

    public String getTedubackground() {
        return Tedubackground;
    }

    public void setTedubackground(String tedubackground) {
        Tedubackground = tedubackground;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public String getPatkind() {
        return patkind;
    }

    public void setPatkind(String patkind) {
        this.patkind = patkind;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getPatremarks() {
        return patremarks;
    }

    public void setPatremarks(String patremarks) {
        this.patremarks = patremarks;
    }
}
