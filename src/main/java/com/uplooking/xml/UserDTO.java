package com.uplooking.xml;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//@XmlType(propOrder = {"createTime", "age"})
@XmlAccessorType(XmlAccessType.FIELD)//序列化字段
@XmlRootElement(name = "tong_jian")
//@XmlType(name = "UserDTO", propOrder = {"id", "username", "password", "age", "createTime"})//指定序列化的顺序
public class UserDTO implements Serializable {


    @XmlElement(name = "id")
    private Long id;
    @XmlElement(name = "user_name")
    private String username;
    @XmlElement(name = "password")
    private String password;
    @XmlElement(name = "age")
    private Integer age;

    private Object source;

    //类型适配器
    @XmlJavaTypeAdapter(value = DateXmlAdapter.class)
    @XmlElement(name = "create_time")
    private Date createTime;

    private BigDecimal count;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserDTO(Object source, String username) {
        this.source = source;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                '}';
    }

}
