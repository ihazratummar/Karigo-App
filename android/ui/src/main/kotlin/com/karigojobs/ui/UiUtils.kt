package com.karigojobs.ui

import androidx.compose.ui.graphics.Color
import com.karigojobs.app.android.ui.R
import com.karigojobs.share.model.TradeType


/**
 * @author hazratummar
 * Created on 21/05/26
 */




fun TradeType.icon () : Int {
    return when(this){
        TradeType.PLUMBER -> R.drawable.plumber
        TradeType.ELECTRICIAN -> R.drawable.electrician
        TradeType.CARPENTER ->  R.drawable.carpenter
        TradeType.PAINTER -> R.drawable.painter
        TradeType.MASON -> R.drawable.mason
        TradeType.GRILL -> R.drawable.grill
        TradeType.AC_TECHNICIAN -> R.drawable.ac_technician
        TradeType.APPLIANCE_REPAIR -> R.drawable.appliance_repair
        TradeType.WATERPROOFING -> R.drawable.water_proffing
        TradeType.PEST_CONTROL -> R.drawable.pest_control
        TradeType.CAR_BIKE_MECHANIC -> R.drawable.mechanic
        TradeType.CCTV_SECURITY -> R.drawable.cctv
        TradeType.SOLAR_INSTALLER -> R.drawable.solar
        TradeType.ALUMINIUM_UPVC -> R.drawable.alumuniam
        TradeType.CIVIL_CONTRACTOR -> R.drawable.civil_contractor
        TradeType.BORE_WELL -> R.drawable.bore_well_water
        TradeType.GAS_LPG_FITTER -> R.drawable.gas_lpg_fitter
        TradeType.NETWORK_SUPPORT -> R.drawable.network_it_support
    }
}

fun TradeType.color() : Color {
    return when(this){
        TradeType.PLUMBER -> Color(0xFF3b82f6)
        TradeType.ELECTRICIAN -> Color(0xFFed990c)
        TradeType.CARPENTER -> Color(0xFF8b5cf6)
        TradeType.PAINTER -> Color(0xFFd6438b)
        TradeType.MASON -> Color(0xFFef4544)
        TradeType.GRILL -> Color(0xFF6366f0)
        TradeType.AC_TECHNICIAN -> Color(0xFF09b6d4)
        TradeType.APPLIANCE_REPAIR -> Color(0xFF15a998)
        TradeType.WATERPROOFING -> Color(0xFF1195d0)
        TradeType.PEST_CONTROL -> Color(0xFF10b981)
        TradeType.CAR_BIKE_MECHANIC -> Color(0xFFf17016)
        TradeType.CCTV_SECURITY -> Color(0xFF64748b)
        TradeType.SOLAR_INSTALLER -> Color(0xFFca9c0c)
        TradeType.ALUMINIUM_UPVC -> Color(0xFF94a3b8)
        TradeType.CIVIL_CONTRACTOR -> Color(0xFF69635f)
        TradeType.BORE_WELL -> Color(0xFF0d9488)
        TradeType.GAS_LPG_FITTER -> Color(0xFFdc2626)
        TradeType.NETWORK_SUPPORT -> Color(0xFF234ba7)
    }
}