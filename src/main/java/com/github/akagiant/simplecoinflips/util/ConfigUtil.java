package com.github.akagiant.simplecoinflips.util;

import me.akagiant.giantapi.util.ColorManager;
import me.akagiant.giantapi.util.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigUtil {

	// TODO: Port to GiantAPI

	public static String string = "String ('your text here')";
	public static String[] strings = new String[]{"'line 1'", "'line 2'", "'line 3'"};
	public static String wholeNumber = "Whole Number E.G. 1, 2, 3";
	public static String decimalNumber = "Decimal or Whole Number E.G. 1, 1.0, 4.23";

	public static String valueMissing = "The value expected at the current path is not a missing.";
	public static String valueNotValid = "The value inputted at the current path is not valid.";
	public static String expectedStringList = "Hmm... I was expecting to find a list of string but found nothing...";
	public static String expectedString = "Hmm... I was expecting to find a string but found nothing...";

	private ConfigUtil() {
		//no instance
	}

	public static String getString(Config config, String path) {
		if (!isSet(config, path)) {
			logError(config, path, expectedString, null, string);
			return "";
		}
		return ColorManager.formatColours(config.getConfig().getString(path));
	}

	public static List<String> getStringList(Config config, String path) {
		if (!isSet(config, path)) {
			logError(config, path, expectedStringList, null, strings);
			return new ArrayList<>();
		}
		List<String> stringList = config.getConfig().getStringList(path);
		if (stringList.isEmpty()) {
			logError(config, path, expectedStringList, null, strings);
			return new ArrayList<>();
		}
		return ColorManager.formatColours(config.getConfig().getStringList(path));
	}

	public static boolean isSet(Config config, String path) {
		return config.getConfig().isSet(path);
	}

	public static double getDouble(Config config, String path) {
		if (!isSet(config, path)) {
			logError(config, path, valueMissing, null, decimalNumber);
			return 0.0;
		}

		return config.getConfig().getDouble(path);
	}

	public static int getInt(Config config, String path) {
		if (!isSet(config, path)) {
			logError(config, path, valueMissing, null, wholeNumber);
			return 0;
		}

		if (!config.getConfig().isInt(path)) {
			logError(config, path, valueNotValid, null, wholeNumber);
			return 0;
		}

		return config.getConfig().getInt(path);
	}

	public static boolean isSetAndStringIsValid(Config config, String path) {
		return config.getConfig().isSet(path) && config.getConfig().getString(path) != null;
	}

	public static boolean getBoolean(Config config, String path) {
		return config.getConfig().isSet(path) && config.getConfig().getBoolean(path);
	}

	public static ConfigurationSection getConfigurationSection(Config config, String path) {
		if (config.getConfig().getConfigurationSection(path) == null) return null;
		return config.getConfig().getConfigurationSection(path);
	}

	public static void logError(Config config, String path, String errorMessage, @Nullable String url, String... expected) {
		Logger.severe("&m————————————————————————————————————");
		Logger.severe("&fConfiguration Error");
		Logger.severe("&m————————————————————————————————————");
		Logger.severe("&fError: " + errorMessage);
		Logger.severe("&fFile: " + config.getFile().getName());
		Logger.severe("&fPath: ");
		printPath(path, expected);
		if (url != null) { Logger.severe("&b" + url + " &ffor more information"); }

		Logger.severe("&m————————————————————————————————————");
	}


	private static void printPath(String path, String... expected) {
		String[] splitPath = path.split("\\.");
		for (int i = 0; i < splitPath.length; i++) {

			final String whiteSpace = new String(new char[i]).replace("\0", "  ");

			if (i != splitPath.length - 1) {
				Logger.severe("&f" + whiteSpace + splitPath[i] + ":");
				continue;
			}

			Logger.severe("&f" + whiteSpace + splitPath[i] + ": <- Expected " + Arrays.toString(expected));
			if (expected.length <= 1) return;
			for (String str : expected) {
				Logger.severe("&f" + whiteSpace + " - " + str);
			}
		}
	}

}
