/*
 * Created on Jan 21, 2005
 *
 */
package elc;
/**
 * @author frak
 *
 *   
 */
public class Actor {
	/** < The actor ID from the server */
    int actor_id;

    /** < Specifies the type of actor (race, sex etc.) */
    int actor_type;

    /** < Specifies the x position of the actor */
    double x_pos;

    /** < Specifies the y position of the actor */
    double y_pos;

    /** < Specifies the z position of the actor */
    double z_pos;
    
    /** < Specifies the x tile position - updated in the timer thread */
    int x_tile_pos;

    /** < Specifies the y tile position - updated in the timer thread \n */
    int y_tile_pos;
    
    /** < Sets the current x rotation */
    float x_rot;

    /** < Sets the current y rotation */
    float y_rot;

    /** < Sets the current z rotation */
    float z_rot;
    
    /** < Sets the boots ID (loaded from the actor_defs array) */
    int boots;

    /** < Sets the hair ID (loaded from the actor_defs array) */
    int hair;

    /** < Sets the skin ID (loaded from the actor_defs array) */
    int skin;

    /** < Sets the pants ID (loaded from the actor_defs array) */
    int pants;

    /** < Sets the shirt ID (loaded from the actor_defs array) */
    int shirt;

    /** < Sets the current weapon of the actor */
    int cur_weapon;
    
    /** < Specifies if we have the enhanced_actor structure below */
    int is_enhanced_model;
    
    /** < Sets the current frame name that will be rendered */
    String cur_frame;
    
    /**
     * < If the actors colours are remapped it will holds the texture in
     * actor->texture_id
     */
    char remapped_colors;

    /**
     * < Sets the texture ID, if the remapped_colors==1 - remember to
     * glDeleteTextures
     */
    int texture_id;

    /** < Sets the skin name */
    String skin_name;

    /**
     * < Sets the actors name - holds the guild name as well after a special
     * 127+color character
     */
    String actor_name;
    
    /** < Holds the current command queue */
    String que;

    /** < Holds the last command */
    char last_command;

    /** < if the actor is busy executing the current command */
    char busy;

    /** < Specifies if the actor is currently sitting */
    char sitting;

    /** < Specifies if the actor is currently fighting */
    char fighting;
    
    /**
     * < Sets the current movement speed in the x direction (used for updating
     * the actor in the timer thread)
     */
    double move_x_speed;

    /**
     * < Sets the current movement speed in the y direction (used for updating
     * the actor in the timer thread)
     */
    double move_y_speed;

    /**
     * < Sets the current movement speed in the z direction (used for updating
     * the actor in the timer thread)
     */
    double move_z_speed;

    /**
     * < Specifies how many movement frames the actor has to do before it goes
     * idle
     */
    int movement_frames_left;

    /**
     * < Sets the x rotation speed (used for updating the actor in the timer
     * thread)
     */
    float rotate_x_speed;

    /**
     * < Sets the y rotation speed (used for updating the actor in the timer
     * thread)
     */
    float rotate_y_speed;

    /**
     * < Sets the z rotation speed (used for updating the actor in the timer
     * thread)
     */
    float rotate_z_speed;

    /** < Specifies how many rotation frames it needs to do */
    int rotate_frames_left;

    /**
     * < When the actor is done moving, it does a small animation before idleing -
     * specifies how many frames it needs to render of that animation
     */
    int after_move_frames_left;
    
    /** < Specifies if the actor is currently on the move */
    char moving;

    /** < Specifies if the actor is currently rotating */
    char rotating;

    /** < Don't loop trough the current animation (like for die, jump, etc.) */
    char stop_animation;

    /** < Sets the actor in an idle stand position */
    char stand_idle;

    /** < Sets the actor in an idle sit position */
    char sit_idle;

    /** < Used when the actor is dead (render the dead position) */
    char dead;

    /** < Sets the damage the actor has been given */
    int damage;

    /** < Defines the remaining time in which the actor damage will be shown */
    int damage_ms;

    /** < Sets the current health of the actor */
    int cur_health;

    /** < Sets the maximum health of the actor */
    int max_health;

    /**
     * < Sets the actor type to ghost (Disable lightning, enable blending
     * (GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA))
     */
    char ghost;

    /**
     * < Defines the kind_of_actor (NPC, HUMAN, COMPUTER_CONTROLLED_HUMAN,
     * PKABLE, PKABLE_COMPUTER_CONTROLLED)
     */
    int kind_of_actor;
    
