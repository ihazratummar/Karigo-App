package com.karigo.share.model




enum class TradeType (
    val displayName: String,
    val description: String
){
    PLUMBER(displayName = "Plumber", "Pipes, fittings, taps & repairs"),
    ELECTRICIAN(displayName = "Electrician", "Wiring, panels & fixtures"),
    CARPENTER(displayName = "Carpenter", "Woodwork, doors & furniture"),
    PAINTER(displayName = "Painter", "Interior & exterior painting"),
    MASON(displayName = "Mason & Contractor", "Brickwork, tiles & concrete"),
    GRILL(displayName = "Grill & Fabricator", "Steel, grills & gates"),
    AC_TECHNICIAN(displayName = "AC Technician", "AC service, repair & installation"),
    APPLIANCE_REPAIR(displayName = "Appliance Repair", "Washers, fridges & home appliances"),
    WATERPROOFING(displayName = "Waterproofing", "Leakage & dampness prevention"),
    PEST_CONTROL(displayName = "Pest Control", "Termite, insect & rodent treatment"),
    CAR_BIKE_MECHANIC(displayName = "Car & Bike Mechanic", "Vehicle repair & maintenance"),
    CCTV_SECURITY(displayName = "CCTV & Security", "Cameras, sensors & alarms"),
    SOLAR_INSTALLER(displayName = "Solar Installer", "Panels, inverters & batteries"),
    ALUMINIUM_UPVC(displayName = "Aluminium & UPVC", "Windows, doors & partitions"),
    CIVIL_CONTRACTOR(displayName = "Civil Contractor", "Small construction projects"),
    BORE_WELL(displayName = "Bore Well & Water Tank", "Drilling, pumps & maintenance"),
    GAS_LPG_FITTER(displayName = "Gas & LPG Fitter", "Pipelines, stoves & repairs"),
    NETWORK_SUPPORT(displayName = "Network & IT Support", "Cabling, routers & WiFi")
}
