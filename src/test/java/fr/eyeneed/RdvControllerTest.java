package fr.eyeneed;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import fr.eyeneed.dao.QuestionRepository;
import fr.eyeneed.dao.ReponseRepository;
import fr.eyeneed.dao.Reponse_choisiRepository;
import fr.eyeneed.dao.UtilisateurRepository;
import fr.eyeneed.entities.Reponse_choisi;
import fr.eyeneed.entities.Role;
import fr.eyeneed.entities.Utilisateurs;
import fr.eyeneed.web.AdminController;
import fr.eyeneed.web.RdvController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
//@WebMvcTest(AdminController.class)
@DataJpaTest
public class RdvControllerTest {

//   @Autowired
//   private MockMvc mvc;

   @MockBean
   private RdvController rdvController;
   
   @Autowired
   private TestEntityManager entityManager;
	 
   @Autowired
   private UtilisateurRepository utilisateurRepository;
   @Autowired
   private Reponse_choisiRepository reponse_choisiRepository;
   @Autowired
   private ReponseRepository reponseRepository;


   @Test
   public void getAllReponsesChoisiTest() throws Exception {

       List<Role> role = null;
       this.entityManager.persist(new Utilisateurs("userTest","test@test.fr", "NomTest", "PrenomTest", 1, new Date(), (long)76100, "$2a$10$Ez.zTwdsWifd.zlDHrBgcO64YFt4/cnA3t0h4YBHfsnOKKF4kNmYO", "telTest", role));
       Utilisateurs user = this.utilisateurRepository.findByLogin("userTest");

       ArrayList<String> listeAttendu = new ArrayList<String>();
       listeAttendu.add(String.valueOf(1));
       listeAttendu.add(String.valueOf(175));
       listeAttendu.add(String.valueOf(166));
       listeAttendu.add(String.valueOf(72));
       listeAttendu.add(String.valueOf(25));
       listeAttendu.add(String.valueOf(23));

       
      
       String specialiste = "";
       specialiste = rdvController.getOrientationLunette(listeAttendu,2017,20,1);
       given(specialiste).willReturn("Opticien");
   }
  
}