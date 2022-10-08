package com.udacity.asteroidradar

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * This class is being used to avoid Glide's bug: Failed to find GeneratedAppGlideModule...
 * Solution: https://github.com/bumptech/glide/issues/4476#issuecomment-1113213037
 */
@GlideModule
class GlideAppModule : AppGlideModule()