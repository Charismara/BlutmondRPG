package de.blutmondgilde.blutmondrpg.capabilities.modclass;

import de.blutmondgilde.blutmondrpg.enums.BasicClasses;
import de.blutmondgilde.blutmondrpg.enums.ClassLevel;

public interface IModClass {
    //Returns Class
    BasicClasses getBasicClass();

    //Sets Class
    void setBasicClass(int id);

    void setBasicClass(BasicClasses basicClasses);

    //Returns Class Level
    ClassLevel getClassLevel();

    //Sets Class Level
    void setClassLevel(ClassLevel level);

    void setClassLevel(int level);

    void addClassLevel(int amount);

    void classLevelUp();

    double getClassExp();

    void setClassExp(double exp);

    void addClassExp(double exp);

    float getMaxHP();

    void setMaxHP(float maxHP);

    float getMaxMana();

    void setMaxMana(float maxMana);

    float getCurrentMana();

    void setCurrentMana(float mana);

    void useMana(float amount);

    void addMana(float amount);
}
