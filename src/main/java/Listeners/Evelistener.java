package Listeners;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Evelistener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        User user = event.getUser();// creation of user object
        String emoji = event.getReaction().getEmoji().getAsReactionCode(); //Emoji object will get the reaction as an emoji code
        String channelMention = event.getChannel().getAsMention(); // creation of Channel object
        String jumpLink = event.getJumpUrl();

        String message = user.getAsTag() + "reacted to a message with" + emoji + "in the" + channelMention + "channel!";
        event.getGuildChannel().asTextChannel().sendMessage(message).queue();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();//Gets the message that was sent in the discord channel
        if(message.equalsIgnoreCase("send")) {
            event.getChannel().sendMessage("Recieved").queue();///WILL NOT WORK WITHOUT GATEWAY INTENT
    }

        }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {//WILL NOT WORK WITHOUT GATEWAY INTENT
        User id = (User) User.fromId("121112216651956226");//THIS NEEDS TO BE THE ROLE OF THE MEMBER
        Role role = event.getGuild().getRoleById(String.valueOf((id)));///Have to find a way to get the ID of a user
        if (role != null){//ID IS AN INT BUT I NEED TO PASS A LONG TO ADD A ROLE TO A MEMBER? FUCK YOU
            event.getGuild().addRoleToMember(event.getMember(),role);
        }

        String avatar = event.getUser().getEffectiveAvatarUrl();
        System.out.println(avatar);
    }

    @Override
    public void onUserUpdateOnlineStatus(@NotNull UserUpdateOnlineStatusEvent event) {
        //WILL NOT WORK WITHOUT USER CACHE
        User user = event.getUser();
        String message = user.getAsTag() + "Updated their online status!";
       // event.getGuild().getDefaultChannel().
    }
}


//HOW TO