/*
 * Created on Jan 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package elc;

/**
 * @author Administrator
 *
 * This class is a port of client_serv.h from elc
 */
public class Protocol {
	public static final byte human_female = 0;
	public static final byte human_male = 1;
	public static final byte elf_female = 2;
	public static final byte elf_male = 3;
	public static final byte dwarf_female = 4;
	public static final byte dwarf_male = 5;
	public static final byte wraith = 6;
	public static final byte cyclops = 7;
	public static final byte beaver = 8;
	public static final byte rat = 9;
	public static final byte goblin_male_2 = 10;
	public static final byte goblin_female_1 = 11;
	public static final byte town_folk4 = 12;
	public static final byte town_folk5 = 13;
	public static final byte shop_girl3 = 14;

	public static final byte deer = 15;
	public static final byte bear = 16;
	public static final byte wolf = 17;
	public static final byte white_rabbit = 18;
	public static final byte brown_rabbit = 19;
	public static final byte boar = 20;
	public static final byte bear2 = 21;
	public static final byte snake1 = 22;
	public static final byte snake2 = 23;
	public static final byte snake3 = 24;
	public static final byte fox = 25;
	public static final byte puma = 26;
	public static final byte ogre_male_1 = 27;
	public static final byte goblin_male_1 = 28;
	public static final byte orc_male_1 = 29;
	public static final byte orc_female_1 = 30;
	public static final byte skeleton = 31;
	public static final byte gargoyle1 = 32;
	public static final byte gargoyle2 = 33;
	public static final byte gargoyle3 = 34;
	public static final byte troll = 35;
	public static final byte chimeran_wolf_mountain = 36;
	public static final byte gnome_female = 37;
	public static final byte gnome_male = 38;
	public static final byte orchan_female = 39;
	public static final byte orchan_male = 40;
	public static final byte draegoni_female = 41;
	public static final byte draegoni_male = 42;

//	skin colors
	public static final byte SKIN_BROWN = 0;
	public static final byte SKIN_NORMAL = 1;
	public static final byte SKIN_PALE = 2;
	public static final byte SKIN_TAN = 3;

//	shirt colors
	public static final byte SHIRT_BLACK = 0;
	public static final byte SHIRT_BLUE = 1;
	public static final byte SHIRT_BROWN = 2;
	public static final byte SHIRT_GREY = 3;
	public static final byte SHIRT_GREEN = 4;
	public static final byte SHIRT_LIGHTBROWN = 5;
	public static final byte SHIRT_ORANGE = 6;
	public static final byte SHIRT_PINK = 7;
	public static final byte SHIRT_PURPLE = 8;
	public static final byte SHIRT_RED = 9;
	public static final byte SHIRT_WHITE = 10;
	public static final byte SHIRT_YELLOW = 11;
	public static final byte SHIRT_LEATHER_ARMOR = 12;
	public static final byte SHIRT_CHAIN_ARMOR = 13;
	public static final byte SHIRT_STEEL_CHAIN_ARMOR = 14;
	public static final byte SHIRT_TITANIUM_CHAIN_ARMOR = 15;
	public static final byte SHIRT_IRON_PLATE_ARMOR = 16;
	public static final byte SHIRT_ARMOR_6 = 17;
	public static final byte SHIRT_FUR = 18;