    /**
     * < If the text is displayed in a bubble over the actor, this holds the
     * text
     */
    String current_displayed_text;

    /** < Defines the remaining time the overhead text should be displayed */
    int current_displayed_text_time_left;

    
    /** < Unused? */
    double x_speed;

    /** < Unused? */
    double y_speed;

    /** < Unused? */
    double z_speed;
    
    Actor() {
        this.cur_frame = "";
        this.skin_name = "";
        this.actor_name = "";
        this.que = "";
    }

    /**
     * @param p
     * Add an actor from server (ADD_NEW_ACTOR packet).
     */
    Actor(Packet p) {
        // TODO read _all_ the data from Packet p
        
        this.cur_frame = "";
        this.skin_name = "";
        byte[] tmp=p.data.array();
        char[] chars=new char[30];
        int count=30;
        for (int i=0;i<30;i++){
        	if(tmp[i+23]!=0){
        		chars[i]=(char)tmp[i+23];
        	}
        	else{
        		chars[i]='\0';
        		count=i;
        		break;
        	}
        }        
        this.actor_name = new String(chars,0,count);
        
        
        this.que = "";
        
        this.actor_id =p.data.getShort();
        this.x_pos = p.data.getShort(2);
        this.y_pos = p.data.getShort(4);
        this.z_pos = p.data.getShort(6);
        this.z_rot = p.data.getShort(8);
        this.actor_type = p.data.getShort(10);
        this.skin = p.data.get(12);
        this.hair = p.data.get(13);
        this.shirt = p.data.get(14);
        this.pants = p.data.get(15);
        this.boots = p.data.get(16);
        this.max_health = p.data.getShort(18);
        this.cur_health = p.data.getShort(20);
        this.kind_of_actor = p.data.get(22);
    }
    /**
     * @param p
     * Add an actor from server (ADD_NEW_ACTOR packet).
     */
    /*
     * for debug purposes
     */
    public String toString(){
    	return actor_name;
    }
    
