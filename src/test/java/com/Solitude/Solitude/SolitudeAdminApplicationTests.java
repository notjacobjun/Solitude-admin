package com.Solitude.Solitude;

import com.Solitude.Entity.UserEntity;
import com.Solitude.Repository.UserEntityRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SolitudeAdminApplicationTests {

    @Test
    void contextLoads() {
    }

    //TODO write tests before you start building
    @DataJpaTest
    static
    class UserEntityRepositoryTest {
        @Test
        // I have two errors here btw, I will try to fix these soon... I'm really new to testing
        static void testUserEntityRepository() {
            UserEntityRepository.save((new UserEntity(
                    "Jacob", "jacob@email.com", 7439, true)));
            assert (UserEntityRepository.findById("Jacob").isPresent());
        }
    }
}
