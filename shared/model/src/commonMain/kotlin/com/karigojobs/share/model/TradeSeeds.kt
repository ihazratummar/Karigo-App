package com.karigojobs.share.model

object TradeSeeds {

    val plumber: List<MaterialsModel> = listOf(
        MaterialsModel(name = "PVC Pipe 1/2\"", unit = "metre", price =  45.0, tradeType =  TradeType.PLUMBER),
        MaterialsModel(name = "PVC Pipe 3/4\"", unit = "metre", price =  70.0, tradeType =  TradeType.PLUMBER),
        MaterialsModel(name = "CPVC Pipe 1/2\"", unit = "metre", price = 85.0, tradeType =  TradeType.PLUMBER),
        MaterialsModel(name = "Elbow 1/2\"", unit = "piece", price = 12.0, tradeType = TradeType.PLUMBER),
        MaterialsModel(name = "Tee 1/2\"", unit = "piece", price = 15.0, tradeType = TradeType.PLUMBER),
        MaterialsModel(name = "Coupling 1/2\"", unit = "piece", price = 10.0, tradeType = TradeType.PLUMBER),
        MaterialsModel(name = "Ball Valve 1/2\"", unit = "piece", price = 120.0, tradeType = TradeType.PLUMBER),
        MaterialsModel(name = "Bib Tap", unit = "piece", price = 180.0, tradeType = TradeType.PLUMBER),
        MaterialsModel(name = "PVC Glue", unit = "can", price = 95.0, tradeType = TradeType.PLUMBER),
        MaterialsModel(name = "PTFE Tape", unit = "roll", price = 25.0, tradeType = TradeType.PLUMBER)
    )