    public String dump(){
    	return "Actor:"+actor_name+" id"+actor_id+" loc"+x_pos+" / "+y_pos+" / "+z_pos;
    }
	/**
	 * @return Returns the actor_id.
	 */
	public int getActor_id() {
		return actor_id;
	}
	/**
	 * @param actor_id The actor_id to set.
	 */
	public void setActor_id(int actor_id) {
		this.actor_id = actor_id;
	}
	/**
	 * @return Returns the actor_name.
	 */
	public String getActor_name() {
		return actor_name;
	}
	/**
	 * @param actor_name The actor_name to set.
	 */
	public void setActor_name(String actor_name) {
		this.actor_name = actor_name;
	}
	/**
	 * @return Returns the actor_type.
	 */
	public int getActor_type() {
		return actor_type;
	}
	/**
	 * @param actor_type The actor_type to set.
	 */
	public void setActor_type(int actor_type) {
		this.actor_type = actor_type;
	}
	/**
	 * @return Returns the after_move_frames_left.
	 */
	public int getAfter_move_frames_left() {
		return after_move_frames_left;
	}
	/**
	 * @param after_move_frames_left The after_move_frames_left to set.
	 */
	public void setAfter_move_frames_left(int after_move_frames_left) {
		this.after_move_frames_left = after_move_frames_left;
	}
	/**
	 * @return Returns the boots.
	 */
	public int getBoots() {
		return boots;
	}
	/**
	 * @param boots The boots to set.
	 */
	public void setBoots(int boots) {
		this.boots = boots;
	}
	/**
	 * @return Returns the busy.
	 */
	public char getBusy() {
		return busy;
	}
	/**
	 * @param busy The busy to set.
	 */
	public void setBusy(char busy) {
		this.busy = busy;
	}
	/**
	 * @return Returns the cur_frame.
	 */
	public String getCur_frame() {
		return cur_frame;
	}
	/**
	 * @param cur_frame The cur_frame to set.
	 */
	public void setCur_frame(String cur_frame) {
		this.cur_frame = cur_frame;
	}
	/**
	 * @return Returns the cur_health.
	 */
	public int getCur_health() {
		return cur_health;
	}
	/**
	 * @param cur_health The cur_health to set.
	 */
	public void setCur_health(int cur_health) {
		this.cur_health = cur_health;
	}
	/**
	 * @return Returns the cur_weapon.
	 */
	public int getCur_weapon() {
		return cur_weapon;
	}
	/**
	 * @param cur_weapon The cur_weapon to set.
	 */
	public void setCur_weapon(int cur_weapon) {
		this.cur_weapon = cur_weapon;
	}
	/**
	 * @return Returns the current_displayed_text.
	 */
	public String getCurrent_displayed_text() {
		return current_displayed_text;
	}
	/**
	 * @param current_displayed_text The current_displayed_text to set.
	 */
	public void setCurrent_displayed_text(String current_displayed_text) {
		this.current_displayed_text = current_displayed_text;
	}
	/**
	 * @return Returns the current_displayed_text_time_left.
	 */
	public int getCurrent_displayed_text_time_left() {
		return current_displayed_text_time_left;
	}
	/**
	 * @param current_displayed_text_time_left The current_displayed_text_time_left to set.
	 */
	public void setCurrent_displayed_text_time_left(
			int current_displayed_text_time_left) {
		this.current_displayed_text_time_left = current_displayed_text_time_left;
	}
	/**
	 * @return Returns the damage.
	 */
	public int getDamage() {
		return damage;
	}
	/**
	 * @param damage The damage to set.
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	/**
	 * @return Returns the damage_ms.
	 */
	public int getDamage_ms() {
		return damage_ms;
	}
	/**
	 * @param damage_ms The damage_ms to set.
	 */
	public void setDamage_ms(int damage_ms) {
		this.damage_ms = damage_ms;
	}
	/**
	 * @return Returns the dead.
	 */
	public char getDead() {
		return dead;
	}
	/**
	 * @param dead The dead to set.
	 */
	public void setDead(char dead) {
		this.dead = dead;
	}
	/**
	 * @return Returns the fighting.
	 */
	public char getFighting() {
		return fighting;
	}
	/**
	 * @param fighting The fighting to set.
	 */
	public void setFighting(char fighting) {
		this.fighting = fighting;
	}
	/**
	 * @return Returns the ghost.
	 */
	public char getGhost() {
		return ghost;
	}
	/**
	 * @param ghost The ghost to set.
	 */
	public void setGhost(char ghost) {
		this.ghost = ghost;
	}
	/**
	 * @return Returns the hair.
	 */
	public int getHair() {
		return hair;
	}
	/**
	 * @param hair The hair to set.
	 */
	public void setHair(int hair) {
		this.hair = hair;
	}
	/**
	 * @return Returns the is_enhanced_model.
	 */
	public int getIs_enhanced_model() {
		return is_enhanced_model;
	}
	/**
	 * @param is_enhanced_model The is_enhanced_model to set.
	 */
	public void setIs_enhanced_model(int is_enhanced_model) {
		this.is_enhanced_model = is_enhanced_model;
	}
	/**
	 * @return Returns the kind_of_actor.
	 */
	public int getKind_of_actor() {
		return kind_of_actor;
	}
	/**
	 * @param kind_of_actor The kind_of_actor to set.
	 */
	public void setKind_of_actor(int kind_of_actor) {
		this.kind_of_actor = kind_of_actor;
	}
	/**
	 * @return Returns the last_command.
	 */
	public char getLast_command() {
		return last_command;
	}
	/**
	 * @param last_command The last_command to set.
	 */
	public void setLast_command(char last_command) {
		this.last_command = last_command;
	}
	/**
	 * @return Returns the max_health.
	 */
	public int getMax_health() {
		return max_health;
	}
	/**
	 * @param max_health The max_health to set.
	 */
	public void setMax_health(int max_health) {
		this.max_health = max_health;
	}
	/**
	 * @return Returns the move_x_speed.
	 */
	public double getMove_x_speed() {
		return move_x_speed;
	}
	/**
	 * @param move_x_speed The move_x_speed to set.
	 */
	public void setMove_x_speed(double move_x_speed) {
		this.move_x_speed = move_x_speed;
	}
	/**
	 * @return Returns the move_y_speed.
	 */
	public double getMove_y_speed() {
		return move_y_speed;
	}
	/**
	 * @param move_y_speed The move_y_speed to set.
	 */
	public void setMove_y_speed(double move_y_speed) {
		this.move_y_speed = move_y_speed;
	}
	/**
	 * @return Returns the move_z_speed.
	 */
	public double getMove_z_speed() {
		return move_z_speed;
	}
	/**
	 * @param move_z_speed The move_z_speed to set.
	 */
	public void setMove_z_speed(double move_z_speed) {
		this.move_z_speed = move_z_speed;
	}
	/**
	 * @return Returns the movement_frames_left.
	 */
	public int getMovement_frames_left() {
		return movement_frames_left;
	}
	/**
	 * @param movement_frames_left The movement_frames_left to set.
	 */
	public void setMovement_frames_left(int movement_frames_left) {
		this.movement_frames_left = movement_frames_left;
	}
	/**
	 * @return Returns the moving.
	 */
	public char getMoving() {
		return moving;
	}
	/**
	 * @param moving The moving to set.
	 */
	public void setMoving(char moving) {
		this.moving = moving;
	}
	/**
	 * @return Returns the pants.
	 */
	public int getPants() {
		return pants;
	}
	/**
	 * @param pants The pants to set.
	 */
	public void setPants(int pants) {
		this.pants = pants;
	}
	/**
	 * @return Returns the que.
	 */
	public String getQue() {
		return que;
	}
	/**
	 * @param que The que to set.
	 */
	public void setQue(String que) {
		this.que = que;
	}
	/**
	 * @return Returns the remapped_colors.
	 */
	public char getRemapped_colors() {
		return remapped_colors;
	}
	/**
	 * @param remapped_colors The remapped_colors to set.
	 */
	public void setRemapped_colors(char remapped_colors) {
		this.remapped_colors = remapped_colors;
	}
	/**
	 * @return Returns the rotate_frames_left.
	 */
	public int getRotate_frames_left() {
		return rotate_frames_left;
	}
	/**
	 * @param rotate_frames_left The rotate_frames_left to set.
	 */
	public void setRotate_frames_left(int rotate_frames_left) {
		this.rotate_frames_left = rotate_frames_left;
	}
	/**
	 * @return Returns the rotate_x_speed.
	 */
	public float getRotate_x_speed() {
		return rotate_x_speed;
	}
	/**
	 * @param rotate_x_speed The rotate_x_speed to set.
	 */
	public void setRotate_x_speed(float rotate_x_speed) {
		this.rotate_x_speed = rotate_x_speed;
	}
	/**
	 * @return Returns the rotate_y_speed.
	 */
	public float getRotate_y_speed() {
		return rotate_y_speed;
	}
	/**
	 * @param rotate_y_speed The rotate_y_speed to set.
	 */
	public void setRotate_y_speed(float rotate_y_speed) {
		this.rotate_y_speed = rotate_y_speed;
	}
	/**
	 * @return Returns the rotate_z_speed.
	 */
	public float getRotate_z_speed() {
		return rotate_z_speed;
	}
	/**
	 * @param rotate_z_speed The rotate_z_speed to set.
	 */
	public void setRotate_z_speed(float rotate_z_speed) {
		this.rotate_z_speed = rotate_z_speed;
	}
	/**
	 * @return Returns the rotating.
	 */
	public char getRotating() {
		return rotating;
	}
	/**
	 * @param rotating The rotating to set.
	 */
	public void setRotating(char rotating) {
		this.rotating = rotating;
	}
	/**
	 * @return Returns the shirt.
	 */
	public int getShirt() {
		return shirt;
	}
	/**
	 * @param shirt The shirt to set.
	 */
	public void setShirt(int shirt) {
		this.shirt = shirt;
	}
	/**
	 * @return Returns the sit_idle.
	 */
	public char getSit_idle() {
		return sit_idle;
	}
	/**
	 * @param sit_idle The sit_idle to set.
	 */
	public void setSit_idle(char sit_idle) {
		this.sit_idle = sit_idle;
	}
	/**
	 * @return Returns the sitting.
	 */
	public char getSitting() {
		return sitting;
	}
	/**
	 * @param sitting The sitting to set.
	 */
	public void setSitting(char sitting) {
		this.sitting = sitting;
	}
	/**
	 * @return Returns the skin.
	 */
	public int getSkin() {
		return skin;
	}
	/**
	 * @param skin The skin to set.
	 */
	public void setSkin(int skin) {
		this.skin = skin;
	}
	/**
	 * @return Returns the skin_name.
	 */
	public String getSkin_name() {
		return skin_name;
	}
	/**
	 * @param skin_name The skin_name to set.
	 */
	public void setSkin_name(String skin_name) {
		this.skin_name = skin_name;
	}
	/**
	 * @return Returns the stand_idle.
	 */
	public char getStand_idle() {
		return stand_idle;
	}
	/**
	 * @param stand_idle The stand_idle to set.
	 */
	public void setStand_idle(char stand_idle) {
		this.stand_idle = stand_idle;
	}
	/**
	 * @return Returns the stop_animation.
	 */
	public char getStop_animation() {
		return stop_animation;
	}
	/**
	 * @param stop_animation The stop_animation to set.
	 */
	public void setStop_animation(char stop_animation) {
		this.stop_animation = stop_animation;
	}
	/**
	 * @return Returns the texture_id.
	 */
	public int getTexture_id() {
		return texture_id;
	}
	/**
	 * @param texture_id The texture_id to set.
	 */
	public void setTexture_id(int texture_id) {
		this.texture_id = texture_id;
	}
	/**
	 * @return Returns the x_pos.
	 */
	public double getX_pos() {
		return x_pos;
	}
	/**
	 * @param x_pos The x_pos to set.
	 */
	public void setX_pos(double x_pos) {
		this.x_pos = x_pos;
	}
	/**
	 * @return Returns the x_rot.
	 */
	public float getX_rot() {
		return x_rot;
	}
	/**
	 * @param x_rot The x_rot to set.
	 */
	public void setX_rot(float x_rot) {
		this.x_rot = x_rot;
	}
	/**
	 * @return Returns the x_speed.
	 */
	public double getX_speed() {
		return x_speed;
	}
	/**
	 * @param x_speed The x_speed to set.
	 */
	public void setX_speed(double x_speed) {
		this.x_speed = x_speed;
	}
	/**
	 * @return Returns the x_tile_pos.
	 */
	public int getX_tile_pos() {
		return x_tile_pos;
	}
	/**
	 * @param x_tile_pos The x_tile_pos to set.
	 */
	public void setX_tile_pos(int x_tile_pos) {
		this.x_tile_pos = x_tile_pos;
	}
	/**
	 * @return Returns the y_pos.
	 */
	public double getY_pos() {
		return y_pos;
	}
	/**
	 * @param y_pos The y_pos to set.
	 */
	public void setY_pos(double y_pos) {
		this.y_pos = y_pos;
	}
	/**
	 * @return Returns the y_rot.
	 */
	public float getY_rot() {
		return y_rot;
	}
	/**
	 * @param y_rot The y_rot to set.
	 */
	public void setY_rot(float y_rot) {
		this.y_rot = y_rot;
	}
	/**
	 * @return Returns the y_speed.
	 */
	public double getY_speed() {
		return y_speed;
	}
	/**
	 * @param y_speed The y_speed to set.
	 */
	public void setY_speed(double y_speed) {
		this.y_speed = y_speed;
	}
	/**
	 * @return Returns the y_tile_pos.
	 */
	public int getY_tile_pos() {
		return y_tile_pos;
	}
	/**
	 * @param y_tile_pos The y_tile_pos to set.
	 */
	public void setY_tile_pos(int y_tile_pos) {
		this.y_tile_pos = y_tile_pos;
	}
	/**
	 * @return Returns the z_pos.
	 */
	public double getZ_pos() {
		return z_pos;
	}
	/**
	 * @param z_pos The z_pos to set.
	 */
	public void setZ_pos(double z_pos) {
		this.z_pos = z_pos;
	}
	/**
	 * @return Returns the z_rot.
	 */
	public float getZ_rot() {
		return z_rot;
	}
	/**
	 * @param z_rot The z_rot to set.
	 */
	public void setZ_rot(float z_rot) {
		this.z_rot = z_rot;
	}
	/**
	 * @return Returns the z_speed.
	 */
	public double getZ_speed() {
		return z_speed;
	}
	/**
	 * @param z_speed The z_speed to set.
	 */
	public void setZ_speed(double z_speed) {
		this.z_speed = z_speed;
	}
	public boolean isEnhanced(){
		return false;
	}
	/**
	 * 
	 * @return  returns the name (ignoreing guild if applicable)
	 */
	public String getActorStraightName(){
		int index=this.actor_name.indexOf(" ");
		if(index!=-1){
			return actor_name.substring(0,index);
		}
		else{
			return actor_name;
		}
	}
	/**`
	 * 
	 * 
	 * @return returns the guild (if applicable)
	 */
	public String getActorGuild(){
		int index=this.actor_name.indexOf(" ");
		if(index!=-1){
			return actor_name.substring(index+2,actor_name.length());
		}
		else{
			return "";
		}
	}
}

