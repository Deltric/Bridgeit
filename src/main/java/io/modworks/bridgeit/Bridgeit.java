package io.modworks.bridgeit;

import java.util.Collection;
import java.util.function.Consumer;

import org.apache.logging.log4j.Logger;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Bridgeit.MODID, name = Bridgeit.NAME, version = Bridgeit.VERSION, acceptableRemoteVersions = "*")
public class Bridgeit
{
	
    public static final String MODID = "bridgeit";
    public static final String NAME = "Bridgeit";
    public static final String VERSION = "1.0.0";
    
    @Instance
    public static Bridgeit INSTANCE;

    private Multimap<String, Consumer> values = HashMultimap.create();
    private Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onForgeEvent(Event event)
    {
    	Collection<Consumer> executors = values.get(event.getClass().getName());
    	if(!executors.isEmpty())
    	{
    		for(Consumer consumer : executors)
        	{
        		consumer.accept(event);
        	}
    	}
    }
    
    /**
     * Adds consumer as a listener to the class event
     * @param <T>
     * @param targetEventClass
     * @param executeConsumer
     */
    public <T extends Event> void addListener(Class<? extends T> targetEventClass, Consumer<T> executeConsumer)
    {
    	values.put(targetEventClass.getName(), executeConsumer);
    	logger.info(String.format("Added new listener for %s", targetEventClass.getName()));
    }
    
}