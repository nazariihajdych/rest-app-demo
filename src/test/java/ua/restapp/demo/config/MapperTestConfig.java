package ua.restapp.demo.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.restapp.demo.model.mapper.UserMapper;

@Configuration
public class MapperTestConfig {
    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }
}
