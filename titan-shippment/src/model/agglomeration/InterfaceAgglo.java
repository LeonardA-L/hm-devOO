package model.agglomeration;

import utils.XMLBuilder;

public class InterfaceAgglo {
	
	private Plan plan;
	
	
	public InterfaceAgglo() {
		plan = new Plan();
	}
	
	/*	Asks the builder to construct a plan from file
	 * 	absFilePath, using the instance of Plan in 
	 * 	InterfaceAgglo.
	 * 	
	 */
	public boolean BuildPlanFromXml(String absFilePath) 
	{
		Plan p = XMLBuilder.getPlan(absFilePath, this);  
		if ( p == null ) {
			return false;
		}
		this.setPlan(p);
		return true;
	}
	
	
	public boolean GetEntrepotFromBuilder()
	{
		int idEntre = XMLBuilder.getEntrepot();
		if(idEntre == -1) {
			return false;
		}
		else {
			plan.setEntrepot(idEntre);
			return true;
		}
	}
	
	public float[][] GetFormatedMap(){
		float[][] matrice = plan.GetMatrix();
		return matrice;
	}
	
	public void resetPlan() {
		plan.reset();
	}
	
	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
}
