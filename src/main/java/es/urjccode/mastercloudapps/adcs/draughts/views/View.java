package es.urjccode.mastercloudapps.adcs.draughts.views;

import es.urjccode.mastercloudapps.adcs.draughts.controllers.AcceptController;
import es.urjccode.mastercloudapps.adcs.draughts.controllers.ControllersVisitor;
import es.urjccode.mastercloudapps.adcs.draughts.controllers.PlayController;
import es.urjccode.mastercloudapps.adcs.draughts.controllers.ResumeController;
import es.urjccode.mastercloudapps.adcs.draughts.controllers.StartController;

public class View implements ControllersVisitor {
	
	private StartView startView;

	private CommandView commandView;

	private ResumeView resumeView;

	public View() {
		this.startView = new StartView();
		this.commandView = new CommandView();
		this.resumeView = new ResumeView();
	}

	public void interact(AcceptController acceptController) {
		acceptController.accept(this);
	}

	@Override
	public void visit(StartController startController) {
		this.startView.interact(startController);
	}

	@Override
	public void visit(PlayController playController) {
		this.commandView.interact(playController);
	}

	@Override
	public void visit(ResumeController resumeController) {
		this.resumeView.interact(resumeController);
	}

}