	public static final byte NO_BODY_ARMOR = 0;
	public static final byte NO_PANTS_ARMOR = 0;
	public static final byte NO_BOOTS_ARMOR = 0;

//	hair
	public static final byte HAIR_BLACK = 0;
	public static final byte HAIR_BLOND = 1;
	public static final byte HAIR_BROWN = 2;
	public static final byte HAIR_GRAY = 3;
	public static final byte HAIR_RED = 4;
	public static final byte HAIR_WHITE = 5;
	public static final byte HAIR_BLUE = 6;		// for Draegoni;
	public static final byte HAIR_GREEN = 7;	// for Draegoni;
	public static final byte HAIR_PURPLE = 8;	// for Draegoni;

//	boots color
	public static final byte BOOTS_BLACK = 0;
	public static final byte BOOTS_BROWN = 1;
	public static final byte BOOTS_DARKBROWN = 2;
	public static final byte BOOTS_DULLBROWN = 3;
	public static final byte BOOTS_LIGHTBROWN = 4;
	public static final byte BOOTS_ORANGE = 5;
	public static final byte BOOTS_LEATHER = 6;
	public static final byte BOOTS_FUR = 7;
	public static final byte BOOTS_IRON_GREAVE = 8;

//	pants
	public static final byte PANTS_BLACK = 0;
	public static final byte PANTS_BLUE = 1;
	public static final byte PANTS_BROWN = 2;
	public static final byte PANTS_DARKBROWN = 3;
	public static final byte PANTS_GREY = 4;
	public static final byte PANTS_GREEN = 5;
	public static final byte PANTS_LIGHTBROWN = 6;
	public static final byte PANTS_RED = 7;
	public static final byte PANTS_WHITE = 8;
	public static final byte PANTS_LEATHER = 9;
	public static final byte PANTS_IRON_CUISSES = 10;
	public static final byte PANTS_FUR = 11;

//	capes
	public static final byte CAPE_BLACK = 0;
	public static final byte CAPE_BLUE = 1;
	public static final byte CAPE_BLUEGRAY = 2;
	public static final byte CAPE_BROWN = 3;
	public static final byte CAPE_BROWNGRAY = 4;
	public static final byte CAPE_GRAY = 5;
	public static final byte CAPE_GREEN = 6;
	public static final byte CAPE_GREENGRAY = 7;
	public static final byte CAPE_PURPLE = 8;
	public static final byte CAPE_WHITE = 9;
	public static final byte CAPE_FUR = 10;
	public static final byte CAPE_GOLD = 11;
	public static final byte CAPE_RED = 12;
	public static final byte CAPE_ORANGE = 13;
	public static final byte CAPE_MOD = 14;
	public static final byte CAPE_MOONSHADOW = 15;
	public static final byte CAPE_RAVENOD = 16;
	public static final byte CAPE_ROGUE = 17;
	public static final byte CAPE_WYTTER = 18;
	public static final byte CAPE_QUELL = 19;
	public static final byte CAPE_NONE = 30;

//	heads
	public static final byte HEAD_1 = 0;
	public static final byte HEAD_2 = 1;
	public static final byte HEAD_3 = 2;
	public static final byte HEAD_4 = 3;
	public static final byte HEAD_5 = 4;

	public static final byte KIND_OF_WEAPON = 0;
	public static final byte KIND_OF_SHIELD = 1;
	public static final byte KIND_OF_CAPE = 2;
	public static final byte KIND_OF_HELMET = 3;
	public static final byte KIND_OF_LEG_ARMOR = 4;
	public static final byte KIND_OF_BODY_ARMOR = 5;
	public static final byte KIND_OF_BOOT_ARMOR = 6;

//	helmets
	public static final byte HELMET_IRON = 0;
	public static final byte HELMET_FUR = 1;
	public static final byte HELMET_LEATHER = 2;
	public static final byte HELMET_NONE = 20;

//	shields
	public static final byte SHIELD_WOOD = 0;
	public static final byte SHIELD_WOOD_ENHANCED = 1;
	public static final byte SHIELD_IRON = 2;
	public static final byte SHIELD_STEEL = 3;
	public static final byte SHIELD_NONE = 11;

//	weapons
	public static final byte WEAPON_NONE = 0;
	public static final byte SWORD_1 = 1;
	public static final byte SWORD_2 = 2;
	public static final byte SWORD_3 = 3;
	public static final byte SWORD_4 = 4;
	public static final byte SWORD_5 = 5;
	public static final byte SWORD_6 = 6;
	public static final byte SWORD_7 = 7;
	public static final byte STAFF_1 = 8;
	public static final byte STAFF_2 = 9;
	public static final byte STAFF_3 = 10;
	public static final byte STAFF_4 = 11;
	public static final byte HAMMER_1 = 12;
	public static final byte HAMMER_2 = 13;
	public static final byte PICKAX = 14;
	public static final byte SWORD_1_FIRE = 15;
	public static final byte SWORD_2_FIRE = 16;
	public static final byte SWORD_2_COLD = 17;
	public static final byte SWORD_3_FIRE = 18;
	public static final byte SWORD_3_COLD = 19;
	public static final byte SWORD_3_MAGIC = 20;
	public static final byte SWORD_4_FIRE = 21;
	public static final byte SWORD_4_COLD = 22;
	public static final byte SWORD_4_MAGIC = 23;
	public static final byte SWORD_4_THERMAL = 24;
	public static final byte SWORD_5_FIRE = 25;
	public static final byte SWORD_5_COLD = 26;
	public static final byte SWORD_5_MAGIC = 27;
	public static final byte SWORD_5_THERMAL = 28;
	public static final byte SWORD_6_FIRE = 29;
	public static final byte SWORD_6_COLD = 30;
	public static final byte SWORD_6_MAGIC = 31;
	public static final byte SWORD_6_THERMAL = 32;
	public static final byte SWORD_7_FIRE = 33;
	public static final byte SWORD_7_COLD = 34;
	public static final byte SWORD_7_MAGIC = 35;
	public static final byte SWORD_7_THERMAL = 36;
	public static final byte PICKAX_MAGIC = 37;
	public static final byte BATTLEAXE_IRON = 38;
	public static final byte BATTLEAXE_STEEL = 39;
	public static final byte BATTLEAXE_TITANIUM = 40;
	public static final byte BATTLEAXE_IRON_FIRE = 41;
	public static final byte BATTLEAXE_STEEL_COLD = 42;
	public static final byte BATTLEAXE_STEEL_FIRE = 43;
	public static final byte BATTLEAXE_TITANIUM_COLD = 44;
	public static final byte BATTLEAXE_TITANIUM_FIRE = 45;
	public static final byte BATTLEAXE_TITANIUM_MAGIC = 46;
	public static final byte GLOVE_FUR = 47;
	public static final byte GLOVE_LEATHER = 48;

