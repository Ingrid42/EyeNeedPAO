package fr.eyeneed.dao;

import fr.eyeneed.entities.Utilisateurs;
import fr.eyeneed.entities.VerificationToken;

public interface IUserService {
    
    Utilisateurs registerNewUserAccount(Utilisateurs accountDto) ;
      //throws EmailExistsException;
 
    Utilisateurs getUser(String verificationToken);
 
    void saveRegisteredUser(Utilisateurs user);
 
    void createVerificationToken(Utilisateurs user, String token);
 
    VerificationToken getVerificationToken(String VerificationToken);
}
