import Bot.commands.CommandManager;
import Listeners.Evelistener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Bot {

    //Loads in token and other options for
    private final Dotenv config;



    private final ShardManager shardManager;


    //Constructor for bot. Gives Default options to Bot on it's creation
    public Bot () throws LoginException {

        config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token); //Passing Token code to build and instantce of bot
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(GatewayIntent.DIRECT_MESSAGE_REACTIONS,GatewayIntent.DIRECT_MESSAGES,GatewayIntent.DIRECT_MESSAGES,GatewayIntent.MESSAGE_CONTENT,GatewayIntent.GUILD_MEMBERS,GatewayIntent.GUILD_PRESENCES,GatewayIntent.GUILD_MESSAGES);//Enabling your bot to handle MESSAGES AND MESSAGE REACTIONS
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ONLINE_STATUS);
        shardManager = builder.build();

        // Register Listener, this is how you tell the shardmanager to listen to this class // ADD EVERYTIME, EVENT BASED
        shardManager.addEventListener(new Evelistener(), new CommandManager());
    }


  //Getter that retrieves variables from .env file

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main (String[]args) {
        try{
            //Initilizes new bot object, will start to do everything in bot constructor above
        Bot bot = new Bot();
    } catch (LoginException e){
            System.out.println("Error: Provided bot token is invalid");//Check CONFIG file
        }
    }
}
