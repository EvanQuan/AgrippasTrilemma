package game.object.item.background.person;

import game.object.item.background.BackgroundItem;
import game.object.item.collectable.Collectable;
import game.system.Inventory;

/**
 * Can be contained in rooms
 * May have inventories
 * Is either alive or dead
 */
public abstract class Person extends BackgroundItem {

    private static final long serialVersionUID = 1L;

    public static final int ALIVE = 0;
    public static final int DEAD = 1;
    public static final int DEFAULT_MAX_HEALTH = 100;
    public static final int DEFAULT_MAX_MANA = 100;
    public static final int DEFAULT_MAX_FULLNESS = 100;
    public static final int DEFAULT_MAX_CORRUPTION = DEFAULT_MAX_HEALTH - 1;
    public static final String DEFAULT_NAME = "Person";

    private Inventory<Collectable> inv;
    private String name;
    private boolean alive;

    private int maxHealth;
    private int health;
    private int maxMana;
    private int mana;
    private int maxFullness;
    private int fullness;
    private int maxCorruption;
    private int corruption;

    /**
    * Default empty Person constructor
    */
    public Person() {
        inv = new Inventory<Collectable>();
        name = DEFAULT_NAME;
        this.alive = true;
        maxHealth = DEFAULT_MAX_HEALTH;
        health = maxHealth;
        maxMana = DEFAULT_MAX_MANA;
        mana = maxMana;
        maxFullness = DEFAULT_MAX_FULLNESS;
        fullness = maxFullness;
        maxCorruption = DEFAULT_MAX_CORRUPTION;
        corruption = maxCorruption;
    }

    /**
    * Default Person constructor
    */
    public Person(Inventory<Collectable> inv, String name, boolean alive) {
        this.inv = inv;
        this.name = name;
        this.alive = alive;
    }

    public Person(String name) {
        this();
        this.name = name;
    }


    /**
    * Returns value of inv
    * @return
    */
    public Inventory<Collectable> getInv() {
        return this.inv;
    }

    /**
    * Returns value of name
    * @return
    */
    public String getName() {
        return this.name;
    }

    /**
    * Returns value of alive
    * @return
    */
    public boolean isAlive() {
        return this.alive;
    }

    /**
    * Sets new value of inv
    * @param
    */
    public void setInv(Inventory<Collectable> inv) {
        this.inv = inv;
    }

    /**
    * Sets new value of name
    * @param
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * Sets new value of alive
    * @param
    */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getFullness() {
        return fullness;
    }

    public int getCorruption() {
        return corruption;
    }

    public void setHealth(int health) {
        if (health < 0) {
            this.health = 0;
        } else if (health > maxHealth) {
            this.health = maxHealth;
        } else {
            this.health = health;
        }
    }
    public void addHealth(int health) {
        setHealth(this.health + health);
    }
    public void removeHealth(int health) {
        setHealth(this.health - health);
    }

    public void setMana(int mana) {
        if (mana < 0) {
            this.mana = 0;
        } else if (mana > maxMana) {
            this.mana = maxMana;
        } else {
            this.mana = mana;
        }
    }
    public void addMana(int mana) {
        setMana(this.mana + mana);
    }
    public void removeMana(int mana) {
        setMana(this.mana - mana);
    }

    public void setFullness(int fullness) {
        if (fullness < 0) {
            this.fullness = 0;
        } else if (fullness > maxFullness) {
            this.fullness = maxFullness;
        } else {
            this.fullness = fullness;
        }
    }
    public void addFullness(int fullness) {
        setFullness(this.fullness + fullness);
    }
    public void removeFullness(int fullness) {
        setFullness(this.fullness - fullness);
    }

    public void setCorruption(int corruption) {
        if (corruption < 0) {
            this.corruption = 0;
        } else if (corruption > maxCorruption) {
            this.corruption = maxCorruption;
        } else {
            this.corruption = corruption;
        }
    }
    public void addCorruption(int corruption) {
        setCorruption(this.corruption + corruption);
    }
    public void removeCorruption(int corruption) {
        setCorruption(this.corruption - corruption);
    }

    /**
     * Put collectable item in inventory
     * @param Collectable collectable to put inside inventory
     */
    public void take(Collectable collectable) {
        inv.add(collectable);
    }

    /**
     * Put multiple of item in inventory
     * @param int         count       of collectable
     * @param Collectable collectable to put inside inventory
     */
    public void take(int count, Collectable collectable) {
        inv.add(count,collectable);
    }

    public void drop(Collectable collectable) {
        inv.remove(collectable);
    }

    public void drop(int count, Collectable collectable) {
        inv.remove(count,collectable);
    }
}
