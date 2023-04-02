package yawlick.ru.luckytest;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum LBType {
    COMMMON(Arrays.asList(new ItemStack(Material.APPLE, 1),
            new ItemStack(Material.CHAINMAIL_CHESTPLATE, 123))),

    RARE(Arrays.asList(new ItemStack(Material.STICK, 1))),

    EPIC(Arrays.asList(new ItemStack(Material.APPLE, 1)));

    private final List<ItemStack> drop;

    LBType(List<ItemStack> drops) {
        this.drop = drops;
    }

    public List<ItemStack> getDrop() {
        return drop;
    }
}
