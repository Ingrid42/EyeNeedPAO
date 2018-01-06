package fr.eyeneed.entities;

import java.util.ArrayList;
import java.util.List;

/* object permettant d'enrgistrer les questions/reponses sur la page Form_Admin_Module */
public class ModuleList {
	
	private List<Module> modules= new ArrayList<Module>();
	private List<Module> newModules= new ArrayList<Module>();
	private String moduleSelect = null;
	
	public ModuleList(){}
	
	public ModuleList(List<Module> modules){
		this.modules=modules;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public List<Module> getNewModules() {
		return newModules;
	}

	public void setNewModules(List<Module> newModules) {
		this.newModules = newModules;
	}

	public String getModuleSelect() {
		return moduleSelect;
	}

	public void setModuleSelect(String moduleSelect) {
		this.moduleSelect = moduleSelect;
	}
}