package com.palmergames.bukkit.towny; /* Localized on 2014-05-05 by Neder */

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.ResidentList;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.palmergames.bukkit.util.BukkitTools;
import com.palmergames.bukkit.util.ChatTools;
import com.palmergames.bukkit.util.Colors;

/**
 * Towny message handling class
 * 
 * @author ElgarL
 * 
 */

public class TownyMessaging {

	/**
	 * Sends an error message to the log
	 * 
	 * @param msg
	 */
	public static void sendErrorMsg(String msg) {

		TownyLogger.log.warning(ChatTools.stripColour("[타우니] 오류: " + msg));
	}

	/**
	 * Sends an Error message (red) to the Player or console
	 * and to the named Dev if DevMode is enabled.
	 * Uses default_towny_prefix
	 * 
	 * @param sender
	 * @param msg
	 */
	public static void sendErrorMsg(Object sender, String msg) {

		boolean isPlayer = false;
		if (sender instanceof Player)
			isPlayer = true;

		if (sender == null)
			System.out.print("메시지의 sender가 없습니다.");

		for (String line : ChatTools.color(TownySettings.getLangString("default_towny_prefix") + Colors.Rose + msg))
			if (isPlayer)
				((Player) sender).sendMessage(line);
			else
				((CommandSender) sender).sendMessage(Colors.strip(line));
		sendDevMsg(msg);
	}

	/**
	 * Sends an Error message (red) to the Player or console
	 * and to the named Dev if DevMode is enabled.
	 * Uses default_towny_prefix
	 * 
	 * @param sender
	 * @param msg
	 */
	public static void sendErrorMsg(Object sender, String[] msg) {

		boolean isPlayer = false;
		if (sender instanceof Player)
			isPlayer = true;

		for (String line : ChatTools.color(TownySettings.getLangString("default_towny_prefix") + Colors.Rose + msg))
			if (isPlayer)
				((Player) sender).sendMessage(line);
			else
				((CommandSender) sender).sendMessage(Colors.strip(line));
		sendDevMsg(msg);
	}

	/**
	 * Sends a message to console only
	 * prefixed by [Towny]
	 * 
	 * @param msg
	 */
	public static void sendMsg(String msg) {

		TownyLogger.log.info("[Towny] " + ChatTools.stripColour(msg));
	}

	/**
	 * Sends a message (green) to the Player or console
	 * and to the named Dev if DevMode is enabled.
	 * Uses default_towny_prefix
	 * 
	 * @param sender
	 * @param msg
	 */
	public static void sendMsg(Object sender, String msg) {

		for (String line : ChatTools.color(TownySettings.getLangString("default_towny_prefix") + Colors.Green + msg))
			if (sender instanceof Player)
				((Player) sender).sendMessage(line);
			else if (sender instanceof CommandSender)
				((CommandSender) sender).sendMessage(Colors.strip(line));
			else if (sender instanceof Resident)
				try {
					TownyUniverse.getPlayer(((Resident) sender)).sendMessage(Colors.strip(line));
				} catch (TownyException e) {
					// No player exists
				}
				
		sendDevMsg(msg);
	}

	/**
	 * Sends a message (green) to the Player or console
	 * and to the named Dev if DevMode is enabled.
	 * Uses default_towny_prefix
	 * 
	 * @param player
	 * @param msg
	 */
	public static void sendMsg(Player player, String[] msg) {

		for (String line : ChatTools.color(TownySettings.getLangString("default_towny_prefix") + Colors.Green + msg))
			player.sendMessage(line);
	}

	/**
	 * Sends a message (red) to the named Dev (if DevMode is enabled)
	 * Uses default_towny_prefix
	 * 
	 * @param msg
	 */
	public static void sendDevMsg(String msg) {

		if (TownySettings.isDevMode()) {
			Player townyDev = BukkitTools.getPlayer(TownySettings.getDevName());
			if (townyDev == null)
				return;
			for (String line : ChatTools.color(TownySettings.getLangString("default_towny_prefix") + " 개발모드: " + Colors.Rose + msg))
				townyDev.sendMessage(line);
		}
	}

