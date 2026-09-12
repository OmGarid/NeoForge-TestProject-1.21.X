    package net.fxrydarmament.testmod.firearm;

    import com.google.gson.Gson;
    import com.google.gson.GsonBuilder;
    import com.google.gson.JsonElement;
    import com.google.gson.JsonObject;
    import net.minecraft.resources.ResourceLocation;
    import net.minecraft.server.packs.resources.ResourceManager;
    import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
    import net.minecraft.util.profiling.ProfilerFiller;

    import java.util.HashMap;
    import java.util.Map;

    /*
        Data Reader

        This Class read firearm data from JSON files, located at:
        root://src/main/resources/data/fxrydarmament/firearm/*.json

        Then itll be stored as New Object, the required/available data at FireArmData.java Class.(Same dir with this class)

     */

    public class FireArmDataLoader extends SimpleJsonResourceReloadListener {

        private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        private static final Map<ResourceLocation, FireArmData> LOADED_WEAPONS = new HashMap<>();

        public FireArmDataLoader() {
            super(GSON, "firearm");
        }

        @Override
        protected void apply(
                Map<ResourceLocation, JsonElement> jsonMap,
                ResourceManager resourceManager,
                ProfilerFiller profiler
        ) {
            LOADED_WEAPONS.clear();

            // Check for any firearm data files (json) available in designated directory
            for (Map.Entry<ResourceLocation, JsonElement> entry : jsonMap.entrySet()) {
                ResourceLocation fileId = entry.getKey();
                JsonObject json = entry.getValue().getAsJsonObject();

                try {
                    ResourceLocation weaponId = fileId;

                    String weaponName = json.get("weapon_name").getAsString();

                    String weaponDesc = json.has("weapon_desc")
                            ? json.get("weapon_desc").getAsString()
                            : "";

                    int magazineCapacity = json.get("magazine_capacity").getAsInt();
                    int damage = json.get("damage").getAsInt();
                    int fireRate = json.get("fire_rate").getAsInt();
                    int reloadTime = json.get("reload_time").getAsInt();
                    int reloadEmptyTime = json.get("reload_empty_time").getAsInt();

                    // Create Firearm data object
                    FireArmData data = new FireArmData(
                            weaponId,
                            weaponName,
                            weaponDesc,
                            magazineCapacity,
                            damage,
                            fireRate,
                            reloadTime,
                            reloadEmptyTime
                    );

                    LOADED_WEAPONS.put(weaponId, data);

                } catch (Exception e) {
                    System.err.println(
                            "Failed to load firearm data from "
                                    + fileId
                                    + ": "
                                    + e.getMessage()
                    );
                }
            }

            System.out.println(
                    "Loaded " + LOADED_WEAPONS.size() + " firearm(s)."
            );
        }

        public static FireArmData get(ResourceLocation weaponId) {
            return LOADED_WEAPONS.get(weaponId);
        }

        public static Map<ResourceLocation, FireArmData> getAll() {
            return LOADED_WEAPONS;
        }
    }