package com.example.demo.Dao;

import com.example.demo.Entity.AccountEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterDao {

    @Select("SELECT username,password,email FROM authme WHERE username = #{username}")
    AccountEntity QueryPlayerAcountByUsername(@Param("username")String username);

    @Select("SELECT username,password,email FROM authme WHERE email = #{email}")
    AccountEntity QueryPlayerAcountByEmail(@Param("email")String email);

    @Insert("INSERT INTO authme (username, realname, password, email) VALUES (#{username},#{realname},#{password},#{email});")
    void AddPlayerAcount(@Param("username")String username, @Param("realname")String realname, @Param("password")String password,@Param("email")String email);


}
