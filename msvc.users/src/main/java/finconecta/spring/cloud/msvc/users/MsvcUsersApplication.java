package finconecta.spring.cloud.msvc.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MsvcUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcUsersApplication.class, args);
    }

}
