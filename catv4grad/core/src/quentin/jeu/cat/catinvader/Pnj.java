package quentin.jeu.cat.catinvader;

import static quentin.jeu.cat.catinvader.Model.*;

import com.badlogic.gdx.math.MathUtils;

import quentin.jeu.cat.catinvader.Model.State;

/** The model class for the player. */
public class Pnj extends Character {
	//level speed
	static float ScrollVelocityX = 6f;
	
	//graphic offset shoot
	static float shootOffsetX =100, shootOffsetY = 20;
	
	//ship    characteristics 
	static float width = 90 * scale, height = 30 * scale;
	public static float hpStart = 4;
	static float playerAcceleration=10f;
	static float playerMaxvely=5f;
	static float inertia = 0.95f;
	
	//mship    characteristics 
	static float mwidth = 90 * scale, mheight = 30 * scale;

	//weapons characteristics
	static float bulletSpeed = 33, bulletInheritVelocity = 0.4f, burstDuration = 0.2f;
	public static float heatperShot = 1;

	static float minprecisionheated = 70;

	static float knockbackX = 14, knockbackY = 5, collisionDelay = 2.5f, flashTime = 0.07f;
	static float headBounceX = 12, headBounceY = 20;

	
	pnjType type;
	float shootTimer;
	float collisionTimer;
	float hpTimer;
	float deathTimer = 2;
	boolean exploded;
	
	public static float shootpsec     = 0.6f;
	public static float maxShots2heat = 10000;
	public static float weaponpower   = 1;

	// This is here for convenience, the model should never touch the view.
	public Pnjview view;

	public Pnj (Model model, pnjType type) {
		super(model);
		this.type = type;
		shootpsec     = 5;
		weaponpower   = 1;
		exploded=false;
		if(type==pnjType.kuro){
			rect.width = width;
			rect.height = height;
			hp=8;
		}else if(type==pnjType.mship){
			rect.width = 7;
			rect.height = 14;
			hp=4;
		}
		acceleration=playerAcceleration;
		maxVelocityX = ScrollVelocityX ;
		maxVelocityY = playerMaxvely;
	}

	void update (float delta) {
		//stay in world
		
		if(position.y> worldheight-height-1){
			velocity.y = Math.max(velocity.y - 2 * acceleration * delta, -maxVelocityY);
			//position.y= worldheight-height-0.4f;
		}
		if(position.y<-worldheight+1){
			velocity.y = Math.min(velocity.y + 2 * acceleration * delta, maxVelocityY);
			//position.y=-worldheight+0.2f;
		}
		if(type==pnjType.kuro){
			if (state == State.death)
				deathTimer -= delta;
			else if (collisionTimer < 0) {
				moveRight(delta);
				if (model.player.position.y > position.y+MathUtils.random(-acceleration/6, acceleration/3)) {
					 moveup(delta);
				} 
				else 
					movedown(delta);
			}
		}
		if(type==pnjType.mship){
			if (state == State.death)
				deathTimer -= delta;
			else if (collisionTimer < 0) {
				moveRight(delta);
			}
		}
		shootTimer -= delta;
		moveRight(delta);
		collisionTimer -= delta;
		//rect.height = height - collisionOffsetY;
		super.update(delta, true);
	}
	enum pnjType {
		kuro, mship
	}
}
