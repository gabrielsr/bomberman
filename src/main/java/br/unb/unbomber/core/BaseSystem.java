package br.unb.unbomber.core;


abstract public class BaseSystem implements System{

	private GameModel model;
	
	
	/*
	 * Init Game System
	 */
	
	/**
	 * Constructor that gets the default Game Model
	 */
	public BaseSystem(){
		model = GameModelImpl.getInstance();
	}

	/**
	* Constructor that gets a custom Game Model
	*/
	public BaseSystem(GameModel model){
		this.model = model;
	}
	
	protected GameModel getModel(){
		return model;
	}

	/*
	* System Life Cycle
	*/
	
	/**
	* Called when the system is started. If a system want to 
	* have a custom behavior it needs to override this method.
	*/
	public void start(){
	}

	/**
	* Called when the system is finished. If a system want to 
	* have a custom behavior it needs to override this method.
	*/	
	public void stop(){
	}
	
	/**
	* Called every turn. Every System has to implements this method.
	*/
	abstract public void update();


	
}