	public static final byte frame_walk = 0;
	public static final byte frame_run = 1;
	public static final byte frame_die1 = 2;
	public static final byte frame_die2 = 3;
	public static final byte frame_pain1 = 4;
	public static final byte frame_pain2 = 11;
	public static final byte frame_pick = 5;
	public static final byte frame_drop = 6;
	public static final byte frame_idle = 7;
	public static final byte frame_harvest = 8;
	public static final byte frame_cast = 9;
	public static final byte frame_ranged = 10;
	public static final byte frame_sit = 12;
	public static final byte frame_stand = 13;
	public static final byte frame_sit_idle = 14;
	public static final byte frame_combat_idle = 15;
	public static final byte frame_in_combat = 16;
	public static final byte frame_out_combat = 17;
	public static final byte frame_attack_up_1 = 18;
	public static final byte frame_attack_up_2 = 19;
	public static final byte frame_attack_up_3 = 20;
	public static final byte frame_attack_up_4 = 21;
	public static final byte frame_attack_down_1 = 22;
	public static final byte frame_attack_down_2 = 23;

	//colors
	public static final byte c_red1 = 0;
	public static final byte c_red2 = 7;
	public static final byte c_red3 = 14;
	public static final byte c_red4 = 21;
	public static final byte c_orange1 = 1;
	public static final byte c_orange2 = 8;
	public static final byte c_orange3 = 15;
	public static final byte c_orange4 = 22;
	public static final byte c_yellow1 = 2;
	public static final byte c_yellow2 = 9;
	public static final byte c_yellow3 = 16;
	public static final byte c_yellow4 = 23;
	public static final byte c_green1 = 3;
	public static final byte c_green2 = 10;
	public static final byte c_green3 = 17;
	public static final byte c_green4 = 24;
	public static final byte c_blue1 = 4;
	public static final byte c_blue2 = 11;
	public static final byte c_blue3 = 18;
	public static final byte c_blue4 = 25;
	public static final byte c_purple1 = 5;
	public static final byte c_purple2 = 12;
	public static final byte c_purple3 = 19;
	public static final byte c_purple4 = 26;
	public static final byte c_grey1 = 6;
	public static final byte c_grey2 = 13;
	public static final byte c_grey3 = 20;
	public static final byte c_grey4 = 27;

	//foreign chars
	public static final int UUML = 180;
	public static final int EACUTE = 181;
	public static final int ACIRC = 182;
	public static final int AGRAVE = 183;
	public static final int CCEDIL = 184;
	public static final int ECIRC = 185;
	public static final int EUML = 186;
	public static final int EGRAVE = 187;
	public static final int IUML = 188;
	public static final int OCIRC = 189;
	public static final int UGRAVE = 190;
	public static final int aUMLAUT = 191;
	public static final int oUMLAUT = 192;
	public static final int uUMLAUT = 192;
	public static final int AUMLAUT = 194;
	public static final int OUMLAUT = 195;
	public static final int UUMLAUT = 196;
	public static final int DOUBLES = 197;
	public static final int aELIG = 198;
	public static final int oSLASH = 199;
	public static final int aRING = 200;
	public static final int AELIG = 201;
	public static final int OSLASH = 202;
	public static final int ARING = 203;

