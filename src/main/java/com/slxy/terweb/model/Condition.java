package com.slxy.terweb.model;

/**
 * @program: TeacherWeb
 * @description: 查询的条件类
 * @author: Mr.Jiang
 * @create: 2019-04-21 10:44
 **/

public class Condition {

    private String cname;
    private String dname;
    private String starttime;
    private String endtime;
    private String tname;


    public Condition(String cname, String dname, String starttime, String endtime, String tname) {
        this.cname = cname;
        this.dname = dname;
        this.starttime = starttime;
        this.endtime = endtime;
        this.tname = tname;
    }

    public Condition(String cname, String starttime, String endtime) {
        this.cname = cname;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public Condition() {
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "cname='" + cname + '\'' +
                ", dname='" + dname + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", tname='" + tname + '\'' +
                '}';
    }
}
