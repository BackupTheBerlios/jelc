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
    // TODO move the comments.
    int actor_id;

    /** < The actor ID from the server */
    int actor_type;

    /** < Specifies the type of actor (race, sex etc.) */

    double x_pos;

    /** < Specifies the x position of the actor */
    double y_pos;

    /** < Specifies the y position of the actor */
    double z_pos;

    /** < Specifies the z position of the actor */

    int x_tile_pos;

    /** < Specifies the x tile position - updated in the timer thread */
    int y_tile_pos;

    /** < Specifies the y tile position - updated in the timer thread \n */

    float x_rot;

    /** < Sets the current x rotation */
    float y_rot;

    /** < Sets the current y rotation */
    float z_rot;

    /** < Sets the current z rotation */

    int boots;

    /** < Sets the boots ID (loaded from the actor_defs array) */
    int hair;

    /** < Sets the hair ID (loaded from the actor_defs array) */
    int skin;

    /** < Sets the skin ID (loaded from the actor_defs array) */
    int pants;

    /** < Sets the pants ID (loaded from the actor_defs array) */
    int shirt;

    /** < Sets the shirt ID (loaded from the actor_defs array) */
    int cur_weapon;

    /** < Sets the current weapon of the actor */

    int is_enhanced_model;

    /** < Specifies if we have the enhanced_actor structure below */

    String cur_frame;

    /** < Sets the current frame name that will be rendered */

    char remapped_colors;

    /**
     * < If the actors colours are remapped it will holds the texture in
     * actor->texture_id
     */
    int texture_id;

    /**
     * < Sets the texture ID, if the remapped_colors==1 - remember to
     * glDeleteTextures
     */
    String skin_name;

    /** < Sets the skin name */
    String actor_name;

    /**
     * < Sets the actors name - holds the guild name as well after a special
     * 127+color character
     */

    String que;

    /** < Holds the current command queue */
    char last_command;

    /** < Holds the last command */
    char busy;

    /** < if the actor is busy executing the current command */
    char sitting;

    /** < Specifies if the actor is currently sitting */
    char fighting;

    /** < Specifies if the actor is currently fighting */

    double move_x_speed;

    /**
     * < Sets the current movement speed in the x direction (used for updating
     * the actor in the timer thread)
     */
    double move_y_speed;

    /**
     * < Sets the current movement speed in the y direction (used for updating
     * the actor in the timer thread)
     */
    double move_z_speed;

    /**
     * < Sets the current movement speed in the z direction (used for updating
     * the actor in the timer thread)
     */
    int movement_frames_left;

    /**
     * < Specifies how many movement frames the actor has to do before it goes
     * idle
     */
    float rotate_x_speed;

    /**
     * < Sets the x rotation speed (used for updating the actor in the timer
     * thread)
     */
    float rotate_y_speed;

    /**
     * < Sets the y rotation speed (used for updating the actor in the timer
     * thread)
     */
    float rotate_z_speed;

    /**
     * < Sets the z rotation speed (used for updating the actor in the timer
     * thread)
     */
    int rotate_frames_left;

    /** < Specifies how many rotation frames it needs to do */
    int after_move_frames_left;

    /**
     * < When the actor is done moving, it does a small animation before idleing -
     * specifies how many frames it needs to render of that animation
     */

    char moving;

    /** < Specifies if the actor is currently on the move */
    char rotating;

    /** < Specifies if the actor is currently rotating */
    char stop_animation;

    /** < Don't loop trough the current animation (like for die, jump, etc.) */
    char stand_idle;

    /** < Sets the actor in an idle stand position */
    char sit_idle;

    /** < Sets the actor in an idle sit position */
    char dead;

    /** < Used when the actor is dead (render the dead position) */
    int damage;

    /** < Sets the damage the actor has been given */
    int damage_ms;

    /** < Defines the remaining time in which the actor damage will be shown */
    int cur_health;

    /** < Sets the current health of the actor */
    int max_health;

    /** < Sets the maximum health of the actor */
    char ghost;

    /**
     * < Sets the actor type to ghost (Disable lightning, enable blending
     * (GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA))
     */
    int kind_of_actor;

    /**
     * < Defines the kind_of_actor (NPC, HUMAN, COMPUTER_CONTROLLED_HUMAN,
     * PKABLE, PKABLE_COMPUTER_CONTROLLED)
     */

    String current_displayed_text;

    /**
     * < If the text is displayed in a bubble over the actor, this holds the
     * text
     */
    int current_displayed_text_time_left;

    /** < Defines the remaining time the overhead text should be displayed */

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