	//Windows
	public static final byte RULE_WIN = 1;
	public static final byte RULE_INTERFACE = 2;
	public static final byte NEW_CHAR_INTERFACE = 3;

	//actor commands
	public static final byte nothing = 0;
	public static final byte kill_me = 1;
	public static final byte die1 = 3;
	public static final byte die2 = 4;
	public static final byte pain1 = 5;
	public static final byte pain2 = 17;
	public static final byte pick = 6;
	public static final byte drop = 7;
	public static final byte idle = 8;
	public static final byte harvest = 9;
	public static final byte cast = 10;
	public static final byte ranged = 11;
	public static final byte meele = 12;
	public static final byte sit_down = 13;
	public static final byte stand_up = 14;
	public static final byte turn_left = 15;
	public static final byte turn_right = 16;
	public static final byte enter_combat = 18;
	public static final byte leave_combat = 19;

	public static final byte move_n = 20;
	public static final byte move_ne = 21;
	public static final byte move_e = 22;
	public static final byte move_se = 23;
	public static final byte move_s = 24;
	public static final byte move_sw = 25;
	public static final byte move_w = 26;
	public static final byte move_nw = 27;


	public static final byte run_n = 30;
	public static final byte run_ne = 31;
	public static final byte run_e = 32;
	public static final byte run_se = 33;
	public static final byte run_s = 34;
	public static final byte run_sw = 35;
	public static final byte run_w = 36;
	public static final byte run_nw = 37;

	public static final byte turn_n = 38;
	public static final byte turn_ne = 39;
	public static final byte turn_e = 40;
	public static final byte turn_se = 41;
	public static final byte turn_s = 42;
	public static final byte turn_sw = 43;
	public static final byte turn_w = 44;
	public static final byte turn_nw = 45;

	public static final byte attack_up_1 = 46;
	public static final byte attack_up_2 = 47;
	public static final byte attack_up_3 = 48;
	public static final byte attack_up_4 = 49;
	public static final byte attack_down_1 = 50;
	public static final byte attack_down_2 = 51;

	//to server commands
	public static final byte MOVE_TO = 1;
	public static final byte SEND_PM = 2;
	public static final byte GET_PLAYER_INFO = 5;
	public static final byte RUN_TO = 6;
	public static final byte SIT_DOWN = 7;
	public static final byte SEND_ME_MY_ACTORS = 8;
	public static final byte SEND_OPENING_SCREEN = 9;
	public static final byte SEND_VERSION = 10;
	public static final byte TURN_LEFT = 11;
	public static final byte TURN_RIGHT = 12;
	public static final byte PING = 13;
	public static final byte HEART_BEAT = 14;
	public static final byte LOCATE_ME = 15;
	public static final byte USE_MAP_OBJECT = 16;
	public static final byte SEND_MY_STATS = 17;
	public static final byte SEND_MY_INVENTORY = 18;
	public static final byte LOOK_AT_INVENTORY_ITEM = 19;
	public static final byte MOVE_INVENTORY_ITEM = 20;
	public static final byte HARVEST = 21;
	public static final byte DROP_ITEM = 22;
	public static final byte PICK_UP_ITEM = 23;
	public static final byte LOOK_AT_GROUND_ITEM = 24;
	public static final byte INSPECT_BAG = 25;
	public static final byte S_CLOSE_BAG = 26;
	public static final byte LOOK_AT_MAP_OBJECT = 27;
	public static final byte TOUCH_PLAYER = 28;
	public static final byte RESPOND_TO_NPC = 29;
	public static final byte MANUFACTURE_THIS = 30;
	public static final byte USE_INVENTORY_ITEM = 31;
	public static final byte TRADE_WITH = 32;
	public static final byte ACCEPT_TRADE = 33;
	public static final byte REJECT_TRADE = 34;
	public static final byte EXIT_TRADE = 35;
	public static final byte PUT_OBJECT_ON_TRADE = 36;
	public static final byte REMOVE_OBJECT_FROM_TRADE = 37;
	public static final byte LOOK_AT_TRADE_ITEM = 38;
	public static final byte CAST_SPELL = 39;
	public static final byte ATTACK_SOMEONE = 40;
	public static final byte GET_KNOWLEDGE_INFO = 41;
	public static final byte ITEM_ON_ITEM = 42;
	public static final byte PING_RESPONSE = 60;

