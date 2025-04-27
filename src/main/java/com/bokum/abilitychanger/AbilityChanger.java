package com.bokum.abilitychanger;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.ability.Ability;
import com.pixelmonmod.pixelmon.api.pokemon.ability.AbilityRegistry;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class AbilityChanger
{
    public static void changeAbility(Pokemon pokemon, Ability ability)
    {
        pokemon.setAbility(ability);
    }

    public static boolean changeAbility(ServerPlayerEntity player, int slot, String ability_name)
    {
        PartyStorage party = StorageProxy.getParty(player);
        if(party == null)
        {
            player.sendMessage(new StringTextComponent("§c파티 정보를 가져올 수 없습니다."), player.getUUID());
            return false;
        }

        Pokemon pokemon = party.get(slot - 1);
        if(pokemon == null)
        {
            player.sendMessage(new StringTextComponent("§c" + (slot - 1) +"번째 포켓몬을 찾을 수 없습니다."), player.getUUID());
            return false;
        }

        Ability ability = AbilityRegistry.getAbility(ability_name).orElse(null);
        if(ability == null)
        {
            player.sendMessage(new StringTextComponent("§c" + ability_name +" 특성은 존재하지 않습니다."), player.getUUID());
            return false;
        }

        changeAbility(pokemon, ability);
        player.sendMessage(new StringTextComponent("§b" + slot +"§7번째에게 §b" + ability_name + "§7 특성을 적용했습니다."), player.getUUID());
        return true;
    }
}