	/**
	 * Sends a message (red) to the named Dev (if DevMode is enabled)
	 * Uses default_towny_prefix
	 * 
	 * @param msg
	 */
	public static void sendDevMsg(String[] msg) {

		if (TownySettings.isDevMode()) {
			Player townyDev = BukkitTools.getPlayer(TownySettings.getDevName());
			if (townyDev == null)
				return;
			for (String line : ChatTools.color(TownySettings.getLangString("default_towny_prefix") + " 개발모드: " + Colors.Rose + msg))
				townyDev.sendMessage(line);
		}
	}

	/**
	 * Sends a message to the log and console
	 * prefixed by [Towny] Debug:
	 * 
	 * @param msg
	 */
	public static void sendDebugMsg(String msg) {

		if (TownySettings.getDebug())
			TownyLogger.debug.info(ChatTools.stripColour("[타우니] 디버그: " + msg));
		sendDevMsg(msg);
	}

	/////////////////

	/**
	 * Send a message to a player
	 * 
	 * @param sender
	 * @param lines
	 */
	public static void sendMessage(Object sender, List<String> lines) {

		sendMessage(sender, lines.toArray(new String[0]));
	}

	/**
	 * Send a message to a player
	 * 
	 * @param sender
	 * @param line
	 */
	public static void sendMessage(Object sender, String line) {

		if ((sender instanceof Player)) {
			((Player) sender).sendMessage(line);
		} else if (sender instanceof CommandSender)
			((CommandSender) sender).sendMessage(line);
		else if (sender instanceof Resident)
			try {
				TownyUniverse.getPlayer(((Resident) sender)).sendMessage(Colors.strip(line));
			} catch (TownyException e) {
				// No player exists
			}
	}

	/**
	 * Send a message to a player
	 * 
	 * @param sender
	 * @param lines
	 */
	public static void sendMessage(Object sender, String[] lines) {

		boolean isPlayer = false;
		if (sender instanceof Player)
			isPlayer = true;

		for (String line : lines) {
			if (isPlayer) {
				((Player) sender).sendMessage(line);
			} else
				((CommandSender) sender).sendMessage(line);
		}
	}

	/**
	 * Send a message to all online residents of a town
	 * 
	 * @param town
	 * @param lines
	 */
	public static void sendTownMessage(Town town, List<String> lines) {

		sendTownMessage(town, lines.toArray(new String[0]));
	}

	/**
	 * Send a message to all online residents of a nation
	 * 
	 * @param nation
	 * @param lines
	 */
	public static void sendNationMessage(Nation nation, List<String> lines) {

		sendNationMessage(nation, lines.toArray(new String[0]));
	}

	/**
	 * Send a message to ALL online players and the log.
	 * 
	 * @param lines
	 */
	public static void sendGlobalMessage(List<String> lines) {

		sendGlobalMessage(lines.toArray(new String[0]));
	}

	/**
	 * Send a message to ALL online players and the log.
	 * 
	 * @param lines
	 */
	public static void sendGlobalMessage(String[] lines) {

		for (String line : lines) {
			TownyLogger.log.info(ChatTools.stripColour("[전체 메시지] " + line));
		}
		for (Player player : BukkitTools.getOnlinePlayers())
			if (player != null)
				for (String line : lines)
					player.sendMessage(line);
	}

	/**
	 * Send a message to All online players and the log.
	 * 
	 * @param line
	 */
	public static void sendGlobalMessage(String line) {

		TownyLogger.log.info(ChatTools.stripColour("[전체 메시지] " + line));
		for (Player player : BukkitTools.getOnlinePlayers()) {
			if (player != null)
				player.sendMessage(line);
		}
	}

	/**
	 * Send a message to a specific resident
	 * 
	 * @param resident
	 * @param lines
	 * @throws TownyException
	 */
	public static void sendResidentMessage(Resident resident, String[] lines) throws TownyException {

		for (String line : lines) {
			TownyLogger.log.info(ChatTools.stripColour("[주민 메시지] " + resident.getName() + ": " + line));
		}
		Player player = TownyUniverse.getPlayer(resident);
		for (String line : lines)
			player.sendMessage(line);

	}