	public static final int GET_DATE = 230;
	public static final int GET_TIME = 231;
	public static final int SERVER_STATS = 232;
	public static final int ORIGINAL_IP = 233;
	public static final int LOG_IN = 140;
	public static final int CREATE_CHAR = 141;

	//to client commands
	public static final byte ADD_NEW_ACTOR = 1;
	public static final byte ADD_ACTOR_COMMAND = 2;
	public static final byte YOU_ARE = 3;
	public static final byte SYNC_CLOCK = 4;
	public static final byte NEW_MINUTE = 5;
	public static final byte REMOVE_ACTOR = 6;
	public static final byte CHANGE_MAP = 7;
	public static final byte COMBAT_MODE = 8;
	public static final byte KILL_ALL_ACTORS = 9;
	public static final byte GET_TELEPORTERS_LIST = 10;
	public static final byte PONG = 11;
	public static final byte TELEPORT_IN = 12;
	public static final byte TELEPORT_OUT = 13;
	public static final byte PLAY_SOUND = 14;
	public static final byte START_RAIN = 15;
	public static final byte STOP_RAIN = 16;
	public static final byte THUNDER = 17;
	public static final byte HERE_YOUR_STATS = 18;
	public static final byte HERE_YOUR_INVENTORY = 19;
	public static final byte INVENTORY_ITEM_TEXT = 20;
	public static final byte GET_NEW_INVENTORY_ITEM = 21;
	public static final byte REMOVE_ITEM_FROM_INVENTORY = 22;
	public static final byte HERE_YOUR_GROUND_ITEMS = 23;
	public static final byte GET_NEW_GROUND_ITEM = 24;
	public static final byte REMOVE_ITEM_FROM_GROUND = 25;
	public static final byte CLOSE_BAG = 26;
	public static final byte GET_NEW_BAG = 27;
	public static final byte GET_BAGS_LIST = 28;
	public static final byte DESTROY_BAG = 29;
	public static final byte NPC_TEXT = 30;
	public static final byte NPC_OPTIONS_LIST = 31;
	public static final byte CLOSE_NPC_MENU = 32;
	public static final byte SEND_NPC_INFO = 33;
	public static final byte GET_TRADE_INFO = 34;
	public static final byte GET_TRADE_OBJECT = 35;
	public static final byte GET_TRADE_ACCEPT = 36;
	public static final byte GET_TRADE_REJECT = 37;
	public static final byte GET_TRADE_EXIT = 38;
	public static final byte REMOVE_TRADE_OBJECT = 39;
	public static final byte GET_YOUR_TRADEOBJECTS = 40;
	public static final byte GET_TRADE_PARTNER_NAME = 41;
	public static final byte GET_YOUR_SIGILS = 42;
	public static final byte SPELL_ITEM_TEXT = 43;
	public static final byte GET_ACTIVE_SPELL = 44;
	public static final byte GET_ACTIVE_SPELL_LIST = 45;
	public static final byte REMOVE_ACTIVE_SPELL = 46;
	public static final byte GET_ACTOR_DAMAGE = 47;
	public static final byte GET_ACTOR_HEAL = 48;
	public static final byte SEND_PARTIAL_STAT = 49;
	public static final byte SPAWN_BAG_PARTICLES = 50;
	public static final byte ADD_NEW_ENHANCED_ACTOR = 51;
	public static final byte ACTOR_WEAR_ITEM = 52;
	public static final byte ACTOR_UNWEAR_ITEM = 53;
	public static final byte PLAY_MUSIC = 54;
	public static final byte GET_KNOWLEDGE_LIST = 55;
	public static final byte GET_NEW_KNOWLEDGE = 56;
	public static final byte GET_KNOWLEDGE_TEXT = 57;
	public static final byte BUDDY_EVENT = 59;
	public static final byte PING_REQUEST = 60;
	public static final byte FIRE_PARTICLES = 61;
	public static final byte REMOVE_FIRE_AT = 62;
	public static final byte DISPLAY_CLIENT_WINDOW = 63;

	public static final int UPGRADE_NEW_VERSION = 240;
	public static final int UPGRADE_TOO_OLD = 241;
	public static final int REDEFINE_YOUR_COLORS = 248;
	public static final int YOU_DONT_EXIST = 249;
	public static final int LOG_IN_OK = 250;
	public static final int LOG_IN_NOT_OK = 251;
	public static final int CREATE_CHAR_OK = 252;
	public static final int CREATE_CHAR_NOT_OK = 253;

