package de.blutmondgilde.blutmondrpg.capabilities.modclass;

import de.blutmondgilde.blutmondrpg.enums.BasicClasses;
import de.blutmondgilde.blutmondrpg.enums.ClassLevel;

public class ModClass implements IModClass {
    private BasicClasses basicClass;
    private ClassLevel classLevel;
    private double classExp;
    private float maxHP;
    private float maxMana;
    private float mana;

    public ModClass() {
        this.basicClass = BasicClasses.NONE;
        this.classLevel = ClassLevel.L1;
        this.classExp = 0;
        this.maxHP = basicClass.getBaseHP();
        this.maxMana = basicClass.getBaseMana();
        this.mana = 0;
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
    }

    @Override
    public ClassLevel getClassLevel() {
        return classLevel;
    }

    @Override
    public void setClassLevel(ClassLevel level) {
        this.classLevel = level;
    }

    @Override
    public void setClassLevel(int level) {
        this.classLevel = ClassLevel.getLevelFromId(level);
    }

    @Override
    public void addClassLevel(int amount) {
        if (this.classLevel.getId() + amount > ClassLevel.getMaxLevel().getId()) return; //deny overleveling
        if (this.classLevel.getId() + amount < this.classLevel.getId()) return; //deny downleveling
        this.classLevel = ClassLevel.getLevelFromId(this.classLevel.getId() + amount);
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
        if (this.classExp + exp >= this.getClassLevel().getExp()) {
            double nextLevelExp = this.classExp + exp - this.getClassLevel().getExp();
            classLevelUp();
            setClassExp(nextLevelExp);
        }
        this.classExp += exp;
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
}
