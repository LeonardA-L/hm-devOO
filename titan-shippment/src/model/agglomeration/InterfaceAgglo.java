package model.agglomeration;

import utils.XMLBuilder;

public class InterfaceAgglo {

	private Plan plan;

	public InterfaceAgglo() {
		plan = new Plan();
	}

	/**
	 * Asks the builder to construct a plan from file absFilePath, using the instance of Plan in InterfaceAgglo.
	 * 
	 * @param absFilePath
	 *            xml absolute file path
	 */
	public boolean buildPlanFromXml(String absFilePath) {
		Plan p = XMLBuilder.getPlan(absFilePath, this);
		if (p == null) {
			return false;
		}
		this.setPlan(p);
		return true;
	}

	/**
	 * Deprecated
	 */
	public float[][] GetFormatedMap() {
		float[][] matrice = plan.getMatrix();
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
