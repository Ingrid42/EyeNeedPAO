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
import fr.eyeneed.dao.RdvPatientRepository;
import fr.eyeneed.dao.UtilisateurRepository;
import fr.eyeneed.entities.Module;
import fr.eyeneed.entities.Question;
import fr.eyeneed.entities.Utilisateurs;
import fr.eyeneed.entities.indicateurs;
import fr.eyeneed.entities.rdvPatient;

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
public class rdvRestService {
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private IndicateursRepository indicateursRepository;
	@Autowired
	private RdvPatientRepository rdvPatientRepository;
	
		
		@RequestMapping(value="/readRdv/{id}", method=RequestMethod.GET)
		public String readRdv(@PathVariable(value="id") Long id){
			System.out.println("id du rdv ="+id);
			rdvPatient rdv=rdvPatientRepository.findOne(id);
			if(rdv !=null){
				
				return rdv.getCommentaire();
			} else return "";
			
				
			
		}
		
}
