package me.pseudoknight.chdiscord.abstraction.jda;

import com.laytonsmith.abstraction.StaticLayer;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import me.pseudoknight.chdiscord.Extension;
import me.pseudoknight.chdiscord.abstraction.jda.Events.JDADiscordGuildMessageReceivedEvent;
import me.pseudoknight.chdiscord.abstraction.jda.Events.JDADiscordPrivateMessageReceivedEvent;
import me.pseudoknight.chdiscord.abstraction.jda.Events.JDADiscordVoiceJoinEvent;
import me.pseudoknight.chdiscord.abstraction.jda.Events.JDADiscordVoiceLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Listener extends ListenerAdapter {

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if(event.getAuthor().equals(Extension.jda.getSelfUser())) {
			return;
		}
		final JDADiscordGuildMessageReceivedEvent mre = new JDADiscordGuildMessageReceivedEvent(event);
		try {
			StaticLayer.GetConvertor().runOnMainThreadAndWait(() -> {
				EventUtils.TriggerListener(Driver.EXTENSION, "discord_message_received", mre);
				return null;
			});
		} catch(Exception ex) {
			Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		if(event.getAuthor().equals(Extension.jda.getSelfUser())) {
			return;
		}
		final JDADiscordPrivateMessageReceivedEvent mre = new JDADiscordPrivateMessageReceivedEvent(event);
		try {
			StaticLayer.GetConvertor().runOnMainThreadAndWait(() -> {
				EventUtils.TriggerListener(Driver.EXTENSION, "discord_direct_message_received", mre);
				return null;
			});
		} catch(Exception ex) {
			Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
		final JDADiscordVoiceJoinEvent vje = new JDADiscordVoiceJoinEvent(event);
		try {
			StaticLayer.GetConvertor().runOnMainThreadAndWait(() -> {
				EventUtils.TriggerListener(Driver.EXTENSION, "discord_voice_joined", vje);
				return null;
			});
		} catch(Exception ex) {
			Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
		final JDADiscordVoiceLeaveEvent vle = new JDADiscordVoiceLeaveEvent(event);
		try {
			StaticLayer.GetConvertor().runOnMainThreadAndWait(() -> {
				EventUtils.TriggerListener(Driver.EXTENSION, "discord_voice_left", vle);
				return null;
			});
		} catch(Exception ex) {
			Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
