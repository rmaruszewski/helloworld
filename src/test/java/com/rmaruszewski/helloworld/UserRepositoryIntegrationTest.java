package com.rmaruszewski.helloworld;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@PropertySource("classpath:application.properties")
@ContextConfiguration(classes={
        DataSourceConfiguration.class,
        HelloWorldConfiguration.class})
public class UserRepositoryIntegrationTest {

    @Autowired
    public UserRepository userRepository;

    @BeforeClass
    public static void initEnvironment() {
        System.setProperty("database.type", "postgres");
    }

    @Test
    @Sql("classpath:data-fixtures/sample_users.sql")
    public void shouldRetrieveUserByExample() {
        Optional<User> savedUser = userRepository.findOne(Example.of(new User("user1")));
        User user = savedUser.get();

        assertThat(user.getUsername()).isEqualTo("user1");
        assertThat(user.getDateOfBirth()).isEqualTo(LocalDate.of(1983, 3, 20));
    }
}
