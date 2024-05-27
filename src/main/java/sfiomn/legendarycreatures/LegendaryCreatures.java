package sfiomn.legendarycreatures;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sfiomn.legendarycreatures.config.Config;
import sfiomn.legendarycreatures.config.json.JsonConfigRegistration;
import sfiomn.legendarycreatures.entities.render.*;
import sfiomn.legendarycreatures.registry.*;
import sfiomn.legendarycreatures.world.ModEntityPlacement;
import software.bernie.geckolib3.GeckoLib;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LegendaryCreatures.MOD_ID)
public class LegendaryCreatures
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "legendarycreatures";

    // Check if shaders are loaded or not. Necessary for the glowing effect. If shader loaded, shaderpack will manage the glowing effect.
    public static boolean optifineLoaded = false;
    public static boolean atmosphericLoaded = false;

    public static Path configPath = FMLPaths.CONFIGDIR.get();
    public static Path modConfigPath = Paths.get(configPath.toAbsolutePath().toString(), "legendarycreatures");
    public static Path modConfigJson = Paths.get(modConfigPath.toString(), "json");

    public LegendaryCreatures() {

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        BlockRegistry.register(modBus);
        EffectRegistry.register(modBus);
        EntityTypeRegistry.register(modBus);
        ItemRegistry.register(modBus);
        ParticleTypeRegistry.register(modBus);
        SoundRegistry.register(modBus);

        Config.register();
        Config.Baked.bakeCommon();
        JsonConfigRegistration.init(LegendaryCreatures.modConfigJson.toFile());

        // Register the setup method for modloading
        modBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        modBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        modBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        modBus.addListener(this::doClientStuff);

        GeckoLib.initialize();

        forgeBus.addListener(this::reloadJsonConfig);

        // Register ourselves for server and other game events we are interested in
        forgeBus.register(this);
        modIntegration();
    }

    private void modIntegration() {

        atmosphericLoaded = ModList.get().isLoaded("atmospheric");

        if (atmosphericLoaded)
            LOGGER.debug("Atmospheric is loaded, enabling compatibility");
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ModEntityPlacement.spawnPlacement();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        try {
            Class.forName("net.optifine.Config");
            optifineLoaded = true;
        } catch (ClassNotFoundException e) {
            optifineLoaded = false;
        }

        if (optifineLoaded)
            LOGGER.debug("Optifine is loaded, disabling custom glowing shader");

        event.enqueueWork(() ->
        {
            RenderTypeLookup.setRenderLayer(BlockRegistry.DOOM_FIRE_BLOCK.get(), RenderType.cutout());
        });

        DistExecutor.safeRunWhenOn(Dist.CLIENT, LegendaryCreatures::registerEntityRendering);
    }

    private static DistExecutor.SafeRunnable registerEntityRendering() {

        return new DistExecutor.SafeRunnable()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void run()
            {
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.DESERT_MOJO.get(), DesertMojoRenderer::new);
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.FOREST_MOJO.get(), ForestMojoRenderer::new);
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.HOUND.get(), HoundRenderer::new);
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.SCARECROW.get(), ScarecrowRenderer::new);
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.SCORPION.get(), ScorpionRenderer::new);
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.SCORPION_BABY.get(), ScorpionBabyRenderer::new);
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.WISP.get(), WispRenderer::new);
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.WISP_PURSE.get(), WispPurseRenderer::new);
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.CORPSE_EATER.get(), CorpseEaterRenderer::new);
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.PEACOCK_SPIDER.get(), PeacockSpiderRenderer::new);
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.BULLFROG.get(), BullfrogRenderer::new);
            }
        };
    }

    private void reloadJsonConfig(final AddReloadListenerEvent event)
    {
        event.addListener(new ReloadListener<Void>()
              {
                  @Nonnull
                  @ParametersAreNonnullByDefault
                  @Override
                  protected Void prepare(IResourceManager manager, IProfiler profiler)
                  {
                      JsonConfigRegistration.clearContainers();
                      return null;
                  }

                  @ParametersAreNonnullByDefault
                  @Override
                  protected void apply(Void objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn)
                  {
                      Config.Baked.bakeCommon();
                      JsonConfigRegistration.init(modConfigJson.toFile());
                  }

              }
        );
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
