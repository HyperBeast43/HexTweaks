package net.walksanator.hextweaks.patterns

import at.petrak.hexcasting.api.spell.ConstMediaAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.spell.orNull
import net.minecraft.network.chat.Component
import net.walksanator.hextweaks.HexTweaks
import net.walksanator.hextweaks.iotas.DictionaryIota
import net.walksanator.hextweaks.iotas.HextweaksIotaType
import net.walksanator.hextweaks.mishap.MishapDictionaryTooBig

class OpDictSet : ConstMediaAction {
    override val argc = 3
    override val isGreat = false

    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        val dict = args[0]
        val key = args[1].orNull()
        val value = args[2].orNull()
        if (dict.type == HextweaksIotaType.DICTIONARY) {
            if (HexTweaks.cannotBeDictKey.contains(key.javaClass)) {
                throw MishapInvalidIota(key,1, Component.translatable("hextweaks.mishap.cannotbekey"))
            } else {
                if ((dict as DictionaryIota).payload.first.size >= HexTweaks.MaxKeysInDictIota) {
                    throw MishapDictionaryTooBig(dict)
                }
                dict.set(key,value,ctx.caster)
                return listOf(dict)
            }
        } else {
            throw MishapInvalidIota(dict,2, Component.translatable("hextweaks.mishap.notadict"))
        }
    }


}