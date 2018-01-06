package fr.eyeneed;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.web.session.HttpSessionEventPublisher;

//à ne plus commenter pour créer le point war

//@SpringBootApplication
//public class EyeNeedApplication extends SpringBootServletInitializer {
//	@Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(EyeNeedApplication.class);
//    }
//
//    public static void main(String[] args) throws Exception {
//        SpringApplication.run(EyeNeedApplication.class, args);
//    }
//	
//		
//}

//à ne plus commenter pour tester l'application dans eclipse

@SpringBootApplication
public class EyeNeedApplication  {
	

    public static void main(String[] args) throws Exception {
        SpringApplication.run(EyeNeedApplication.class, args);
           
    }

		
} 
