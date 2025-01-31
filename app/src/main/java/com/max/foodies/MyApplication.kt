package com.max.foodies

import android.app.Application
import android.content.Context
import com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory
import com.max.foodies.data.repositories.CartRepositoryImpl
import com.max.foodies.screens.cartScreen.CartViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication: Application()