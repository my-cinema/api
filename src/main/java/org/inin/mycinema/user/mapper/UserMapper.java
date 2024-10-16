package org.inin.mycinema.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper {

    void insertUser( Map<String, String> insertUserMapper );

}
