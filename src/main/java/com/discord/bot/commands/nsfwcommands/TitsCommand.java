package com.discord.bot.commands.nsfwcommands;

import com.discord.bot.commands.ISlashCommand;
import com.discord.bot.entity.Post;
import com.discord.bot.service.PostService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.List;
import java.util.Random;

public class TitsCommand implements ISlashCommand {
    PostService postService;
    NsfwCommandUtils utils;

    Random random = new Random();

    public TitsCommand(PostService postService, NsfwCommandUtils utils) {
        this.postService = postService;
        this.utils = utils;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        List<Post> postList = postService.getTits();
        Post post = postList.get(random.nextInt(postList.size()));
        utils.checkTypeAndPost(event, post);
        net.dv8tion.jda.api.entities.User user = event.getUser();
        utils.counter(user.getId(), user.getAsTag(), true);
    }
}
