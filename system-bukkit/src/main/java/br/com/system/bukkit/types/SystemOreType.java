package br.com.system.bukkit.types;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SystemOreType {

  COAL(XMaterial.COAL, XMaterial.COAL_BLOCK, XMaterial.COAL_ORE, XMaterial.DEEPSLATE_COAL_ORE),
  IRON_INGOT(XMaterial.IRON_INGOT,XMaterial.IRON_BLOCK, XMaterial.IRON_ORE, XMaterial.DEEPSLATE_IRON_ORE),
  GOLD_INGOT(XMaterial.GOLD_INGOT, XMaterial.GOLD_BLOCK, XMaterial.GOLD_ORE, XMaterial.DEEPSLATE_GOLD_ORE),
  GOLD_NUGGET(XMaterial.GOLD_NUGGET, XMaterial.GOLD_INGOT),
  DIAMOND(XMaterial.DIAMOND, XMaterial.DIAMOND_BLOCK, XMaterial.DIAMOND_ORE, XMaterial.DEEPSLATE_DIAMOND_ORE),
  EMERALD(XMaterial.EMERALD, XMaterial.EMERALD_BLOCK, XMaterial.EMERALD_ORE, XMaterial.DEEPSLATE_EMERALD_ORE),
  REDSTONE(XMaterial.REDSTONE, XMaterial.REDSTONE_BLOCK, XMaterial.REDSTONE_ORE, XMaterial.DEEPSLATE_REDSTONE_ORE),
  COPPER(XMaterial.COPPER_INGOT, XMaterial.COPPER_BLOCK, XMaterial.COPPER_ORE, XMaterial.DEEPSLATE_COPPER_ORE),
  LAPIS(XMaterial.LAPIS_LAZULI, XMaterial.LAPIS_BLOCK, XMaterial.LAPIS_ORE, XMaterial.DEEPSLATE_LAPIS_ORE);

  private final XMaterial material;
  private final XMaterial result;
  private final XMaterial[] ores;

  SystemOreType(XMaterial material, XMaterial result, XMaterial... ores) {
    this.material = material;
    this.result = result;
    this.ores = ores;
  }

  public static SystemOreType findSingle(XMaterial material) {
    return Arrays.stream(values())
     .filter(ore -> ore.material == material)
     .findAny()
     .orElse(null);
  }

  public static SystemOreType findAny(XMaterial material) {
    return Arrays.stream(values())
     .filter(ore -> ore.material == material || Arrays.stream(ore.ores).anyMatch(o -> o == material) || ore.result == material)
     .findAny()
     .orElse(null);
  }
}