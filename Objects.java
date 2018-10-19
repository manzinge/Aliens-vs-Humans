public class Objects {
	class Unit {
		protected
			boolean hasResource = false;	//Indicates if Unit has resource or not
			int health;						//Saves the health of the unit
			int strength;					//The strength of the unit
			int radius;						//How far the Unit can move
			void takeDamage(int dmg) {		//If the unit should take damage(fight) call this function with the strength of the opponent
			health = health-dmg;
			}
	}
	class Human extends Unit {
		int health=3;
		int strenght = 1;
		void heal() {						//Only humans can heal in the zone in the back?
			health++;
		}
		void gatherResource() {
			hasResource = true;
			radius--;
		}
		void dropResource() {
			hasResource = false;
			radius++;
		}
	}
	class Alien extends Unit {
		int health = 4;
		int strenght = 1;
		void gatherResource() {
			hasResource = true;
			radius--;
		}
		void consumeResource() {
			hasResource = false;
			radius++;
		}
	}
}
