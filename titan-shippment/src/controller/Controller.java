package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import view.agglomeration.VueNoeud;
import view.utils.InterfaceView;
import model.planning.InterfacePlanning;
import model.planning.Livraison;
import model.agglomeration.InterfaceAgglo;

public class Controller implements ActionListener {

	private static Controller INSTANCE = null;

	private InterfaceAgglo interfaceAgglo;
	private InterfacePlanning interfacePlanning;
	private InterfaceView interfaceView;
	private UndoRedo undoRedo;

	private boolean mapLoaded;
	private boolean livraisonsLoaded;
	private boolean tourneeCalculed;
	private boolean addingNewLivraison;
	private int nouvelleAdresseLivraison = -1;

	final String ERROR_DELIVERY_LOAD = "Erreur - Chargement des livraisons";
	final String ERROR_DELIVERY_MOD = "Erreur - Modification des livraisons";
	final String ERROR_MAP = "Erreur - Chargement Plan";
	final String ERROR_TOURNEE = "Erreur - Calcul de la tournee";
	final String ERROR_INSTRUC = "Erreur - G�n�ration des instructions";
	final String WARNING_UNDO = "Undo";
	final String WARNING_REDO = "Redo";

	/**
	 * Constructor Private in order to implement the singleton
	 */
	private Controller() {
		mapLoaded = false;
		livraisonsLoaded = false;
		addingNewLivraison = false;
		tourneeCalculed = false;
	}

