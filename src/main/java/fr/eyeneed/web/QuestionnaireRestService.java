package fr.eyeneed.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.eyeneed.dao.ModuleRepository;
import fr.eyeneed.dao.QuestionRepository;
import fr.eyeneed.dao.ReponseRepository;
import fr.eyeneed.dao.Reponse_choisiRepository;
import fr.eyeneed.dao.UtilisateurRepository;
import fr.eyeneed.entities.Module;
import fr.eyeneed.entities.Question;
import fr.eyeneed.entities.Reponse;
import fr.eyeneed.entities.Reponse_choisi;
import fr.eyeneed.entities.Utilisateurs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QuestionnaireRestService {
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private ReponseRepository reponseRepository;
	@Autowired
	private ModuleRepository moduleRepository;
	@Autowired
	private Reponse_choisiRepository reponseChoisiRepository;
		@Secured({"ROLE_ADMIN"})
		@RequestMapping(value="/saveReponse",method=RequestMethod.PUT)
		public Reponse_choisi saveReponse(@RequestBody Reponse_choisi r){
			return reponseChoisiRepository.save(r);
		}
		@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
		@RequestMapping(value="/questions/{id}",method=RequestMethod.GET)
		public Page<Utilisateurs> listUtilisateur(int page,int size){
			return utilisateurRepository.findAll(new PageRequest(page, size));
		}
		@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
		@RequestMapping(value="/restUtilisateurs",method=RequestMethod.GET)
		public List<Utilisateurs> resttUtilisateur(){
			return utilisateurRepository.findAll();
		}
		@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
		@RequestMapping(value="/restModules",method=RequestMethod.GET)
		public List<Module> restModules(){
			return moduleRepository.findAll();
		}
		@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
		@RequestMapping(value="/restReponses",method=RequestMethod.GET)
		public List<Reponse> restReponses(){
			return reponseRepository.findAll();
		}
		@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
		@RequestMapping(value="/restQuestions",method=RequestMethod.GET)
		public List<Question> restQuestions(){
			return questionRepository.findAll();
		}
		@Secured({"ROLE_ADMIN","ROLE_MEDECIN","ROLE_USER"})
		@RequestMapping(value="/module/{id}", method=RequestMethod.GET)
		public List<Question> restModule(@PathVariable(value="id") Long id){
			System.out.println("id="+id);
			Module m=moduleRepository.findOne(id);
			List<Question> questions=questionRepository.chercherModule(m); //Liste des questions du module listé par position
				
			return questions;
		}
		
		@RequestMapping(value="/listReponseVoisines/{id}", method=RequestMethod.GET)
		public String reponsesVoisines(@PathVariable(value="id") Long id){
			System.out.println("id de la réponse="+id);
			if(reponseRepository.findOne(id) !=null){
				List <Reponse> reponses=reponseRepository.chercherQuestion(reponseRepository.findOne(id).getQuestion());
				String output="";
				for (Reponse reponse : reponses) {
					if(reponse.getId_reponse()!=id){
						output+=reponse.getId_reponse() + ";";
						
					}
				}
				 return output;
			} else return "";
			
				
			
		}
		
}
