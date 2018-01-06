package fr.eyeneed.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.eyeneed.dao.IndicateursRepository;
import fr.eyeneed.dao.UtilisateurRepository;
import fr.eyeneed.entities.Module;
import fr.eyeneed.entities.Question;
import fr.eyeneed.entities.Utilisateurs;
import fr.eyeneed.entities.indicateurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class UtilisateurRestService {
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private IndicateursRepository indicateursRepository;
	@RequestMapping(value="/majVisite",method=RequestMethod.GET)	
	//fonction qui liste les utilisateur connecté
	public boolean majVisite(Model model){
		
		Calendar d=Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		indicateurs i=new indicateurs();
		i=indicateursRepository.findOne(format1.format(d.getTime()));
		if(i == null){
			//si première visite
			i=new indicateurs(format1.format(d.getTime()));
			i.setVisites(1);
			indicateursRepository.save(i);
		} else {
			i.setVisites(i.getVisites()+1);
			indicateursRepository.save(i);
		}
		
		
		return true;
	}
		@Secured({"ROLE_ADMIN"})
		@RequestMapping(value="/restSaveUtilisateur")
		public Utilisateurs saveUtilisateur(Utilisateurs e){
			return utilisateurRepository.save(e);
		}
		@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
		@RequestMapping(value="/restUtilisateurs")
		public Page<Utilisateurs> listUtilisateur(int page,int size){
			return utilisateurRepository.findAll(new PageRequest(page, size));
		}
		@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
		@RequestMapping(value="/restUtilisateur/{email}")
		public Utilisateurs Utilisateur(@PathVariable(value="email") String email){
				return utilisateurRepository.findOne(email);
		}
		@RequestMapping(value="/getLogedUser")
		public Utilisateurs getLogedUser(HttpServletRequest httpServletRequest){
		HttpSession session=httpServletRequest.getSession();
		SecurityContext	securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		/*List<String> roles=new ArrayList<>();
		for(GrantedAuthority ga:securityContext.getAuthentication().getAuthorities()){
			roles.add(ga.getAuthority());
		}*/
		//Map<String, Object> params=new HashMap<>();
		//params.put("username", username);
		//params.put("roles", roles);
		return utilisateurRepository.findByEmail(username);
			}
		
		@RequestMapping(value="/checkUsername/{login}", method=RequestMethod.GET)
		public boolean login(@PathVariable(value="login") String login){
			System.out.println("login="+login);
			if(utilisateurRepository.findByLogin(login) !=null){
				return true;
			} else return false;
			
				
			
		}
		@RequestMapping(value="/checkPassword", method=RequestMethod.GET)
		public boolean password(@RequestParam(name="login")  String login,@RequestParam(name="password") String password){
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String existingPassword = password;
			String dbPassword       = utilisateurRepository.findByLogin(login).getPassword();

			if (passwordEncoder.matches(existingPassword, dbPassword)) {
				System.out.println("password ok");
				return true;
				
			} else {
				System.out.println("password faux");
				return false;
			}
			
			
				
			
		}
}
