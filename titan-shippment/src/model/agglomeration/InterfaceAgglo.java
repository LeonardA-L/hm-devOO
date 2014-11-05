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
		Plan p = XMLBuilder.getPlan(absFilePath, this);  	// actually we could call it createPlan as it's building it, not returning it
		if ( p == null ) {
			return false;
		}
		plan = p;
		return true;
	}
	
	public int[][] GetFormatedMap(){
		int[][] matrice = plan.GetMatrix();
		return matrice;
	}
	
	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
}