    val electrician: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Copper Wire 1.5 sqmm", unit = "metre", price = 22.0, tradeType = TradeType.ELECTRICIAN),
        MaterialsModel(name = "Copper Wire 2.5 sqmm", unit = "metre", price = 35.0, tradeType = TradeType.ELECTRICIAN),
        MaterialsModel(name = "Conduit Pipe 20mm", unit = "metre", price = 18.0, tradeType = TradeType.ELECTRICIAN),
        MaterialsModel(name = "Conduit Bend", unit = "piece", price = 8.0, tradeType = TradeType.ELECTRICIAN),
        MaterialsModel(name = "Switch 6A", unit = "piece", price = 65.0, tradeType = TradeType.ELECTRICIAN),
        MaterialsModel(name = "Socket 6A", unit = "piece", price = 75.0, tradeType = TradeType.ELECTRICIAN),
        MaterialsModel(name = "MCB 16A", unit = "piece", price = 220.0, tradeType = TradeType.ELECTRICIAN),
        MaterialsModel(name = "Distribution Box", unit = "piece", price = 650.0, tradeType = TradeType.ELECTRICIAN),
        MaterialsModel(name = "Ceiling Rose", unit = "piece", price = 28.0, tradeType = TradeType.ELECTRICIAN),
        MaterialsModel(name = "Insulation Tape", unit = "roll", price = 20.0, tradeType = TradeType.ELECTRICIAN)
    )

    val carpenter: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Plywood Sheet 8x4", unit = "sheet", price = 1450.0, tradeType = TradeType.CARPENTER),
        MaterialsModel(name = "Sunmica Sheet", unit = "sheet", price = 950.0, tradeType = TradeType.CARPENTER),
        MaterialsModel(name = "Wood Screw Set", unit = "box", price = 180.0, tradeType = TradeType.CARPENTER),
        MaterialsModel(name = "Nail Set", unit = "kg", price = 120.0, tradeType = TradeType.CARPENTER),
        MaterialsModel(name = "Wood Glue", unit = "bottle", price = 160.0, tradeType = TradeType.CARPENTER),
        MaterialsModel(name = "Hinge", unit = "piece", price = 35.0, tradeType = TradeType.CARPENTER),
        MaterialsModel(name = "Drawer Slider", unit = "pair", price = 280.0, tradeType = TradeType.CARPENTER),
        MaterialsModel(name = "Door Handle", unit = "piece", price = 220.0, tradeType = TradeType.CARPENTER),
        MaterialsModel(name = "Wood Polish", unit = "can", price = 240.0, tradeType = TradeType.CARPENTER),
        MaterialsModel(name = "Laminate Edge Tape", unit = "roll", price = 55.0, tradeType = TradeType.CARPENTER)
    )

    val painter: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Interior Emulsion Paint", unit = "litre", price = 260.0, tradeType = TradeType.PAINTER),
        MaterialsModel(name = "Exterior Paint", unit = "litre", price = 290.0, tradeType = TradeType.PAINTER),
        MaterialsModel(name = "Primer", unit = "litre", price = 180.0, tradeType = TradeType.PAINTER),
        MaterialsModel(name = "Wall Putty", unit = "kg", price = 45.0, tradeType = TradeType.PAINTER),
        MaterialsModel(name = "Paint Roller", unit = "piece", price = 120.0, tradeType = TradeType.PAINTER),
        MaterialsModel(name = "Brush 2 inch", unit = "piece", price = 60.0, tradeType = TradeType.PAINTER),
        MaterialsModel(name = "Masking Tape", unit = "roll", price = 85.0, tradeType = TradeType.PAINTER),
        MaterialsModel(name = "Thinner", unit = "litre", price = 140.0, tradeType = TradeType.PAINTER),
        MaterialsModel(name = "Paint Tray", unit = "piece", price = 75.0, tradeType = TradeType.PAINTER),
        MaterialsModel(name = "Sandpaper Sheet", unit = "piece", price = 12.0, tradeType = TradeType.PAINTER)
    )

    val mason: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Cement", unit = "bag", price = 420.0, tradeType = TradeType.MASON),
        MaterialsModel(name = "Sand", unit = "cft", price = 55.0, tradeType = TradeType.MASON),
        MaterialsModel(name = "Bricks", unit = "piece", price = 8.0, tradeType = TradeType.MASON),
        MaterialsModel(name = "Aggregate", unit = "cft", price = 60.0, tradeType = TradeType.MASON),
        MaterialsModel(name = "Tile Adhesive", unit = "bag", price = 380.0, tradeType = TradeType.MASON),
        MaterialsModel(name = "White Cement", unit = "bag", price = 450.0, tradeType = TradeType.MASON),
        MaterialsModel(name = "Binding Wire", unit = "kg", price = 95.0, tradeType = TradeType.MASON),
        MaterialsModel(name = "Rod 8mm", unit = "kg", price = 68.0, tradeType = TradeType.MASON),
        MaterialsModel(name = "Pop Powder", unit = "bag", price = 320.0, tradeType = TradeType.MASON),
        MaterialsModel(name = "Concrete Block", unit = "piece", price = 45.0, tradeType = TradeType.MASON)
    )

    val grill: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Mild Steel Pipe", unit = "metre", price = 180.0, tradeType = TradeType.GRILL),
        MaterialsModel(name = "MS Square Tube", unit = "metre", price = 210.0, tradeType = TradeType.GRILL),
        MaterialsModel(name = "Flat Bar", unit = "metre", price = 95.0, tradeType = TradeType.GRILL),
        MaterialsModel(name = "Angle Iron", unit = "metre", price = 140.0, tradeType = TradeType.GRILL),
        MaterialsModel(name = "Welding Rod", unit = "kg", price = 220.0, tradeType = TradeType.GRILL),
        MaterialsModel(name = "Cutting Disc", unit = "piece", price = 55.0, tradeType = TradeType.GRILL),
        MaterialsModel(name = "Grinding Disc", unit = "piece", price = 65.0, tradeType = TradeType.GRILL),
        MaterialsModel(name = "Primer Paint", unit = "litre", price = 190.0, tradeType = TradeType.GRILL),
        MaterialsModel(name = "Hinges Heavy Duty", unit = "pair", price = 240.0, tradeType = TradeType.GRILL),
        MaterialsModel(name = "Lock Set", unit = "set", price = 320.0, tradeType = TradeType.GRILL)
    )

    val acTechnician: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Copper Pipe 1/4\"", unit = "metre", price = 220.0, tradeType = TradeType.AC_TECHNICIAN),
        MaterialsModel(name = "Copper Pipe 3/8\"", unit = "metre", price = 280.0, tradeType = TradeType.AC_TECHNICIAN),
        MaterialsModel(name = "AC Gas R32", unit = "kg", price = 950.0, tradeType = TradeType.AC_TECHNICIAN),
        MaterialsModel(name = "AC Gas R22", unit = "kg", price = 780.0, tradeType = TradeType.AC_TECHNICIAN),
        MaterialsModel(name = "Insulation Foam", unit = "metre", price = 35.0, tradeType = TradeType.AC_TECHNICIAN),
        MaterialsModel(name = "Drain Pipe", unit = "metre", price = 18.0, tradeType = TradeType.AC_TECHNICIAN),
        MaterialsModel(name = "AC Bracket", unit = "pair", price = 650.0, tradeType = TradeType.AC_TECHNICIAN),
        MaterialsModel(name = "Copper Nut", unit = "piece", price = 22.0, tradeType = TradeType.AC_TECHNICIAN),
        MaterialsModel(name = "Service Valve", unit = "piece", price = 260.0, tradeType = TradeType.AC_TECHNICIAN),
        MaterialsModel(name = "Contact Cleaner", unit = "can", price = 240.0, tradeType = TradeType.AC_TECHNICIAN)
    )

    val applianceRepair: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Thermostat", unit = "piece", price = 220.0, tradeType = TradeType.APPLIANCE_REPAIR),
        MaterialsModel(name = "Capacitor 2.5uF", unit = "piece", price = 85.0, tradeType = TradeType.APPLIANCE_REPAIR),
        MaterialsModel(name = "Capacitor 5uF", unit = "piece", price = 95.0, tradeType = TradeType.APPLIANCE_REPAIR),
        MaterialsModel(name = "Capacitor 10uF", unit = "piece", price = 110.0, tradeType = TradeType.APPLIANCE_REPAIR),
        MaterialsModel(name = "Relay", unit = "piece", price = 140.0, tradeType = TradeType.APPLIANCE_REPAIR),
        MaterialsModel(name = "Fuse", unit = "piece", price = 15.0, tradeType = TradeType.APPLIANCE_REPAIR),
        MaterialsModel(name = "Wire Harness", unit = "set", price = 180.0, tradeType = TradeType.APPLIANCE_REPAIR),
        MaterialsModel(name = "PCB Cleaner", unit = "can", price = 260.0, tradeType = TradeType.APPLIANCE_REPAIR),
        MaterialsModel(name = "Motor Belt", unit = "piece", price = 160.0, tradeType = TradeType.APPLIANCE_REPAIR),
        MaterialsModel(name = "Bearing Set", unit = "set", price = 240.0, tradeType = TradeType.APPLIANCE_REPAIR)
    )

    val waterproofing: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Waterproof Coating", unit = "kg", price = 180.0, tradeType = TradeType.WATERPROOFING),
        MaterialsModel(name = "Acrylic Sealant", unit = "tube", price = 120.0, tradeType = TradeType.WATERPROOFING),
        MaterialsModel(name = "Bitumen Roll", unit = "roll", price = 950.0, tradeType = TradeType.WATERPROOFING),
        MaterialsModel(name = "PU Sealant", unit = "tube", price = 220.0, tradeType = TradeType.WATERPROOFING),
        MaterialsModel(name = "Bonding Agent", unit = "litre", price = 260.0, tradeType = TradeType.WATERPROOFING),
        MaterialsModel(name = "Crack Filler", unit = "kg", price = 140.0, tradeType = TradeType.WATERPROOFING),
        MaterialsModel(name = "Mesh Tape", unit = "roll", price = 75.0, tradeType = TradeType.WATERPROOFING),
        MaterialsModel(name = "Primer", unit = "litre", price = 190.0, tradeType = TradeType.WATERPROOFING),
        MaterialsModel(name = "Drainage Nozzle", unit = "piece", price = 55.0, tradeType = TradeType.WATERPROOFING),
        MaterialsModel(name = "Membrane Sheet", unit = "square feet", price = 18.0, tradeType = TradeType.WATERPROOFING)
    )

    val pestControl: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Termiticide", unit = "litre", price = 850.0, tradeType = TradeType.PEST_CONTROL),
        MaterialsModel(name = "Insecticide Spray", unit = "litre", price = 420.0, tradeType = TradeType.PEST_CONTROL),
        MaterialsModel(name = "Rodent Bait", unit = "piece", price = 35.0, tradeType = TradeType.PEST_CONTROL),
        MaterialsModel(name = "Gel Bait", unit = "tube", price = 260.0, tradeType = TradeType.PEST_CONTROL),
        MaterialsModel(name = "Fogging Chemical", unit = "litre", price = 780.0, tradeType = TradeType.PEST_CONTROL),
        MaterialsModel(name = "Hand Sprayer", unit = "piece", price = 650.0, tradeType = TradeType.PEST_CONTROL),
        MaterialsModel(name = "Protective Gloves", unit = "pair", price = 90.0, tradeType = TradeType.PEST_CONTROL),
        MaterialsModel(name = "Mask", unit = "piece", price = 25.0, tradeType = TradeType.PEST_CONTROL),
        MaterialsModel(name = "Pest Trap", unit = "piece", price = 120.0, tradeType = TradeType.PEST_CONTROL),
        MaterialsModel(name = "Bait Station", unit = "piece", price = 180.0, tradeType = TradeType.PEST_CONTROL)
    )

    val carBikeMechanic: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Engine Oil", unit = "litre", price = 420.0, tradeType = TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel(name = "Gear Oil", unit = "litre", price = 380.0, tradeType = TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel(name = "Air Filter", unit = "piece", price = 160.0, tradeType = TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel(name = "Oil Filter", unit = "piece", price = 140.0, tradeType = TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel(name = "Spark Plug", unit = "piece", price = 120.0, tradeType = TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel(name = "Brake Pad Set", unit = "set", price = 650.0, tradeType = TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel(name = "Chain Lube", unit = "can", price = 180.0, tradeType = TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel(name = "Battery Water", unit = "bottle", price = 25.0, tradeType = TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel(name = "Grease", unit = "kg", price = 220.0, tradeType = TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel(name = "Coolant", unit = "litre", price = 240.0, tradeType = TradeType.CAR_BIKE_MECHANIC)
    )

    val cctvSecurity: List<MaterialsModel> = listOf(
        MaterialsModel(name = "CCTV Camera", unit = "piece", price = 1850.0, tradeType = TradeType.CCTV_SECURITY),
        MaterialsModel(name = "DVR", unit = "piece", price = 4200.0, tradeType = TradeType.CCTV_SECURITY),
        MaterialsModel(name = "NVR", unit = "piece", price = 6500.0, tradeType = TradeType.CCTV_SECURITY),
        MaterialsModel(name = "BNC Connector", unit = "piece", price = 18.0, tradeType = TradeType.CCTV_SECURITY),
        MaterialsModel(name = "Power Adapter", unit = "piece", price = 220.0, tradeType = TradeType.CCTV_SECURITY),
        MaterialsModel(name = "Cat6 Cable", unit = "metre", price = 22.0, tradeType = TradeType.CCTV_SECURITY),
        MaterialsModel(name = "Hard Disk", unit = "piece", price = 3200.0, tradeType = TradeType.CCTV_SECURITY),
        MaterialsModel(name = "Power Supply Box", unit = "piece", price = 950.0, tradeType = TradeType.CCTV_SECURITY),
        MaterialsModel(name = "Junction Box", unit = "piece", price = 65.0, tradeType = TradeType.CCTV_SECURITY),
        MaterialsModel(name = "IR Bulb", unit = "piece", price = 140.0, tradeType = TradeType.CCTV_SECURITY)
    )

    val solarInstaller: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Solar Panel 550W", unit = "piece", price = 13500.0, tradeType = TradeType.SOLAR_INSTALLER),
        MaterialsModel(name = "Solar Inverter", unit = "piece", price = 28000.0, tradeType = TradeType.SOLAR_INSTALLER),
        MaterialsModel(name = "Solar Battery", unit = "piece", price = 16500.0, tradeType = TradeType.SOLAR_INSTALLER),
        MaterialsModel(name = "Mounting Structure", unit = "set", price = 4200.0, tradeType = TradeType.SOLAR_INSTALLER),
        MaterialsModel(name = "Solar Cable", unit = "metre", price = 65.0, tradeType = TradeType.SOLAR_INSTALLER),
        MaterialsModel(name = "MC4 Connector", unit = "pair", price = 55.0, tradeType = TradeType.SOLAR_INSTALLER),
        MaterialsModel(name = "Charge Controller", unit = "piece", price = 3800.0, tradeType = TradeType.SOLAR_INSTALLER),
        MaterialsModel(name = "DC MCB", unit = "piece", price = 480.0, tradeType = TradeType.SOLAR_INSTALLER),
        MaterialsModel(name = "Earthing Kit", unit = "set", price = 950.0, tradeType = TradeType.SOLAR_INSTALLER),
        MaterialsModel(name = "SPD", unit = "piece", price = 1200.0, tradeType = TradeType.SOLAR_INSTALLER)
    )

    val aluminiumUpvc: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Aluminium Section", unit = "metre", price = 240.0, tradeType = TradeType.ALUMINIUM_UPVC),
        MaterialsModel(name = "UPVC Profile", unit = "metre", price = 180.0, tradeType = TradeType.ALUMINIUM_UPVC),
        MaterialsModel(name = "Glass Sheet", unit = "square feet", price = 85.0, tradeType = TradeType.ALUMINIUM_UPVC),
        MaterialsModel(name = "Rubber Beading", unit = "metre", price = 12.0, tradeType = TradeType.ALUMINIUM_UPVC),
        MaterialsModel(name = "Corner Joint", unit = "piece", price = 35.0, tradeType = TradeType.ALUMINIUM_UPVC),
        MaterialsModel(name = "Screw Set", unit = "box", price = 160.0, tradeType = TradeType.ALUMINIUM_UPVC),
        MaterialsModel(name = "Handle", unit = "piece", price = 220.0, tradeType = TradeType.ALUMINIUM_UPVC),
        MaterialsModel(name = "Roller Wheel", unit = "piece", price = 55.0, tradeType = TradeType.ALUMINIUM_UPVC),
        MaterialsModel(name = "Silicone Sealant", unit = "tube", price = 140.0, tradeType = TradeType.ALUMINIUM_UPVC),
        MaterialsModel(name = "Lock Set", unit = "set", price = 320.0, tradeType = TradeType.ALUMINIUM_UPVC)
    )

    val civilContractor: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Cement", unit = "bag", price = 420.0, tradeType = TradeType.CIVIL_CONTRACTOR),
        MaterialsModel(name = "Sand", unit = "cft", price = 55.0, tradeType = TradeType.CIVIL_CONTRACTOR),
        MaterialsModel(name = "Aggregate", unit = "cft", price = 60.0, tradeType = TradeType.CIVIL_CONTRACTOR),
        MaterialsModel(name = "Steel Rod 10mm", unit = "kg", price = 72.0, tradeType = TradeType.CIVIL_CONTRACTOR),
        MaterialsModel(name = "Steel Rod 12mm", unit = "kg", price = 74.0, tradeType = TradeType.CIVIL_CONTRACTOR),
        MaterialsModel(name = "Binding Wire", unit = "kg", price = 95.0, tradeType = TradeType.CIVIL_CONTRACTOR),
        MaterialsModel(name = "Shuttering Plywood", unit = "sheet", price = 1250.0, tradeType = TradeType.CIVIL_CONTRACTOR),
        MaterialsModel(name = "Nails", unit = "kg", price = 120.0, tradeType = TradeType.CIVIL_CONTRACTOR),
        MaterialsModel(name = "Concrete Admixture", unit = "litre", price = 280.0, tradeType = TradeType.CIVIL_CONTRACTOR),
        MaterialsModel(name = "Curing Compound", unit = "litre", price = 320.0, tradeType = TradeType.CIVIL_CONTRACTOR)
    )

    val boreWell: List<MaterialsModel> = listOf(
        MaterialsModel(name = "PVC Bore Pipe", unit = "metre", price = 125.0, tradeType = TradeType.BORE_WELL),
        MaterialsModel(name = "Submersible Cable", unit = "metre", price = 85.0, tradeType = TradeType.BORE_WELL),
        MaterialsModel(name = "Bore Clamp", unit = "piece", price = 45.0, tradeType = TradeType.BORE_WELL),
        MaterialsModel(name = "Foot Valve", unit = "piece", price = 320.0, tradeType = TradeType.BORE_WELL),
        MaterialsModel(name = "Water Tank Float", unit = "piece", price = 280.0, tradeType = TradeType.BORE_WELL),
        MaterialsModel(name = "Submersible Pump", unit = "piece", price = 8500.0, tradeType = TradeType.BORE_WELL),
        MaterialsModel(name = "Pressure Gauge", unit = "piece", price = 240.0, tradeType = TradeType.BORE_WELL),
        MaterialsModel(name = "GI Pipe", unit = "metre", price = 180.0, tradeType = TradeType.BORE_WELL),
        MaterialsModel(name = "Cable Joint Kit", unit = "set", price = 220.0, tradeType = TradeType.BORE_WELL),
        MaterialsModel(name = "Pump Starter Box", unit = "piece", price = 1650.0, tradeType = TradeType.BORE_WELL)
    )

    val gasLpgFitter: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Copper Gas Pipe", unit = "metre", price = 240.0, tradeType = TradeType.GAS_LPG_FITTER),
        MaterialsModel(name = "LPG Hose", unit = "metre", price = 85.0, tradeType = TradeType.GAS_LPG_FITTER),
        MaterialsModel(name = "Gas Regulator", unit = "piece", price = 380.0, tradeType = TradeType.GAS_LPG_FITTER),
        MaterialsModel(name = "Gas Valve", unit = "piece", price = 220.0, tradeType = TradeType.GAS_LPG_FITTER),
        MaterialsModel(name = "Clamp", unit = "piece", price = 18.0, tradeType = TradeType.GAS_LPG_FITTER),
        MaterialsModel(name = "Teflon Tape", unit = "roll", price = 25.0, tradeType = TradeType.GAS_LPG_FITTER),
        MaterialsModel(name = "Burner Nozzle", unit = "piece", price = 55.0, tradeType = TradeType.GAS_LPG_FITTER),
        MaterialsModel(name = "LPG Connector", unit = "piece", price = 140.0, tradeType = TradeType.GAS_LPG_FITTER),
        MaterialsModel(name = "Leak Detector Spray", unit = "can", price = 260.0, tradeType = TradeType.GAS_LPG_FITTER),
        MaterialsModel(name = "Gas Wrench", unit = "piece", price = 320.0, tradeType = TradeType.GAS_LPG_FITTER)
    )

    val networkSupport: List<MaterialsModel> = listOf(
        MaterialsModel(name = "Cat6 Cable", unit = "metre", price = 22.0, tradeType = TradeType.NETWORK_SUPPORT),
        MaterialsModel(name = "RJ45 Connector", unit = "piece", price = 12.0, tradeType = TradeType.NETWORK_SUPPORT),
        MaterialsModel(name = "Patch Cord", unit = "piece", price = 95.0, tradeType = TradeType.NETWORK_SUPPORT),
        MaterialsModel(name = "Router", unit = "piece", price = 2200.0, tradeType = TradeType.NETWORK_SUPPORT),
        MaterialsModel(name = "Switch 8 Port", unit = "piece", price = 1650.0, tradeType = TradeType.NETWORK_SUPPORT),
        MaterialsModel(name = "Access Point", unit = "piece", price = 2800.0, tradeType = TradeType.NETWORK_SUPPORT),
        MaterialsModel(name = "Crimping Tool", unit = "piece", price = 450.0, tradeType = TradeType.NETWORK_SUPPORT),
        MaterialsModel(name = "LAN Tester", unit = "piece", price = 780.0, tradeType = TradeType.NETWORK_SUPPORT),
        MaterialsModel(name = "Cable Tie", unit = "pack", price = 60.0, tradeType = TradeType.NETWORK_SUPPORT),
        MaterialsModel(name = "Wall Jack", unit = "piece", price = 85.0, tradeType = TradeType.NETWORK_SUPPORT)
    )

    val seeds: Map<TradeType, List<MaterialsModel>> = mapOf(
        TradeType.PLUMBER to plumber,
        TradeType.ELECTRICIAN to electrician,
        TradeType.CARPENTER to carpenter,
        TradeType.PAINTER to painter,
        TradeType.MASON to mason,
        TradeType.GRILL to grill,
        TradeType.AC_TECHNICIAN to acTechnician,
        TradeType.APPLIANCE_REPAIR to applianceRepair,
        TradeType.WATERPROOFING to waterproofing,
        TradeType.PEST_CONTROL to pestControl,
        TradeType.CAR_BIKE_MECHANIC to carBikeMechanic,
        TradeType.CCTV_SECURITY to cctvSecurity,
        TradeType.SOLAR_INSTALLER to solarInstaller,
        TradeType.ALUMINIUM_UPVC to aluminiumUpvc,
        TradeType.CIVIL_CONTRACTOR to civilContractor,
        TradeType.BORE_WELL to boreWell,
        TradeType.GAS_LPG_FITTER to gasLpgFitter,
        TradeType.NETWORK_SUPPORT to networkSupport
    )
}