package de.blutmondgilde.blutmondrpg.capabilities.modclass;

import de.blutmondgilde.blutmondrpg.enums.BasicClasses;
import de.blutmondgilde.blutmondrpg.enums.ClassLevel;

public class ModClass implements IModClass {
    private BasicClasses basicClass;
    private ClassLevel classLevel;
    private double classExp;
    private float maxHP, maxMana, mana, meleeDmg, magicDmg, bowDmg;

    public ModClass() {
        this.basicClass = BasicClasses.NONE;
        this.classLevel = ClassLevel.L1;
        this.classExp = 0;
        this.maxHP = basicClass.getBaseHP();
        this.maxMana = basicClass.getBaseMana();
        this.mana = 0;
        this.meleeDmg = basicClass.getBaseMeleeDamage();
        this.magicDmg = basicClass.getBaseMagicalDamage();
        this.bowDmg = basicClass.getBaseBowDamage();
    }

    @Override
    public BasicClasses getBasicClass() {
        return this.basicClass;
    }

    @Override
    public void setBasicClass(int id) {
        setBasicClass(BasicClasses.getById(id));
    }

    @Override
    public void setBasicClass(BasicClasses basicClass) {
        this.basicClass = basicClass;
        setMaxHP(basicClass.getBaseHP());
        setClassExp(0);
        setClassLevel(ClassLevel.L1);
        setMaxMana(basicClass.getBaseMana());
        setCurrentMana(0);
    }

    @Override
    public ClassLevel getClassLevel() {
        return classLevel;
    }

    @Override
    public void setClassLevel(ClassLevel level) {
        this.classLevel = level;
        recalculateStats();
    }

    private void recalculateStats() {
        this.meleeDmg = calcStat(this.basicClass.getBaseMeleeDamage(), this.basicClass.getMeleeScaling());
        this.magicDmg = calcStat(this.basicClass.getBaseMagicalDamage(), this.basicClass.getMagicDamageScaling());
        this.bowDmg = calcStat(this.basicClass.getBaseBowDamage(), this.basicClass.getBowDamageScaling());
        this.maxHP = calcStat(this.basicClass.getBaseHP(), this.basicClass.getHpScaling());
        this.maxMana = calcStat(this.basicClass.getBaseMana(), this.basicClass.getManaScaling());
    }

    private float calcStat(float base, float scaling) {
        return base * this.getClassLevel().getId() * scaling;
    }

    @Override
    public void setClassLevel(int level) {
        setClassLevel(ClassLevel.getLevelFromId(level));
    }

    @Override
    public void addClassLevel(int amount) {
        if (this.classLevel.getId() + amount > ClassLevel.getMaxLevel().getId()) return; //deny overleveling
        if (this.classLevel.getId() + amount < this.classLevel.getId()) return; //deny downleveling
        setClassLevel(ClassLevel.getLevelFromId(this.classLevel.getId() + amount));
    }

    @Override
    public void classLevelUp() {
        addClassLevel(1);
    }

    @Override
    public double getClassExp() {
        return this.classExp;
    }

    @Override
    public void setClassExp(double exp) {
        this.classExp = exp;
    }

    @Override
    public void addClassExp(double exp) {
        //If player is max level just add the exp
        if (this.classLevel.equals(ClassLevel.getMaxLevel())) {
            this.classExp += exp;
            return;
        }
        //If the player can level up with the exp then level up and add the remaining exp else just add exp
        if (this.classExp + exp >= ClassLevel.getLevelFromId(this.classLevel.getId() + 1).getExp()) {
            double nextLevelExp = this.classExp + exp - ClassLevel.getLevelFromId(this.getClassLevel().getId() + 1).getExp();
            classLevelUp();
            setClassExp(nextLevelExp);
        } else {
            this.classExp += exp;
        }
    }

    @Override
    public float getMaxHP() {
        return this.maxHP;
    }

    @Override
    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }

    @Override
    public float getMaxMana() {
        return this.maxMana;
    }

    @Override
    public void setMaxMana(float maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    public float getCurrentMana() {
        return this.mana;
    }

    @Override
    public void setCurrentMana(float mana) {
        this.mana = mana;
    }

    @Override
    public void useMana(float amount) {
        if (getCurrentMana() < amount) return;
        this.mana -= amount;
    }

    @Override
    public void addMana(float amount) {
        if (getCurrentMana() + amount > getMaxMana()) {
            this.mana = getMaxMana();
        } else {
            this.mana += amount;
        }
    }

    @Override
    public void setMeleeDmg(float amount) {
        this.meleeDmg = amount;
    }

    @Override
    public float getMeleeDmg() {
        return this.meleeDmg;
    }

    @Override
    public void setMagicDmg(float amount) {
        this.magicDmg = amount;
    }

    @Override
    public float getMagicDmg() {
        return this.magicDmg;
    }

    @Override
    public void setBowDmg(float amount) {
        this.bowDmg = amount;
    }

    @Override
    public float getBowDmg() {
        return this.bowDmg;
    }
}
