package com.discord.bot.commands.musiccommands;

import com.discord.bot.audioplayer.GuildMusicManager;
import com.discord.bot.audioplayer.PlayerManager;
import com.discord.bot.commands.ISlashCommand;
import com.discord.bot.entity.User;
import com.discord.bot.service.UserService;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SwapCommand implements ISlashCommand {
    MusicCommandUtils utils;

    public SwapCommand(MusicCommandUtils utils) {
        this.utils = utils;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        GuildVoiceState botVoiceState = event.getGuild().getSelfMember().getVoiceState();
        GuildVoiceState userVoiceState = event.getMember().getVoiceState();
        if (utils.channelControl(botVoiceState, userVoiceState)) {
            GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event);

            List<AudioTrack> trackList = new ArrayList<>(musicManager.scheduler.queue);

            if (trackList.size() > 1) {
                int first = event.getOption("songnum1").getAsInt() - 1;
                int second = event.getOption("songnum2").getAsInt() - 1;

                try {
                    AudioTrack temp = trackList.get(first);
                    trackList.set(first, trackList.get(second));
                    trackList.set(second, temp);
                } catch (Exception e) {
                    event.replyEmbeds(new EmbedBuilder()
                            .setDescription("Please enter a valid queue ids for both of the songs.")
                            .setColor(Color.RED).build()).queue();
                    return;
                }

                musicManager.scheduler.queue.clear();
                for (AudioTrack track : trackList) {
                    musicManager.scheduler.queue(track);
                }

                event.replyEmbeds(new EmbedBuilder()
                        .setDescription("Successfully swapped order of two songs")
                        .setColor(Color.GREEN).build()).queue();
            } else if (trackList.size() == 1) {
                event.replyEmbeds(new EmbedBuilder().setDescription("There is only one song in queue.")
                        .setColor(Color.RED).build()).queue();
            } else {
                event.replyEmbeds(new EmbedBuilder().setDescription("Queue is empty.")
                        .setColor(Color.RED).build()).queue();
            }
        } else {
            event.replyEmbeds(new EmbedBuilder().setDescription("Please be in a same voice channel as bot.")
                    .setColor(Color.RED).build()).queue();
        }
        net.dv8tion.jda.api.entities.User user = event.getUser();
        utils.counter(user.getId(), user.getAsTag());
    }
}
