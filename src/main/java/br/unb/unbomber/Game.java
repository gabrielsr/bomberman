package br.unb.unbomber;

public class Game {

	public void game() {


	}
	
	public void run(){
		
		boolean finished = false;

		showPresentation();
		showMenu();
		
		while (!finished) {
			getControls();
			update();
			render();
		}
	}

	private void showPresentation() {
		// TODO Auto-generated method stub
		
	}
	
	private void showMenu() {
		// TODO Auto-generated method stub
		
	}

	private void render() {
		// TODO Auto-generated method stub

	}

	private void update() {
		// TODO Auto-generated method stub

	}

	private void getControls() {
		// TODO Auto-generated method stub

	}
	
	public void pause(){
		
	}
	
	public void save(int slot){
		
	}
	
	public void load(int slot){
		
	}

}
