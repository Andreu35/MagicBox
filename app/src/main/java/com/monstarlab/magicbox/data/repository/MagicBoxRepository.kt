package com.monstarlab.magicbox.data.repository

import com.monstarlab.magicbox.data.remote.MagicBoxDataSource
import javax.inject.Inject

class MagicBoxRepository @Inject constructor(private val dataSource: MagicBoxDataSource) {

}