	/**
	 * Send a message to a specific resident
	 * 
	 * @param resident
	 * @param line
	 * @throws TownyException
	 */
	public static void sendResidentMessage(Resident resident, String line) throws TownyException {

		TownyLogger.log.info(ChatTools.stripColour("[주민 메시지] " + resident.getName() + ": " + line));
		Player player = TownyUniverse.getPlayer(resident);
		player.sendMessage(TownySettings.getLangString("default_towny_prefix") + line);
	}

	/**
	 * Send a message to All online residents of a town and log
	 * 
	 * @param town
	 * @param lines
	 */
	public static void sendTownMessage(Town town, String[] lines) {

		for (String line : lines) {
			TownyLogger.log.info(ChatTools.stripColour("[마을 메시지] " + town.getName() + ": " + line));
		}
		for (Player player : TownyUniverse.getOnlinePlayers(town)) {
			for (String line : lines)
				player.sendMessage(line);
		}
	}

	/**
	 * Send a message to All online residents of a town and log
	 * 
	 * @param town
	 * @param line
	 */
	public static void sendTownMessagePrefixed(Town town, String line) {

		TownyLogger.log.info(ChatTools.stripColour(line));
		for (Player player : TownyUniverse.getOnlinePlayers(town))
			player.sendMessage(TownySettings.getLangString("default_towny_prefix") + line);
	}

	/**
	 * Send a message to All online residents of a town and log
	 * 
	 * @param town
	 * @param line
	 */
	public static void sendTownMessage(Town town, String line) {

		TownyLogger.log.info(ChatTools.stripColour("[마을 메시지] " + town.getName() + ": " + line));
		for (Player player : TownyUniverse.getOnlinePlayers(town))
			player.sendMessage(line);
	}

	/**
	 * Send a message to All online residents of a nation and log
	 * 
	 * @param nation
	 * @param lines
	 */
	public static void sendNationMessage(Nation nation, String[] lines) {

		for (String line : lines) {
			TownyLogger.log.info(ChatTools.stripColour("[국가 메시지] " + nation.getName() + ": " + line));
		}
		for (Player player : TownyUniverse.getOnlinePlayers(nation))
			for (String line : lines)
				player.sendMessage(line);
	}

	/**
	 * Send a message to All online residents of a nation and log
	 * 
	 * @param nation
	 * @param line
	 */
	public static void sendNationMessage(Nation nation, String line) {

		TownyLogger.log.info(ChatTools.stripColour("[국가 메시지] " + nation.getName() + ": " + line));
		for (Player player : TownyUniverse.getOnlinePlayers(nation))
			player.sendMessage(line);
	}

	/**
	 * Send a message to All online residents of a nation and log
	 * 
	 * @param nation
	 * @param line
	 */
	public static void sendNationMessagePrefixed(Nation nation, String line) {

		TownyLogger.log.info(ChatTools.stripColour("[국가 메시지] " + nation.getName() + ": " + line));
		for (Player player : TownyUniverse.getOnlinePlayers(nation))
			player.sendMessage(TownySettings.getLangString("default_towny_prefix") + line);
	}

	/**
	 * Send the town board to a player (in yellow)
	 * 
	 * @param player
	 * @param town
	 */
	public static void sendTownBoard(Player player, Town town) {

		for (String line : ChatTools.color(Colors.Gold + "[" + town.getName() + "] " + Colors.Yellow + town.getTownBoard()))
			player.sendMessage(line);
	}
	
	/**
	 * Send a message to all residents in the list with the required mode
	 * 
	 * @param residents
	 * @param msg
	 * @param modeRequired
	 */
	public static void sendMessageToMode(ResidentList residents, String msg, String modeRequired) {

		for (Resident resident : TownyUniverse.getOnlineResidents(residents))
			if (resident.hasMode(modeRequired))
				sendMessage(resident, msg);
	}
	
	/**
	 * Send a message to all residents in the town with the required mode
	 * 
	 * @param town
	 * @param msg
	 * @param modeRequired
	 */
	public static void sendMessageToMode(Town town, String msg, String modeRequired) {

		for (Resident resident : town.getResidents())
			if (BukkitTools.isOnline(resident.getName()))
				sendMessage(resident,msg);
	}
}