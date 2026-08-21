package org.teamvoided.voidlib.helpers

import net.minecraft.world.phys.Vec3


fun Vec3.map(func: (Double) -> Double): Vec3 = Vec3(func(this.x), func(this.y), func(this.z))