	/**
	 * Return the singleton
	 * 
	 * @return Controller
	 */
	public static Controller getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Controller();
		}
		return INSTANCE;
	}

	/**
	 * Called by the view in order to process user input on map
	 * 
	 * @param action
	 *            Action triggered
	 * @param x
	 *            X Coord of the point clicked
	 * @param y
	 *            Y Coord of the point clicked
	 */
	public void trigger(String action, int x, int y) {
		if (action.equals("click_map")) {
			if (!mapLoaded) {
				return;
			}

			VueNoeud view_noeud = interfaceView.getVuePanel().getVue_plan()
					.getWhoIsClicked(x, y);

			if (!livraisonsLoaded) {
				interfaceView
						.displayAlert(
								ERROR_DELIVERY_LOAD,
								"Vous devez charger les livraisons avant de pouvoir les modifier.",
								"warning");
				return;
			}
			if (!tourneeCalculed) {
				interfaceView
						.displayAlert(
								ERROR_DELIVERY_MOD,
								"Vous devez calculer une tourn�e avant de modifier des livraisons.",
								"warning");
				return;
			}

			if (view_noeud != null) { // user clicked on a node
				if (!addingNewLivraison) { // 1st step of delivery creation
					boolean deleted = deleteNoeud(view_noeud.getNoeud().getId());
					if (deleted) {
						return;
					}
					addingNewLivraison = true;
					nouvelleAdresseLivraison = view_noeud.getNoeud().getId();
				} else if (addingNewLivraison) { // 2nd step
					if (!interfacePlanning.isNodeADelivery(view_noeud
							.getNoeud().getId())) { // stop if node is already a
													// delivery
						interfaceView.displayAlert("Ajouter une livraison",
								"Ce noeud n'a pas de livraison", "warning");
						interruptAddingNewLivraison();
						return;
					}

					// -- Input user for data about new delivery
					int adressePrecedente = view_noeud.getNoeud().getId();
					String[] retour = interfaceView.askPlageHoraire(); // 0 :
																		// heureDebut
																		// / 1 :
																		// heureFin
																		// / 2 =
																		// idClient
					if (retour[0] == null || retour[1] == null) {
						interruptAddingNewLivraison();
						return;
					}
					String heureDebut = retour[0];
					String heureFin = retour[1];
					int idClient = Integer.parseInt(retour[2]);
					// Actual delivery creation :
					boolean success = undoRedo.InsertAddCmd(idClient,
							heureDebut, heureFin, nouvelleAdresseLivraison,
							adressePrecedente);
					if (!success) {
						return;
					}
					interruptAddingNewLivraison();
				} else {
					interfaceView.displayAlert("Ajouter une livraison", "",
							"warning");
				}
				interfaceView.highlight(view_noeud); // red node
			}
		} else if (action.equals("mouse_moved_on_map")) {
			if (mapLoaded) {
				VueNoeud view_noeud = interfaceView.getVuePanel().getVue_plan()
						.getWhoIsClicked(x, y);
				String infos = "";

				if (view_noeud != null) {
					if (interfacePlanning.isNodeADelivery(view_noeud.getNoeud()
							.getId())) {
						Livraison livraison = interfacePlanning
								.getLivraisonByAdr(view_noeud.getNoeud()
										.getId());
						if (livraison != null) {
							infos = livraison.toString();
						} else {
							infos = "Entrepot";
						}
					} else {
						infos = view_noeud.getNoeud().toString();
					}
				}
				interfaceView.setInfoPoint(infos);
			}
		}
	}

	/**
	 * Reset the state of the trigger (right above in code) so that a delivery
	 * creation process doesn't start with next user click.
	 */
	private void interruptAddingNewLivraison() {
		addingNewLivraison = false;
		interfaceView.clearHighlightedNodes();
	}

	/**
	 * Called by the view in order to process user's inputs on buttons
	 * 
	 * @param action
	 *            Type of action (load file, process sth...)
	 * @param name
	 *            SubType of action (which file to load, what to process...)
	 */
	public void trigger(String action, String name) {
		if (addingNewLivraison) {
			interfaceView
					.displayAlert(
							ERROR_TOURNEE,
							"Vous ne pouvez pas charger de fichier ou calculer une nouvelle tourn�e pendant l'ajout d'un point de livraison.",
							"warning");
		} else {
			if (action.equals("loadFile")) {
				if (name.equals("loadMap")) { // LOAD MAP
					String filename = interfaceView.loadFile();

					if (filename != null && filename.length() > 0) {
						// Remove former map
						resetPlan();
						resetLivraisons();
						resetTournee();

						boolean buildOk = interfaceAgglo
								.buildPlanFromXml(filename);
						if (buildOk) {
							mapLoaded = true;
						} else {
							interfaceView
									.displayAlert(
											ERROR_MAP,
											"La carte n'a pas �t� charg�e correctement.",
											"error");
							interfaceView.repaint();
							return;
						}
						// Set views and scale map
						interfaceAgglo.getPlan().fitJPanel(
								interfaceView.getVuePanel().getHeight(),
								interfaceView.getVuePanel().getWidth());
						interfaceView.getVuePanel().getVue_plan()
								.setPlan(interfaceAgglo.getPlan());
						interfaceView.repaint();
					}
				} else if (name.equals("loadLivraisons")) { // LOAD DELIVERIES
					if (!mapLoaded) {
						interfaceView.displayAlert(ERROR_DELIVERY_LOAD,
								"Vous devez charger une carte au pr�alable.",
								"warning");
					} else {
						String filename = interfaceView.loadFile();

						if (filename != null && filename.length() > 0) {

							resetLivraisons();
							resetTournee();

							// load livraisons
							boolean buildOk = interfacePlanning
									.GetPlanningsFromBuilder(filename);
							if (buildOk) {
								livraisonsLoaded = true;
							} else {
								interfaceView
										.displayAlert(
												ERROR_DELIVERY_LOAD,
												"Les livraisons n'ont pas �t� charg�es correctement",
												"error");
								interfaceView.repaint();
								return;
							}

							// set views
							boolean creatingViewOk = interfaceView
									.genererVueLivraisons(interfacePlanning
											.getListeLivraisons(),
											interfacePlanning.getEntrepot());
							// pour les tourn�es, rien � voir
							// .getVue_tournee().setTournee(interfacePlanning.getTournee());

							if (!creatingViewOk) {
								livraisonsLoaded = false;
								interfaceView.displayAlert(ERROR_DELIVERY_LOAD,
										"Un noeud est introuvable.", "error");
							}

							interfaceView.repaint();
						}
					}
				}
			} else if (action.equals("click_button")) {
				if (name.equals("calculTournee")) {
					if (!mapLoaded || !livraisonsLoaded) {
						interfaceView
								.displayAlert(
										ERROR_TOURNEE,
										"Vous devez charger une carte et une livraison au pr�alable.",
										"warning");
					} else {
						resetTournee();
						interfacePlanning.calculTournee();
						interfaceView.getVuePanel().getVue_tournee()
								.setTournee(interfacePlanning.getTournee());
						tourneeCalculed = true;

						// System.out.println("Tournee : " +
						// interfacePlanning.getTournee().toString());
						interfaceView.repaint();
					}
				} else if (name.equals("undo")) {
					if (!undoRedo.Undo()) {
						interfaceView.displayAlert(WARNING_UNDO,
								"Rien � annuler", "info");
					}
					interfaceView.getVuePanel().getVue_tournee()
							.setTournee(interfacePlanning.getTournee());
					interfaceView.repaint();
				} else if (name.equals("redo")) {
					if (!undoRedo.Redo()) {
						interfaceView.displayAlert(WARNING_REDO,
								"Rien � r�tablir", "info");
					}
					interfaceView.getVuePanel().getVue_tournee()
							.setTournee(interfacePlanning.getTournee());
					interfaceView.repaint();
				} else if (name.equals("generateInstructions")) {
					if (!tourneeCalculed) {
						interfaceView
								.displayAlert(
										ERROR_INSTRUC,
										"Impossible de g�n�ner le fichier d'instructions avant le calcul d'une tourn�e",
										"warning");
						return;
					}
					generateInstructions();
				}

			} else if (action.equals("delete_noeud")) {

				if (!tourneeCalculed) {
					interfaceView
							.displayAlert(
									ERROR_DELIVERY_MOD,
									"Vous devez calculer une tourn�e avant de modifier des livraisons.",
									"warning");
					return;
				}
				int idNoeud = Integer.parseInt(name);
				boolean deleted = deleteNoeud(idNoeud);
				if (deleted) {
					return;
				}
			}
		}
	}

	private void resetTournee() {
		if (tourneeCalculed) {
			interfacePlanning.resetTournee();
			interfaceView.getVuePanel().resetTournee();
			tourneeCalculed = false;
		}
	}

	private void resetPlan() {
		if (mapLoaded) {
			interfaceAgglo.resetPlan();
			interfaceView.getVuePanel().resetPlan();
			mapLoaded = false;
		}
	}

	private void resetLivraisons() {
		if (livraisonsLoaded) {
			interfacePlanning.resetLivraisons();
			interfaceView.getVuePanel().resetLivraisons();
			interfaceView.resetShippmentTable();
			livraisonsLoaded = false;
		}
	}

	private boolean deleteNoeud(int idNoeud) {
		boolean isEntrepot = interfacePlanning.isNodeEntrepot(idNoeud);

		if (!isEntrepot && interfacePlanning.isNodeADelivery(idNoeud)) {
			boolean suppr = interfaceView.confirmUserInput("Suppression",
					"Supprimer cette livraison ? ");
			if (suppr) {
				undoRedo.InsertRemoveCmd(idNoeud);
				interfaceView.getVuePanel().getVue_tournee()
						.setTournee(interfacePlanning.getTournee());
				interfaceView.repaint();
				return true;
			}
			return false;
		}

		if (isEntrepot) {
			interfaceView.displayAlert("Supprimer une livraison",
					"Vous ne pouvez pas supprimer l'entrepot", "warning");
		}
		return false;
	}

	private void generateInstructions() {
		String instructions = interfacePlanning.getTournee().toString();
		File file = new File("../Instructions/Instructions.txt");
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file, false));
			out.append(instructions);
			interfaceView.displayAlert(
					"Succ�s",
					"Instructions charg�es dans le fichier "
							+ file.getAbsolutePath() + ".", "info");
		} catch (Exception e) {
			interfaceView.displayAlert("Echec",
					"Impossible de charger les instructions dans le fichier "
							+ file.getAbsolutePath() + ".", "error");
		} finally {
			try {
				out.close();
			} catch (Exception e) {

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public InterfaceAgglo getReferenceToInterfaceAgglo() {
		return interfaceAgglo;
	}

	// ------------------------------------------------------------------
	// GETTERS - SETTERS
	public InterfaceAgglo getInterfaceAgglo() {
		return interfaceAgglo;
	}

	public void setInterfaceAgglo(InterfaceAgglo interfaceAgglo) {
		this.interfaceAgglo = interfaceAgglo;
	}

	public InterfacePlanning getInterfacePlanning() {
		return interfacePlanning;
	}

	public void setInterfacePlanning(InterfacePlanning interfacePlanning) {
		this.interfacePlanning = interfacePlanning;
	}

	public void setUndoRedo() {
		undoRedo = new UndoRedo(interfacePlanning, interfaceView);
	}

	public UndoRedo getUndoRedo() {
		return undoRedo;
	}

	public void setUndoRedo(UndoRedo undoRedo) {
		this.undoRedo = undoRedo;
	}

	public InterfaceView getInterfaceView() {
		return interfaceView;
	}

	public void setInterfaceView(InterfaceView interfaceView) {
		this.interfaceView = interfaceView;
	}

}
