package net.fxrydarmament.testmod.firearm;

import net.minecraft.resources.ResourceLocation;

public class FireArmData {

    private final ResourceLocation weaponId;
    private final String weaponName;
    private final String weaponDesc;
    private final int magazineCapacity;
    private final int damage;
    private final int fireRate;
    private final int reloadTime;
    private final int reloadEmptyTime;

    // Constructor
    public FireArmData(
            ResourceLocation weaponId,
            String weaponName,
            String weaponDesc,
            int magazineCapacity,
            int damage,
            int fireRate,
            int reloadTime,
            int reloadEmptyTime
    ) {
        this.weaponId = weaponId;
        this.weaponName = weaponName;
        this.weaponDesc = weaponDesc;
        this.magazineCapacity = magazineCapacity;
        this.damage = damage;
        this.fireRate = fireRate;
        this.reloadTime = reloadTime;
        this.reloadEmptyTime = reloadEmptyTime;
    }

    // Getter
    public ResourceLocation getWeaponId() {
        return weaponId;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public String getWeaponDesc() {
        return weaponDesc;
    }

    public int getMagazineCapacity() {
        return magazineCapacity;
    }

    public int getDamage() {
        return damage;
    }

    public int getFireRate() {
        return fireRate;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public int getReloadEmptyTime() {
        return reloadEmptyTime;
    }
}