package model.agglomeration;

public class InterfaceAgglo {
	
	private Plan plan;
	
	public InterfaceAgglo() {
		
	}
	
	public InterfaceAgglo(Plan plan) {
		this.plan = plan;
	}
	
	public boolean GetAgglosFromBuilder(String absFilePath) {
		return true;
	}
	
	private boolean GeneratePlan() {
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
