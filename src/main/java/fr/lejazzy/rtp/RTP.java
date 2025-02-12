package fr.lejazzy.rtp;

import co.aikar.commands.PaperCommandManager;
import fr.lejazzy.rtp.api.CommandRegister;
import fr.lejazzy.rtp.api.ListenerRegister;
import fr.lejazzy.rtp.commands.RTPCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public class RTP extends JavaPlugin {

    private CommandRegister commandRegister;
    private ListenerRegister listenerRegister;


    @Override
    public void onEnable() {


        this.commandRegister = new CommandRegister(this);
        this.listenerRegister = new ListenerRegister(this);

        registerListeners();
        registerCommands();

        System.out.println(ChatColor.RED + "le RTP viens de se démarée.");

    }

    @Override
    public void onDisable() {
        System.out.println(ChatColor.RED + "Le RTP viens de s'éteindre.");
    }


    private void registerListeners() {
        if (listenerRegister == null) {
            getLogger().severe("Erreur : ListenerRegister n'a pas été initialisé !");
            return;
        }

        getLogger().info("Listeners enregistrés avec succès.");
    }

    private void registerCommands() {
        if (commandRegister == null) {
            getLogger().severe("Erreur : CommandRegister n'a pas été initialisé !");
            return;
        }

        PaperCommandManager commandManager = new PaperCommandManager(this);

        commandRegister.registerCommands(
                new RTPCommand()
        );
        getLogger().info("Commandes enregistrées avec succès.");
    }


}
