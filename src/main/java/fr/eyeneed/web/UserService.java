package fr.eyeneed.web;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eyeneed.dao.IUserService;
import fr.eyeneed.dao.UtilisateurRepository;
import fr.eyeneed.dao.VerificationTokenRepository;
import fr.eyeneed.entities.Utilisateurs;
import fr.eyeneed.entities.VerificationToken;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UtilisateurRepository repository;
 
    @Autowired
    private VerificationTokenRepository tokenRepository;
    
    @Override
    public Utilisateurs registerNewUserAccount(Utilisateurs accountDto){ 
      /*throws EmailExistsException {
         
        if (emailExist(accountDto.getMail())) {
            throw new EmailExistsException(
              "There is an account with that email adress: "
              + accountDto.getMail());
        }*/
         
        Utilisateurs user = new Utilisateurs();
        /*user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
        user.setRole(new Role(Integer.valueOf(1), user));*/
        return repository.save(user);
    }
 
    private boolean emailExist(String email) {
        Utilisateurs user = repository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
     
    @Override
    public Utilisateurs getUser(String verificationToken) {
        Utilisateurs user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }
     
    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
     
    @Override
    public void saveRegisteredUser(Utilisateurs user) {
        repository.save(user);
    }
     
    @Override
    public void createVerificationToken(Utilisateurs user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
}