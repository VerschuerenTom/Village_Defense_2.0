package pl.Plajer.GameAPI;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class LanguageManager {

	private static JavaPlugin plugin;
	private static FileConfiguration languageConfig = null;
	private static File languageConfigFile = null;

	public LanguageManager(JavaPlugin pl){
		plugin = pl;
	}

	public static void saveDefaultLanguageFile() {
		if (languageConfigFile == null) {
			languageConfigFile = new File(plugin.getDataFolder(), "language.yml");
		}
		if (!languageConfigFile.exists()) {            
			plugin.saveResource("language.yml", false);
		}
	}

	public static String getLanguageMessage(String message) {
		if (languageConfig == null) {
			reloadLanguageFile();
		}
		if(getLanguageFile().isSet(message)) {
			return getLanguageFile().getString(message);
		}
		return null;
	}
	
	public static FileConfiguration getLanguageFile() {
		if (languageConfig == null) {
			reloadLanguageFile();
		}
		return languageConfig;
	}

	public static void reloadLanguageFile() {
		if (languageConfigFile == null) {
			languageConfigFile = new File(plugin.getDataFolder(), "language.yml");
		}
		languageConfig = YamlConfiguration.loadConfiguration(languageConfigFile);

		// Look for defaults in the jar
		try{
			Reader defConfigStream = new InputStreamReader(plugin.getResource("language.yml"));
			if (defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				languageConfig.setDefaults(defConfig);
			}
		} catch(Exception e){
			System.out.println("[NewGameAPI] Error occured while trying to reload configuration!");
			e.printStackTrace();
		}
	}

	public static void saveLanguageFile() {
		if (languageConfig == null || languageConfigFile == null) {
			return;
		}
		try {
			getLanguageFile().save(languageConfigFile);
		} catch (IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "Could not save file to " + languageConfigFile, ex);
		}
	}

}
