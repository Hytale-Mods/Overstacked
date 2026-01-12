package net.darkhax.overstacked;

import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.regex.Pattern;

public class Overstacked extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private final Config<MaxStackSizeConfig> cfg;

    public Overstacked(@Nonnull JavaPluginInit init) {
        super(init);
        this.cfg = this.withConfig("MaxStackSizes", MaxStackSizeConfig.CODEC);
    }

    @Override
    protected void setup() {
        this.getEventRegistry().register(LoadedAssetsEvent.class, Item.class, this::onItemAssetLoad);
        this.cfg.save();
    }

    private void onItemAssetLoad(@Nonnull LoadedAssetsEvent<String, Item, DefaultAssetMap<String, Item>> event) {
        try {
            final Map<String, Item> assets = event.getLoadedAssets();
            final Field maxStackSize = Item.class.getDeclaredField("maxStack");
            maxStackSize.setAccessible(true);
            for (Map.Entry<String, Integer> entry : cfg.get().itemIds.entrySet()) {
                final Item item = assets.get(entry.getKey());
                if (item != null) {
                    maxStackSize.set(item, entry.getValue());
                }
            }
            for (Map.Entry<String, Integer> entry : cfg.get().patterns.entrySet()) {
                final Pattern pattern = Pattern.compile(entry.getKey());
                for (String id : assets.keySet()) {
                    if (pattern.matcher(id).matches()) {
                        maxStackSize.set(assets.get(id), entry.getValue());
                    }
                }
            }
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}