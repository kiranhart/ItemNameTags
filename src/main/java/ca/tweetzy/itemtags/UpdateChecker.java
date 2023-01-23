package ca.tweetzy.itemtags;

import ca.tweetzy.flight.FlightPlugin;
import ca.tweetzy.flight.utils.Common;
import org.bukkit.command.CommandSender;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

public class UpdateChecker {

	public enum UpdateStatus {
		UNKNOWN, ERROR, UP_TO_DATE, UPDATE_AVAILABLE, UNRELEASED_VERSION
	}

	private UpdateStatus status = UpdateStatus.UNKNOWN;
	final String API_URL = "https://api.spigotmc.org/legacy/update.php?resource=%d";

	final FlightPlugin plugin;
	final int SPIGOT_ID;
	final CommandSender[] to;
	private String latestVersion = "0.0.0";

	private String getLatestVersionFromSpigot() {
		String version = "0.0.0";
		try {
			URL url = new URL(String.format(API_URL, SPIGOT_ID));
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "text/plain");
			connection.setRequestProperty("User-Agent", "Tweetzy Plugin Update Checker");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			StringBuilder content = null;

			if (connection.getResponseCode() == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				content = new StringBuilder();
				String input;
				while ((input = reader.readLine()) != null) {
					content.append(input);
				}
				reader.close();
			}

			connection.disconnect();

			if (content != null) {
				version = content.toString();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return version;
	}

	public UpdateChecker check() {
		latestVersion = getLatestVersionFromSpigot();
		int[] latest = Arrays.stream(latestVersion.split("\\.")).mapToInt(Integer::parseInt).toArray();
		int[] current = Arrays.stream(plugin.getDescription().getVersion().split("\\.")).mapToInt(Integer::parseInt).toArray();

		if (latest.length != 3 || current.length != 3) {
			status = UpdateStatus.ERROR;
			return this;
		}

		if (latest[0] == current[0] || latest[1] == current[1] || latest[2] == current[2])
			status = UpdateStatus.UP_TO_DATE;
		if (latest[0] > current[0] || latest[1] > current[1] || latest[2] > current[2])
			status = UpdateStatus.UPDATE_AVAILABLE;
		if (latest[0] < current[0] || latest[1] < current[1] || latest[2] < current[2])
			status = UpdateStatus.UNRELEASED_VERSION;

		for (CommandSender sender : to) {
			switch (status) {
				case UP_TO_DATE:
					Common.log("&aIs running the latest version!");
					break;
				case UPDATE_AVAILABLE:
					Common.log(String.format("&dA new update is available for %s", plugin.getDescription().getName()));
					break;
				case UNRELEASED_VERSION:
					Common.log(String.format("&dYou're running an unreleased version of %s &f(&c%s&f)", plugin.getDescription().getName(), plugin.getDescription().getVersion()));
					break;
				case UNKNOWN:
					Common.log("&cUnknown version is in use");
					break;
				case ERROR:
					Common.log("&cAn error has occurred while trying to get the latest version");
					break;
			}
		}

		return this;
	}

	public UpdateChecker(FlightPlugin plugin, int spigotID, CommandSender... to) {
		this.plugin = plugin;
		this.SPIGOT_ID = spigotID;
		this.to = to;
	}

	public UpdateStatus getStatus() {
		return status;
	}

	public boolean isUpdateToDate() {
		return getStatus() == UpdateStatus.UP_TO_DATE;
	}

	public String getLatestVersion() {
		return latestVersion;
	}
}