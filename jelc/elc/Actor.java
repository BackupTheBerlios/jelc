/*
 * Created on Jan 21, 2005
 *
 */
package elc;

/**
 * @author frak
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
        this.actor_name = new String(p.data.array(), 3+23, 30);
        this.que = "";
        
        this.actor_id = p.data.getShort(3);
        this.x_pos = p.data.getShort(3+2);
        this.y_pos = p.data.getShort(3+4);
        this.z_pos = p.data.getShort(3+6);
        this.z_rot = p.data.getShort(3+8);
        this.actor_type = p.data.getShort(3+10);
        this.skin = p.data.get(3+12);
        this.hair = p.data.get(3+13);
        this.shirt = p.data.get(3+14);
        this.pants = p.data.get(3+15);
        this.boots = p.data.get(3+16);
        this.max_health = p.data.getShort(3+18);
        this.cur_health = p.data.getShort(3+20);
        this.kind_of_actor = p.data.get(3+22);
    }
}

