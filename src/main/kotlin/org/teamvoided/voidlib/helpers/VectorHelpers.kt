package org.teamvoided.voidlib.helpers

import net.minecraft.util.math.Vec3d

fun Vec3d.map(func: (Double) -> Double): Vec3d = Vec3d(func(this.x), func(this.y), func(this.z))