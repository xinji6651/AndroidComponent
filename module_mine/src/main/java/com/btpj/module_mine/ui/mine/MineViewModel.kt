package com.btpj.module_mine.ui.mine

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.data.bean.CoinInfo
import com.btpj.lib_base.ext.launch
import com.btpj.lib_base.data.bean.User
import com.btpj.lib_base.data.local.UserManager
import com.btpj.lib_base.ext.handleRequest
import com.btpj.module_mine.data.DataRepository

class MineViewModel : BaseViewModel() {
    val user = ObservableField<User?>()
    val integral = MutableLiveData<CoinInfo?>()

    val userName = object : ObservableField<String>(user) {
        override fun get(): String {
            return if (UserManager.isLogin()) user.get()!!.nickname else "请登录"
        }
    }

    override fun start() {
        if (UserManager.isLogin()) {
            user.set(UserManager.getUser())
        }
    }

    /** 获取个人积分 */
    fun fetchPoints() {
        launch({
            val response = DataRepository.getUserIntegral()
            handleRequest(response, {
                integral.value = response.data
            })
        })
    }
}