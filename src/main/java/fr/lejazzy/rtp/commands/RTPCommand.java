package fr.lejazzy.rtp.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import fr.lejazzy.rtp.gui.GuiRTP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CommandAlias("rtp")
public class RTPCommand extends BaseCommand {

    private final Map<UUID, Integer> rtpUses = new HashMap<>();
    private final Map<UUID, Long> lastRtpTime = new HashMap<>();
    private static final long COOLDOWN_TIME = 24 * 60 * 60 * 1000;
    private final GuiRTP guiRTP = new GuiRTP();

    @Default
    public void onRTP(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cette commande ne peut être utilisée que par des joueurs.");
            return;
        }

        Player player = (Player) sender;
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        if (lastRtpTime.containsKey(playerId)) {
            long lastTime = lastRtpTime.get(playerId);
            if (currentTime - lastTime < COOLDOWN_TIME) {
                long timeLeft = (COOLDOWN_TIME - (currentTime - lastTime)) / 1000;
                player.sendMessage("§7[§eSkyRTP§7] >> §fTu dois attendre encore " + formatTime(currentTime) + " secondes avant d'utiliser à nouveau la commande RTP.");
                return;
            }
        }

        int uses = rtpUses.getOrDefault(playerId, 0);
        if (uses >= 3) {
            player.sendMessage("§7[§eSkyRTP§7] >> §fTu as utilisé le RTP maximum");
            return;
        }

        Location randomLocation = getRandomLocation(player);
        player.teleport(randomLocation);
        player.sendMessage("§7[§eSkyRTP§7] >> §fTu as bien été téléporté en localisation aléatoire !");
        rtpUses.put(playerId, uses + 1);
        lastRtpTime.put(playerId, currentTime);
    }

    @Subcommand("reset")
    @CommandPermission("skyrtp.reset")
    public void onReset(CommandSender sender, String targetPlayerName) {
        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
        if (targetPlayer == null) {
            sender.sendMessage("§7[§eSkyRTP§7] >> §fLe joueur est §cintrouvable");
            return;
        }

        rtpUses.remove(targetPlayer.getUniqueId());
        lastRtpTime.remove(targetPlayer.getUniqueId());
        sender.sendMessage("§7[§eSkyRTP§7] >> §fUtilisation des RTP a été réinitialisée pour le joueur : " + targetPlayerName);
    }

    @Subcommand("help")
    @CommandPermission("skyrtp.help")
    public void onHelp(Player player) {
        player.sendMessage("§7----------------------------------------------------------");
        player.sendMessage(" ");
        player.sendMessage("§aCommandes disponibles :");
        player.sendMessage("/rtp §7>> §fSert a ce téléporter aléatoirement sur la map");
        if (player.hasPermission("skyrtp.help")) {
            player.sendMessage("/rtp reset <pseudo> §7>> Sert a reinialiser le maximum de rtp d'un joueur");
        }
        player.sendMessage("§7----------------------------------------------------------");
    }

    @Subcommand("gui")
    @CommandPermission("skyrtp.gui")
    public void onGui(Player player) {
        // Ouvre le menu pour le joueur
        player.openInventory(guiRTP.createInventory());
    }

    private Location getRandomLocation(Player player) {
        int x = (int) (Math.random() * 10000) - 5000;
        int z = (int) (Math.random() * 10000) - 5000;
        int y = player.getWorld().getHighestBlockYAt(x, z);
        return new Location(player.getWorld(), x, y, z);
    }

    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        if (minutes > 0) {
            return minutes + "m " + remainingSeconds + "s";
        } else {
            return remainingSeconds + "s";
        }
    }
}