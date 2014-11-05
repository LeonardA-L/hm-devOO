package view.utils;

import model.agglomeration.Plan;
import view.agglomeration.VuePlan;

public class InterfaceView {
	
	private VuePlan vue_plan;
	
	public InterfaceView(Plan plan) {
		Fenetre fenetre = new Fenetre(plan);
		fenetre.setVisible(true);
		vue_plan = fenetre.getVuePlan();
	}
	
	public InterfaceView(VuePlan vue_plan) {
		this.vue_plan = vue_plan;
	}

	public VuePlan getVue_plan() {
		return vue_plan;
	}

	public void setVue_plan(VuePlan vue_plan) {
		this.vue_plan = vue_plan;
	}
}
