package sfiomn.legendarycreatures;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sfiomn.legendarycreatures.config.Config;
import sfiomn.legendarycreatures.config.json.JsonConfigRegistration;
import sfiomn.legendarycreatures.entities.render.*;
import sfiomn.legendarycreatures.level.gen.ModEntityPlacement;
import sfiomn.legendarycreatures.particles.CorpseSplatter;
import sfiomn.legendarycreatures.particles.CrowsParticle;
import sfiomn.legendarycreatures.particles.DesertMojoParticle;
import sfiomn.legendarycreatures.particles.WispParticle;
import sfiomn.legendarycreatures.registry.*;
import software.bernie.geckolib.GeckoLib;

import java.nio.file.Path;
import java.nio.file.Paths;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LegendaryCreatures.MOD_ID)
public class LegendaryCreatures
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "legendarycreatures";

    // Check if shaders are loaded or not. Necessary for the glowing effect. If shader loaded, shaderpack will manage the glowing effect, so revert to basic minecraft glowing effect.
    public static boolean optifineLoaded = false;
    public static boolean oculusLoaded = false;

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
        CreativeTabRegistry.register(modBus);

        Config.register();

        // Register the setup method for modloading
        modBus.addListener(this::setup);

        GeckoLib.initialize();

        forgeBus.addListener(this::onModConfigLoadEvent);

        // Register ourselves for server and other game events we are interested in
        forgeBus.register(this);
        modIntegration();
    }

    private void modIntegration() {

        atmosphericLoaded = ModList.get().isLoaded("atmospheric");
        oculusLoaded = ModList.get().isLoaded("oculus");

        if (atmosphericLoaded)
            LOGGER.debug("Atmospheric is loaded, enabling compatibility");

        if (oculusLoaded)
            LOGGER.debug("Oculus is loaded, enabling compatibility");
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        Config.Baked.bakeCommon();
        JsonConfigRegistration.init(LegendaryCreatures.modConfigJson.toFile());
        event.enqueueWork(ModEntityPlacement::spawnPlacement);
    }

    private void onModConfigLoadEvent(ModConfigEvent.Loading event)
    {
        final ModConfig config = event.getConfig();

        if (config.getSpec() == Config.COMMON_SPEC)
            Config.Baked.bakeCommon();
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            try {
                Class.forName("net.optifine.Config");
                optifineLoaded = true;
            } catch (ClassNotFoundException e) {
                optifineLoaded = false;
            }

            if (optifineLoaded)
                LOGGER.debug("Optifine is loaded, disabling custom glowing shader");
        }

        @SubscribeEvent
        public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers  event) {
            event.registerEntityRenderer(EntityTypeRegistry.DESERT_MOJO.get(), DesertMojoRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.FOREST_MOJO.get(), ForestMojoRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.HOUND.get(), HoundRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.SCARECROW.get(), ScarecrowRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.SCORPION.get(), ScorpionRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.SCORPION_BABY.get(), ScorpionBabyRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.WISP.get(), WispRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.NETHER_WISP.get(), NetherWispRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.ENDER_WISP.get(), EnderWispRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.WISP_PURSE.get(), WispPurseRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.NETHER_WISP_PURSE.get(), NetherWispPurseRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.ENDER_WISP_PURSE.get(), EnderWispPurseRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.CORPSE_EATER.get(), CorpseEaterRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.PEACOCK_SPIDER.get(), PeacockSpiderRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.BULLFROG.get(), BullfrogRenderer::new);
        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ParticleTypeRegistry.CORPSE_SPLATTER.get(), CorpseSplatter.Factory::new);
            event.registerSpriteSet(ParticleTypeRegistry.WISP_PARTICLE.get(), WispParticle.Factory::new);
            event.registerSpriteSet(ParticleTypeRegistry.DESERT_MOJO_PARTICLE.get(), DesertMojoParticle.Factory::new);
            event.registerSpriteSet(ParticleTypeRegistry.CROWS_PARTICLE.get(), CrowsParticle.Factory::new);
        }
    }
}
