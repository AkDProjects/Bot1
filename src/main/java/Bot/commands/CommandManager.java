package Bot.commands;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        switch (command){
            case "combineeveryone":
            {
                    StringBuilder memAll = new StringBuilder();
                    Role role1 = event.getOption("role1").getAsRole();
                    Role role2 = event.getOption("role2").getAsRole();
                    Guild guild = role1.getGuild();
                    System.out.println(guild.getMembersWithRoles(role1,role2));


                    List<Member> members = guild.getMembersWithRoles(role1,role2);
                    if(members.size() == 0){
                        event.reply("There are no members in one/or both of the roles to ping!").setEphemeral(true).queue();
                    }else{
                    for (Member member:members)
                    {
                        memAll.append(member.getAsMention());
                    } event.reply(memAll.toString()).queue();}
            break;
        }
            case "roles":
            {
                //run the '/roles' command
                event.deferReply().queue();//waits longer than 3 seconds before discord closes its queue. USE FOR API/HTTP REQUESTS TO DISCORD!
                String response = "";
                for (Role role : event.getGuild().getRoles()) {//Enhanced for loop that cycles through every role in the server
                    if (!role.getName().equals("@everyone")){
                        response += role.toString() + "\n";
                    }

                }
                event.getHook().sendMessage(response).queue();
            }
            break;
            case "combinehere":
            {
                StringBuilder mem = new StringBuilder();
              Role role1 = event.getOption("role1").getAsRole();
              Role role2 = event.getOption("role2").getAsRole();
              Guild guild = role1.getGuild();
              System.out.println(guild.getMembersWithRoles(role1,role2));

                List<Member> members = guild.getMembersWithRoles(role1,role2);

                members.removeIf(a->a.getOnlineStatus() != (OnlineStatus.ONLINE));
                if (members.size() == 0){
                    event.reply("There are no members in one/or both of the roles to ping!").setEphemeral(true).queue();
                }else{
                for (Member member:members)
                   mem.append(member.getAsMention());
               } event.reply(mem.toString()).queue();}

               

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        ArrayList <CommandData> commandData = new ArrayList();
        //NEED TO ADD NEW COMMANDS METHOD TO COMMAND LIST TO RUN IT ON BOT
        commandData.add(Commands.slash("roles","Display all roles on Sever"));
        commandData.add(Commands.slash("combinehere","Combining Roles of People Online!").addOption(OptionType.ROLE,"role1","first role",true).addOption(OptionType.ROLE,"role2","second role",true));
        commandData.add(Commands.slash("combineeveryone", "Combining Roles of People, Regardless of Status!").addOption(OptionType.ROLE,"role1", "first role",true ).addOption(OptionType.ROLE,"role2","second role", true));
        event.getJDA().updateCommands().addCommands(commandData).queue();
    }
}

