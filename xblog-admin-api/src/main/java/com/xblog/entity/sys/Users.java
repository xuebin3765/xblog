package com.xblog.entity.sys;

import javax.persistence.*;

/**
 * 用户表
 * Created by Administrator on 2017/9/27.
 */
@Entity
@Table
public class Users {

    private static final int STATUS = 0;
    private static final String DESCRIPTION = "description";
    private static final String PIC = "pic";

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String pic;
    @Column
    private String nickname;
    @Column
    private int status;
    @Column
    private String description;

    public Users() {
    }

    public Users(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.status = STATUS;
        this.description = DESCRIPTION;
        this.pic = PIC;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
