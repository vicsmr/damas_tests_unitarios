package es.urjccode.mastercloudapps.adcs.draughts;

import es.urjccode.mastercloudapps.adcs.draughts.controllers.AcceptController;
import es.urjccode.mastercloudapps.adcs.draughts.controllers.Logic;
import es.urjccode.mastercloudapps.adcs.draughts.views.View;

public class Draughts {
	
	private Logic logic;
	
	private View view;
	
	protected Draughts() {
		this.logic = new Logic();
		this.view = new View();
	}

	protected void play() {
		AcceptController acceptController;
		do {
			acceptController = this.logic.getController();
			if (acceptController != null) {
				this.view.interact(acceptController);
			}
		} while (acceptController != null); 
	}
	
	public static void main(String[] args) {
		new Draughts().play();
	}

}
