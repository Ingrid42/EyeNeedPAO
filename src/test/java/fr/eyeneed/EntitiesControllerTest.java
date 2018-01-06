package fr.eyeneed;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.eyeneed.dao.UtilisateurRepository;
import fr.eyeneed.entities.Role;
import fr.eyeneed.entities.Utilisateurs;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class EntitiesControllerTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Test
    public void testExample() throws Exception {
    	List<Role> role = null;
        this.entityManager.persist(new Utilisateurs("testUser","dev@eyeneed.fr", "NomTest", "PrenomTest", 1, new Date(), (long)76230, "$2a$10$Ez.zTwdsWifd.zlDHrBgcO64YFt4/cnA3t0h4YBHfsnOKKF4kNmYO", "telTest", role));
        Utilisateurs user = this.utilisateurRepository.findByLogin("testUser");
        assertThat(user.getNom()).isEqualTo("NomTest");
        assertThat(user.getPrenom()).isEqualTo("PrenomTest");
    }

}