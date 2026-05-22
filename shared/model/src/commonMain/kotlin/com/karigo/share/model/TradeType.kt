package com.karigo.share.model


enum class TradeType(
    val displayName: String,
    val description: String,
    val defaultLabourRate: Double = 0.0,
    val starterMaterial: List<StarterMaterial> = emptyList()

) {
    PLUMBER(
        displayName = "Plumber",
        description = "Pipes, fittings, taps & repairs",
        defaultLabourRate = 150.0,
        starterMaterial = TradeSeeds.plumber
    ),

    ELECTRICIAN(
        displayName = "Electrician",
        description = "Wiring, panels & fixtures",
        defaultLabourRate = 180.0,
        starterMaterial = TradeSeeds.electrician
    ),

    CARPENTER(
        displayName = "Carpenter",
        description = "Woodwork, doors & furniture",
        defaultLabourRate = 220.0,
        starterMaterial = TradeSeeds.carpenter
    ),

    PAINTER(
        displayName = "Painter",
        description = "Interior & exterior painting",
        defaultLabourRate = 140.0,
        starterMaterial = TradeSeeds.painter
    ),

    MASON(
        displayName = "Mason & Contractor",
        description = "Brickwork, tiles & concrete",
        defaultLabourRate = 200.0,
        starterMaterial = TradeSeeds.mason
    ),

    GRILL(
        displayName = "Grill & Fabricator",
        description = "Steel, grills & gates",
        defaultLabourRate = 250.0,
        starterMaterial = TradeSeeds.grill
    ),

    AC_TECHNICIAN(
        displayName = "AC Technician",
        description = "AC service, repair & installation",
        defaultLabourRate = 350.0,
        starterMaterial = TradeSeeds.acTechnician
    ),

    APPLIANCE_REPAIR(
        displayName = "Appliance Repair",
        description = "Washers, fridges & home appliances",
        defaultLabourRate = 300.0,
        starterMaterial = TradeSeeds.applianceRepair
    ),

    WATERPROOFING(
        displayName = "Waterproofing",
        description = "Leakage & dampness prevention",
        defaultLabourRate = 280.0,
        starterMaterial = TradeSeeds.waterproofing
    ),

    PEST_CONTROL(
        displayName = "Pest Control",
        description = "Termite, insect & rodent treatment",
        defaultLabourRate = 250.0,
        starterMaterial = TradeSeeds.pestControl
    ),

    CAR_BIKE_MECHANIC(
        displayName = "Car & Bike Mechanic",
        description = "Vehicle repair & maintenance",
        defaultLabourRate = 300.0,
        starterMaterial = TradeSeeds.carBikeMechanic
    ),

    CCTV_SECURITY(
        displayName = "CCTV & Security",
        description = "Cameras, sensors & alarms",
        defaultLabourRate = 350.0,
        starterMaterial = TradeSeeds.cctvSecurity
    ),

    SOLAR_INSTALLER(
        displayName = "Solar Installer",
        description = "Panels, inverters & batteries",
        defaultLabourRate = 500.0,
        starterMaterial = TradeSeeds.solarInstaller
    ),

    ALUMINIUM_UPVC(
        displayName = "Aluminium & UPVC",
        description = "Windows, doors & partitions",
        defaultLabourRate = 260.0,
        starterMaterial = TradeSeeds.aluminiumUpvc
    ),

    CIVIL_CONTRACTOR(
        displayName = "Civil Contractor",
        description = "Small construction projects",
        defaultLabourRate = 400.0,
        starterMaterial = TradeSeeds.civilContractor
    ),

    BORE_WELL(
        displayName = "Bore Well & Water Tank",
        description = "Drilling, pumps & maintenance",
        defaultLabourRate = 450.0,
        starterMaterial = TradeSeeds.boreWell
    ),

    GAS_LPG_FITTER(
        displayName = "Gas & LPG Fitter",
        description = "Pipelines, stoves & repairs",
        defaultLabourRate = 300.0,
        starterMaterial = TradeSeeds.gasLpgFitter
    ),

    NETWORK_SUPPORT(
        displayName = "Network & IT Support",
        description = "Cabling, routers & WiFi",
        defaultLabourRate = 350.0,
        starterMaterial = TradeSeeds.networkSupport
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
