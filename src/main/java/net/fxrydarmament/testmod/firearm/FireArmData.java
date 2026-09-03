package net.fxrydarmament.testmod.firearm;

public class FireArmData {

    private final String weaponId;
    private final String weaponName;
    private final String weaponDesc;
    private final int magazineCapacity;
    private final int damage;
    private final int fireRate;


    // Getter
    public FireArmData(String weaponId, String weaponName, String weaponDesc,
                       int magazineCapacity, int damage, int fireRate) {
        this.weaponId = weaponId;
        this.weaponName = weaponName;
        this.weaponDesc = weaponDesc;
        this.magazineCapacity = magazineCapacity;
        this.damage = damage;
        this.fireRate = fireRate;
    }

    public String getWeaponId() {
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
}
