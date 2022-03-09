package com.discord.bot.commands.musiccommands;

import com.discord.bot.commands.ISlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class MusicHelpCommand implements ISlashCommand {
    MusicCommandUtils utils;

    public MusicHelpCommand(MusicCommandUtils utils) {
        this.utils = utils;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("Music Commands").setDescription("""
                        - /play
                        - /skip
                        - /pause
                        - /resume
                        - /leave
                        - /queue
                        - /swap
                        - /shuffle
                        """)
                .setFooter("Bot can't play age restricted videos and shorts.");
        event.replyEmbeds(embedBuilder.build()).queue();
        net.dv8tion.jda.api.entities.User user = event.getUser();
        utils.counter(user.getId(), user.getAsTag());
    }
}
