package com.gufli.treasurechests.app.data.beans;

import com.gufli.treasurechests.app.data.converters.ItemStackConverter;
import io.ebean.annotation.ConstraintMode;
import io.ebean.annotation.DbDefault;
import io.ebean.annotation.DbForeignKey;
import org.bukkit.inventory.ItemStack;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "treasure_loot")
public class BTreasureLoot extends BModel {

    @Id
    private UUID id;

    @ManyToOne(targetEntity = BTreasureChest.class)
    @DbForeignKey(onDelete = ConstraintMode.CASCADE)
    public BTreasureChest chest;

    @Convert(converter = ItemStackConverter.class)
    @Column(length = 65535, columnDefinition = "TEXT")
    private final ItemStack item;

    private int chance = 100;

    public BTreasureLoot(BTreasureChest chest, ItemStack item) {
        this.chest = chest;
        this.item = item;
    }

    @Override
    public boolean delete() {
        chest.removeLoot(this);
        return super.delete();
    }

    // getters

    public ItemStack item() {
        return item;
    }

    public int chance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    //

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof BTreasureLoot bl) && bl.id.equals(id);
    }

}