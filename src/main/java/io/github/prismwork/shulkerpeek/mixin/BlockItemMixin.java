package io.github.prismwork.shulkerpeek.mixin;

import net.minecraft.client.item.BundleTooltipData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.inventory.Inventories;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {
	public BlockItemMixin(Settings settings) {
		super(settings);
	}

	@Override
	public Optional<TooltipData> getTooltipData(ItemStack stack) {
		if (Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock) {
			NbtCompound nbt = BlockItem.getBlockEntityNbtFromStack(stack);
			if (nbt != null) {
				DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
				Inventories.readNbt(nbt, inventory);
				return Optional.of(new BundleTooltipData(inventory, inventory.size()));
			}
		}
		return super.getTooltipData(stack);
	}
}
