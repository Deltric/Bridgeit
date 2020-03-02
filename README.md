# Bridgeit
Bridgeit is a simple mod that allows for registering of event listeners outside the Forge environment. This mod was made to allow Bukkit plugins run in Forge/Bukkit Hybrid servers (Catserver, Magma, etc) to be able to listen to events from Forge within the plugin.

If you need help or want to keep up with other projects I'm working on, feel free to join the Modworks [Discord Server](https://discord.ggZjAHjDC).

## Quick Overview
Bridgeit listens for events and checks if an event has ant registered listener's to it which are Consumers. If there's any listeners to a certain event class then on fired it'll execute all the registered consumers. Which solves the issues with Forge not being able to find Bukkit classes or proper mods registering it, etc. Bridgeit supports any event that's called on the **MinecraftForge EventBus** and extends from Forge's **Event** class.


## Code Example
You can add a new listener by calling **addListener** on the **Bridgeit main instance**. Which as arguments you pass in the class of the event you want to listen to and then a consumer, that'll be called for the execution of the event.

In this example we'll be adding a new listener to the ExperienceGainEvent from [Pixelmon Generations](https://pixelmongenerations.com/).

```java
public class ExampleProject extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable()
	{	
		// Registers the listener to the event to Bridgeit	
		Bridgeit.INSTANCE.addListener(ExperienceGainEvent.class, ExampleProject::onExperienceGain);
	}
	
	public static void onExperienceGain(ExperienceGainEvent event)
	{
		// If the Pokemon's species is a Eevee
		if(event.getPokemon().getSpecies() == EnumSpecies.Eevee)
		{
			// Set experience gain to 3000 points
			event.setExperience(3000);
		}
	}
	
}
```