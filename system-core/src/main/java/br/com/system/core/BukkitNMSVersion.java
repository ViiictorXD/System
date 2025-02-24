package br.com.system.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public enum BukkitNMSVersion {

  V1_22(22, "v1.22"),
  V1_21(21, "v1.21"),
  V1_20(20, "v1.20"),
  V1_19(19, "v1.19"),
  V1_18(18, "v1.18"),
  V1_17(17, "v1.17"),
  V1_16(16, "v1.16"),
  V1_15(15, "v1.15"),
  V1_14(14, "v1.14"),
  V1_13(13, "v1.13"),
  V1_12(12, "v1.12"),
  V1_11(11, "v1.11"),
  V1_10(10, "v1.10"),
  V1_9(9, "v1.9"),
  V1_8(8, "v1.8"),
  V1_7(7, "v1.7"),
  UNKNOWN(-1, "Desconhecida");

  private final int versionId;
  private final String simpleName;

  public boolean higherThan(@NotNull BukkitNMSVersion version) {
    return versionId > version.getVersionId();
  }

  public static BukkitNMSVersion getCurrentVersion() {
    return directFrom(Bukkit.getVersion());
  }

  public static BukkitNMSVersion directFrom(@NotNull String packageName) {
    if (packageName.contains("1.22")) return V1_22;
    if (packageName.contains("1.21")) return V1_21;
    if (packageName.contains("1.20")) return V1_20;
    if (packageName.contains("1.19")) return V1_19;
    if (packageName.contains("1.18")) return V1_18;
    if (packageName.contains("1.17")) return V1_17;
    if (packageName.contains("1.16")) return V1_16;
    if (packageName.contains("1.15")) return V1_15;
    if (packageName.contains("1.14")) return V1_14;
    if (packageName.contains("1.13")) return V1_13;
    if (packageName.contains("1.12")) return V1_12;
    if (packageName.contains("1.11")) return V1_11;
    if (packageName.contains("1.10")) return V1_10;
    if (packageName.contains("1.9")) return V1_9;
    if (packageName.contains("1.8")) return V1_8;
    return UNKNOWN;
  }
}
