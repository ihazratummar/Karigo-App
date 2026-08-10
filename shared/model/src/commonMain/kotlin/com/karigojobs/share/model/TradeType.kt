package com.karigojobs.share.model

import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.trade_ac_technician
import karigojobs.shared.resources.generated.resources.trade_ac_technician_description
import karigojobs.shared.resources.generated.resources.trade_aluminium_upvc
import karigojobs.shared.resources.generated.resources.trade_aluminium_upvc_description
import karigojobs.shared.resources.generated.resources.trade_appliance_repair
import karigojobs.shared.resources.generated.resources.trade_appliance_repair_description
import karigojobs.shared.resources.generated.resources.trade_bore_well
import karigojobs.shared.resources.generated.resources.trade_bore_well_description
import karigojobs.shared.resources.generated.resources.trade_car_bike_mechanic
import karigojobs.shared.resources.generated.resources.trade_car_bike_mechanic_description
import karigojobs.shared.resources.generated.resources.trade_carpenter
import karigojobs.shared.resources.generated.resources.trade_carpenter_description
import karigojobs.shared.resources.generated.resources.trade_cctv_security
import karigojobs.shared.resources.generated.resources.trade_cctv_security_description
import karigojobs.shared.resources.generated.resources.trade_civil_contractor
import karigojobs.shared.resources.generated.resources.trade_civil_contractor_description
import karigojobs.shared.resources.generated.resources.trade_electrician
import karigojobs.shared.resources.generated.resources.trade_electrician_description
import karigojobs.shared.resources.generated.resources.trade_gas_lpg_fitter
import karigojobs.shared.resources.generated.resources.trade_gas_lpg_fitter_description
import karigojobs.shared.resources.generated.resources.trade_grill
import karigojobs.shared.resources.generated.resources.trade_grill_description
import karigojobs.shared.resources.generated.resources.trade_mason
import karigojobs.shared.resources.generated.resources.trade_mason_description
import karigojobs.shared.resources.generated.resources.trade_network_support
import karigojobs.shared.resources.generated.resources.trade_network_support_description
import karigojobs.shared.resources.generated.resources.trade_painter
import karigojobs.shared.resources.generated.resources.trade_painter_description
import karigojobs.shared.resources.generated.resources.trade_pest_control
import karigojobs.shared.resources.generated.resources.trade_pest_control_description
import karigojobs.shared.resources.generated.resources.trade_plumber
import karigojobs.shared.resources.generated.resources.trade_plumber_description
import karigojobs.shared.resources.generated.resources.trade_solar_installer
import karigojobs.shared.resources.generated.resources.trade_solar_installer_description
import karigojobs.shared.resources.generated.resources.trade_waterproofing
import karigojobs.shared.resources.generated.resources.trade_waterproofing_description
import org.jetbrains.compose.resources.StringResource


enum class TradeType(
    val displayNameRes: StringResource,
    val descriptionRes: StringResource
) {
    PLUMBER(
        displayNameRes = Res.string.trade_plumber,
        descriptionRes = Res.string.trade_plumber_description
    ),

    ELECTRICIAN(
        displayNameRes = Res.string.trade_electrician,
        descriptionRes = Res.string.trade_electrician_description
    ),

    CARPENTER(
        displayNameRes = Res.string.trade_carpenter,
        descriptionRes = Res.string.trade_carpenter_description
    ),

    PAINTER(
        displayNameRes = Res.string.trade_painter,
        descriptionRes = Res.string.trade_painter_description
    ),

    MASON(
        displayNameRes = Res.string.trade_mason,
        descriptionRes = Res.string.trade_mason_description
    ),

    GRILL(
        displayNameRes = Res.string.trade_grill,
        descriptionRes = Res.string.trade_grill_description
    ),

    AC_TECHNICIAN(
        displayNameRes = Res.string.trade_ac_technician,
        descriptionRes = Res.string.trade_ac_technician_description
    ),

    APPLIANCE_REPAIR(
        displayNameRes = Res.string.trade_appliance_repair,
        descriptionRes = Res.string.trade_appliance_repair_description
    ),

    WATERPROOFING(
        displayNameRes = Res.string.trade_waterproofing,
        descriptionRes = Res.string.trade_waterproofing_description
    ),

    PEST_CONTROL(
        displayNameRes = Res.string.trade_pest_control,
        descriptionRes = Res.string.trade_pest_control_description
    ),

    CAR_BIKE_MECHANIC(
        displayNameRes = Res.string.trade_car_bike_mechanic,
        descriptionRes = Res.string.trade_car_bike_mechanic_description
    ),

    CCTV_SECURITY(
        displayNameRes = Res.string.trade_cctv_security,
        descriptionRes = Res.string.trade_cctv_security_description
    ),

    SOLAR_INSTALLER(
        displayNameRes = Res.string.trade_solar_installer,
        descriptionRes = Res.string.trade_solar_installer_description
    ),

    ALUMINIUM_UPVC(
        displayNameRes = Res.string.trade_aluminium_upvc,
        descriptionRes = Res.string.trade_aluminium_upvc_description
    ),

    CIVIL_CONTRACTOR(
        displayNameRes = Res.string.trade_civil_contractor,
        descriptionRes = Res.string.trade_civil_contractor_description
    ),

    BORE_WELL(
        displayNameRes = Res.string.trade_bore_well,
        descriptionRes = Res.string.trade_bore_well_description
    ),

    GAS_LPG_FITTER(
        displayNameRes = Res.string.trade_gas_lpg_fitter,
        descriptionRes = Res.string.trade_gas_lpg_fitter_description
    ),

    NETWORK_SUPPORT(
        displayNameRes = Res.string.trade_network_support,
        descriptionRes = Res.string.trade_network_support_description
    );

    companion object {
        /** "PLUMBER, ELECTRICIAN" -> [PLUMBER, ELECTRICIAN] */
        fun fromDbString(csv: String?) : List<TradeType> {
            return csv.orEmpty()
                .split(",")
                .mapNotNull { runCatching { valueOf(it.trim()) }.getOrNull() }
        }

        /** [PLUMBER, ELECTRICIAN] → "PLUMBER,ELECTRICIAN" */
        fun List<TradeType>.toDbString(types: List<TradeType>): String {
            return types.joinToString(",") { it.name }
        }
    }
}