	//common (both to the server and client)
	public static final byte RAW_TEXT = 0;
	public static final int BYE = 255;

	//protocol places
	public static final byte PROTOCOL = 0; //is an unsigned char;

	//STATS
	public static final byte PHY_CUR = 0;
	public static final byte PHY_BASE = 1;
	public static final byte COO_CUR = 2;
	public static final byte COO_BASE = 3;
	public static final byte REAS_CUR = 4;
	public static final byte REAS_BASE = 5;
	public static final byte WILL_CUR = 6;
	public static final byte WILL_BASE = 7;
	public static final byte INST_CUR = 8;
	public static final byte INST_BASE = 9;
	public static final byte VIT_CUR = 10;
	public static final byte VIT_BASE = 11;
	public static final byte HUMAN_CUR = 12;
	public static final byte HUMAN_BASE = 13;
	public static final byte ANIMAL_CUR = 14;
	public static final byte ANIMAL_BASE = 15;
	public static final byte VEGETAL_CUR = 16;
	public static final byte VEGETAL_BASE = 17;
	public static final byte INORG_CUR = 18;
	public static final byte INORG_BASE = 19;
	public static final byte ARTIF_CUR = 20;
	public static final byte ARTIF_BASE = 21;
	public static final byte MAGIC_CUR = 22;
	public static final byte MAGIC_BASE = 23;
	public static final byte MAN_S_CUR = 24;
	public static final byte MAN_S_BASE = 25;
	public static final byte HARV_S_CUR = 26;
	public static final byte HARV_S_BASE = 27;
	public static final byte ALCH_S_CUR = 28;
	public static final byte ALCH_S_BASE = 29;
	public static final byte OVRL_S_CUR = 30;
	public static final byte OVRL_S_BASE = 31;
	public static final byte DEF_S_CUR = 32;
	public static final byte DEF_S_BASE = 33;
	public static final byte ATT_S_CUR = 34;
	public static final byte ATT_S_BASE = 35;
	public static final byte MAG_S_CUR = 36;
	public static final byte MAG_S_BASE = 37;
	public static final byte POT_S_CUR = 38;
	public static final byte POT_S_BASE = 39;
	public static final byte CARRY_WGHT_CUR = 40;
	public static final byte CARRY_WGHT_BASE = 41;
	public static final byte MAT_POINT_CUR = 42;
	public static final byte MAT_POINT_BASE = 43;
	public static final byte ETH_POINT_CUR = 44;
	public static final byte ETH_POINT_BASE = 45;
	public static final byte FOOD_LEV = 46;
	public static final byte RESEARCHING = 47;
	public static final byte MAG_RES = 48;
	public static final byte MAN_EXP = 49;
	public static final byte MAN_EXP_NEXT = 50;
	public static final byte HARV_EXP = 51;
	public static final byte HARV_EXP_NEXT = 52;
	public static final byte ALCH_EXP = 53;
	public static final byte ALCH_EXP_NEXT = 54;
	public static final byte OVRL_EXP = 55;
	public static final byte OVRL_EXP_NEXT = 56;
	public static final byte DEF_EXP = 57;
	public static final byte DEF_EXP_NEXT = 58;
	public static final byte ATT_EXP = 59;
	public static final byte ATT_EXP_NEXT = 60;
	public static final byte MAG_EXP = 61;
	public static final byte MAG_EXP_NEXT = 62;
	public static final byte POT_EXP = 63;
	public static final byte POT_EXP_NEXT = 64;
	public static final byte RESEARCH_COMPLETED = 65;
	public static final byte RESEARCH_TOTAL = 66;
	public static final byte SUM_EXP = 67;
	public static final byte SUM_EXP_NEXT = 68;
	public static final byte SUM_S_CUR = 69;
	public static final byte SUM_S_BASE = 70;
	public static final byte CRA_EXP = 71;
	public static final byte CRA_EXP_NEXT = 72;
	public static final byte CRA_S_CUR = 73;
	public static final byte CRA_S_BASE = 74;

	//SOUND
	public static final byte snd_rain = 0;
	public static final byte snd_tele_in = 1;
	public static final byte snd_tele_out = 2;
	public static final byte snd_teleprtr = 3;
	public static final byte snd_thndr_1 = 4;
	public static final byte snd_thndr_2 = 5;
	public static final byte snd_thndr_3 = 6;
	public static final byte snd_thndr_4 = 7;
	public static final byte snd_thndr_5 = 8;
}
