package de.bossascrew.generator.data;

public class Message {

	public static final String PREFIX = "§6Jeff §7> ";
	
	public static final String NO_PERMISSION = PREFIX + "Dazu hast du nicht die nötigen Berechtigungen!";
	public static final String WRONG_COMMAND_USAGE = PREFIX + "Dein parameter keine zahl wallah mach /oregenerator restore [id]";
	public static final String MAXIMUM_GENERATORS_PLACED = PREFIX + "So viele Generatoren brauchst du doch gar nicht! Du hast dein Limit erreicht";
	public static final String USE_GUI_TO_DROP = PREFIX + "Benutze das Menü des Generators, um ihn zu droppen!";
	public static final String CANT_AFFORD_LEVEL = PREFIX + "Das kannst du dir so nicht leisten. Dir fehlen noch Items!";
	public static final String OUT_OF_PLACED = PREFIX + "Du hast [placed]/[maximum] Generatoren gesetzt!";
	public static final String NOT_YOUR_GENERATOR = PREFIX + "§7Das ist nicht dein Generator. Er gehört §e[Player]§7!";	
	
	public static final String GUI_TITLE = "§9Erze Generator";
	
	public static final String GUI_PROBABILITY_OVERWORLD = "§7Oberwelt:";
	public static final String GUI_PROBABILITY_NETHER = "§7Im Nether:";
	public static final String GUI_PROBABILITY_PROBS = "§f§nWahrscheinlichkeiten:";
	public static final String GUI_PROBABILITY_FORMAT = "§7- §f[name]§7: §a[probability]§7%";

	public static final String GUI_REQUIREMENTS_TITLE = "§f§nLevel [level]";
	public static final String GUI_REQUIREMENTS_LEVEL = "§7- §fVoriges Level";
	public static final String GUI_REQUIREMENTS_FORMAT = "§7- §f[item]§7, §a[amount]x";
	public static final String GUI_REQUIREMENTS_LORE1 = "§8Schalte erst das vorige";
	public static final String GUI_REQUIREMENTS_LORE2 = "§8Level frei!";
	
	public static final String GUI_DROP_TITLE = "§f§nGenerator abbauen";
	public static final String GUI_DROP_LORE1 = "§7Hiermit kannst du den Generator";
	public static final String GUI_DROP_LORE2 = "§7abbauen und umplatzieren!";

	public static final String GUI_CONFIRM_TITLE = "§cDie nötigen Items verschwinden!";
	public static final String GUI_CONFIRM_CONFIRM = "§f§nIst mir bewusst!";
	public static final String GUI_CONFIRM_DENY = "§f§nKreisch ernsthaft?! ABBRUCH";
}