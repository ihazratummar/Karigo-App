package com.karigojobs.share.model

object TradeSeeds {

    val plumber: List<MaterialsModel> = listOf(
        MaterialsModel(name = "PVC Pipe 1/2\"", unit = "metre", price =  45.0, tradeType =  TradeType.PLUMBER),
        MaterialsModel(name = "PVC Pipe 3/4\"", unit = "metre", price =  70.0, tradeType =  TradeType.PLUMBER),
        MaterialsModel("CPVC Pipe 1/2\"", "metre", price = 85.0, tradeType =  TradeType.PLUMBER),
        MaterialsModel("Elbow 1/2\"", "piece", 12.0, TradeType.PLUMBER),
        MaterialsModel("Tee 1/2\"", "piece", 15.0, TradeType.PLUMBER),
        MaterialsModel("Coupling 1/2\"", "piece", 10.0, TradeType.PLUMBER),
        MaterialsModel("Ball Valve 1/2\"", "piece", 120.0, TradeType.PLUMBER),
        MaterialsModel("Bib Tap", "piece", 180.0, TradeType.PLUMBER),
        MaterialsModel("PVC Glue", "can", 95.0, TradeType.PLUMBER),
        MaterialsModel("PTFE Tape", "roll", 25.0, TradeType.PLUMBER)
    )

    val electrician: List<MaterialsModel> = listOf(
        MaterialsModel("Copper Wire 1.5 sqmm", "metre", 22.0, TradeType.ELECTRICIAN),
        MaterialsModel("Copper Wire 2.5 sqmm", "metre", 35.0, TradeType.ELECTRICIAN),
        MaterialsModel("Conduit Pipe 20mm", "metre", 18.0, TradeType.ELECTRICIAN),
        MaterialsModel("Conduit Bend", "piece", 8.0, TradeType.ELECTRICIAN),
        MaterialsModel("Switch 6A", "piece", 65.0, TradeType.ELECTRICIAN),
        MaterialsModel("Socket 6A", "piece", 75.0, TradeType.ELECTRICIAN),
        MaterialsModel("MCB 16A", "piece", 220.0, TradeType.ELECTRICIAN),
        MaterialsModel("Distribution Box", "piece", 650.0, TradeType.ELECTRICIAN),
        MaterialsModel("Ceiling Rose", "piece", 28.0, TradeType.ELECTRICIAN),
        MaterialsModel("Insulation Tape", "roll", 20.0, TradeType.ELECTRICIAN)
    )

    val carpenter: List<MaterialsModel> = listOf(
        MaterialsModel("Plywood Sheet 8x4", "sheet", 1450.0, TradeType.CARPENTER),
        MaterialsModel("Sunmica Sheet", "sheet", 950.0, TradeType.CARPENTER),
        MaterialsModel("Wood Screw Set", "box", 180.0, TradeType.CARPENTER),
        MaterialsModel("Nail Set", "kg", 120.0, TradeType.CARPENTER),
        MaterialsModel("Wood Glue", "bottle", 160.0, TradeType.CARPENTER),
        MaterialsModel("Hinge", "piece", 35.0, TradeType.CARPENTER),
        MaterialsModel("Drawer Slider", "pair", 280.0, TradeType.CARPENTER),
        MaterialsModel("Door Handle", "piece", 220.0, TradeType.CARPENTER),
        MaterialsModel("Wood Polish", "can", 240.0, TradeType.CARPENTER),
        MaterialsModel("Laminate Edge Tape", "roll", 55.0, TradeType.CARPENTER)
    )

    val painter: List<MaterialsModel> = listOf(
        MaterialsModel("Interior Emulsion Paint", "litre", 260.0, TradeType.PAINTER),
        MaterialsModel("Exterior Paint", "litre", 290.0, TradeType.PAINTER),
        MaterialsModel("Primer", "litre", 180.0, TradeType.PAINTER),
        MaterialsModel("Wall Putty", "kg", 45.0, TradeType.PAINTER),
        MaterialsModel("Paint Roller", "piece", 120.0, TradeType.PAINTER),
        MaterialsModel("Brush 2 inch", "piece", 60.0, TradeType.PAINTER),
        MaterialsModel("Masking Tape", "roll", 85.0, TradeType.PAINTER),
        MaterialsModel("Thinner", "litre", 140.0, TradeType.PAINTER),
        MaterialsModel("Paint Tray", "piece", 75.0, TradeType.PAINTER),
        MaterialsModel("Sandpaper Sheet", "piece", 12.0, TradeType.PAINTER)
    )

    val mason: List<MaterialsModel> = listOf(
        MaterialsModel("Cement", "bag", 420.0, TradeType.MASON),
        MaterialsModel("Sand", "cft", 55.0, TradeType.MASON),
        MaterialsModel("Bricks", "piece", 8.0, TradeType.MASON),
        MaterialsModel("Aggregate", "cft", 60.0, TradeType.MASON),
        MaterialsModel("Tile Adhesive", "bag", 380.0, TradeType.MASON),
        MaterialsModel("White Cement", "bag", 450.0, TradeType.MASON),
        MaterialsModel("Binding Wire", "kg", 95.0, TradeType.MASON),
        MaterialsModel("Rod 8mm", "kg", 68.0, TradeType.MASON),
        MaterialsModel("Pop Powder", "bag", 320.0, TradeType.MASON),
        MaterialsModel("Concrete Block", "piece", 45.0, TradeType.MASON)
    )

    val grill: List<MaterialsModel> = listOf(
        MaterialsModel("Mild Steel Pipe", "metre", 180.0, TradeType.GRILL),
        MaterialsModel("MS Square Tube", "metre", 210.0, TradeType.GRILL),
        MaterialsModel("Flat Bar", "metre", 95.0, TradeType.GRILL),
        MaterialsModel("Angle Iron", "metre", 140.0, TradeType.GRILL),
        MaterialsModel("Welding Rod", "kg", 220.0, TradeType.GRILL),
        MaterialsModel("Cutting Disc", "piece", 55.0, TradeType.GRILL),
        MaterialsModel("Grinding Disc", "piece", 65.0, TradeType.GRILL),
        MaterialsModel("Primer Paint", "litre", 190.0, TradeType.GRILL),
        MaterialsModel("Hinges Heavy Duty", "pair", 240.0, TradeType.GRILL),
        MaterialsModel("Lock Set", "set", 320.0, TradeType.GRILL)
    )

    val acTechnician: List<MaterialsModel> = listOf(
        MaterialsModel("Copper Pipe 1/4\"", "metre", 220.0, TradeType.AC_TECHNICIAN),
        MaterialsModel("Copper Pipe 3/8\"", "metre", 280.0, TradeType.AC_TECHNICIAN),
        MaterialsModel("AC Gas R32", "kg", 950.0, TradeType.AC_TECHNICIAN),
        MaterialsModel("AC Gas R22", "kg", 780.0, TradeType.AC_TECHNICIAN),
        MaterialsModel("Insulation Foam", "metre", 35.0, TradeType.AC_TECHNICIAN),
        MaterialsModel("Drain Pipe", "metre", 18.0, TradeType.AC_TECHNICIAN),
        MaterialsModel("AC Bracket", "pair", 650.0, TradeType.AC_TECHNICIAN),
        MaterialsModel("Copper Nut", "piece", 22.0, TradeType.AC_TECHNICIAN),
        MaterialsModel("Service Valve", "piece", 260.0, TradeType.AC_TECHNICIAN),
        MaterialsModel("Contact Cleaner", "can", 240.0, TradeType.AC_TECHNICIAN)
    )

    val applianceRepair: List<MaterialsModel> = listOf(
        MaterialsModel("Thermostat", "piece", 220.0, TradeType.APPLIANCE_REPAIR),
        MaterialsModel("Capacitor 2.5uF", "piece", 85.0, TradeType.APPLIANCE_REPAIR),
        MaterialsModel("Capacitor 5uF", "piece", 95.0, TradeType.APPLIANCE_REPAIR),
        MaterialsModel("Capacitor 10uF", "piece", 110.0, TradeType.APPLIANCE_REPAIR),
        MaterialsModel("Relay", "piece", 140.0, TradeType.APPLIANCE_REPAIR),
        MaterialsModel("Fuse", "piece", 15.0, TradeType.APPLIANCE_REPAIR),
        MaterialsModel("Wire Harness", "set", 180.0, TradeType.APPLIANCE_REPAIR),
        MaterialsModel("PCB Cleaner", "can", 260.0, TradeType.APPLIANCE_REPAIR),
        MaterialsModel("Motor Belt", "piece", 160.0, TradeType.APPLIANCE_REPAIR),
        MaterialsModel("Bearing Set", "set", 240.0, TradeType.APPLIANCE_REPAIR)
    )

    val waterproofing: List<MaterialsModel> = listOf(
        MaterialsModel("Waterproof Coating", "kg", 180.0, TradeType.WATERPROOFING),
        MaterialsModel("Acrylic Sealant", "tube", 120.0, TradeType.WATERPROOFING),
        MaterialsModel("Bitumen Roll", "roll", 950.0, TradeType.WATERPROOFING),
        MaterialsModel("PU Sealant", "tube", 220.0, TradeType.WATERPROOFING),
        MaterialsModel("Bonding Agent", "litre", 260.0, TradeType.WATERPROOFING),
        MaterialsModel("Crack Filler", "kg", 140.0, TradeType.WATERPROOFING),
        MaterialsModel("Mesh Tape", "roll", 75.0, TradeType.WATERPROOFING),
        MaterialsModel("Primer", "litre", 190.0, TradeType.WATERPROOFING),
        MaterialsModel("Drainage Nozzle", "piece", 55.0, TradeType.WATERPROOFING),
        MaterialsModel("Membrane Sheet", "square feet", 18.0, TradeType.WATERPROOFING)
    )

    val pestControl: List<MaterialsModel> = listOf(
        MaterialsModel("Termiticide", "litre", 850.0, TradeType.PEST_CONTROL),
        MaterialsModel("Insecticide Spray", "litre", 420.0, TradeType.PEST_CONTROL),
        MaterialsModel("Rodent Bait", "piece", 35.0, TradeType.PEST_CONTROL),
        MaterialsModel("Gel Bait", "tube", 260.0, TradeType.PEST_CONTROL),
        MaterialsModel("Fogging Chemical", "litre", 780.0, TradeType.PEST_CONTROL),
        MaterialsModel("Hand Sprayer", "piece", 650.0, TradeType.PEST_CONTROL),
        MaterialsModel("Protective Gloves", "pair", 90.0, TradeType.PEST_CONTROL),
        MaterialsModel("Mask", "piece", 25.0, TradeType.PEST_CONTROL),
        MaterialsModel("Pest Trap", "piece", 120.0, TradeType.PEST_CONTROL),
        MaterialsModel("Bait Station", "piece", 180.0, TradeType.PEST_CONTROL)
    )

    val carBikeMechanic: List<MaterialsModel> = listOf(
        MaterialsModel("Engine Oil", "litre", 420.0, TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel("Gear Oil", "litre", 380.0, TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel("Air Filter", "piece", 160.0, TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel("Oil Filter", "piece", 140.0, TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel("Spark Plug", "piece", 120.0, TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel("Brake Pad Set", "set", 650.0, TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel("Chain Lube", "can", 180.0, TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel("Battery Water", "bottle", 25.0, TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel("Grease", "kg", 220.0, TradeType.CAR_BIKE_MECHANIC),
        MaterialsModel("Coolant", "litre", 240.0, TradeType.CAR_BIKE_MECHANIC)
    )

    val cctvSecurity: List<MaterialsModel> = listOf(
        MaterialsModel("CCTV Camera", "piece", 1850.0, TradeType.CCTV_SECURITY),
        MaterialsModel("DVR", "piece", 4200.0, TradeType.CCTV_SECURITY),
        MaterialsModel("NVR", "piece", 6500.0, TradeType.CCTV_SECURITY),
        MaterialsModel("BNC Connector", "piece", 18.0, TradeType.CCTV_SECURITY),
        MaterialsModel("Power Adapter", "piece", 220.0, TradeType.CCTV_SECURITY),
        MaterialsModel("Cat6 Cable", "metre", 22.0, TradeType.CCTV_SECURITY),
        MaterialsModel("Hard Disk", "piece", 3200.0, TradeType.CCTV_SECURITY),
        MaterialsModel("Power Supply Box", "piece", 950.0, TradeType.CCTV_SECURITY),
        MaterialsModel("Junction Box", "piece", 65.0, TradeType.CCTV_SECURITY),
        MaterialsModel("IR Bulb", "piece", 140.0, TradeType.CCTV_SECURITY)
    )

    val solarInstaller: List<MaterialsModel> = listOf(
        MaterialsModel("Solar Panel 550W", "piece", 13500.0, TradeType.SOLAR_INSTALLER),
        MaterialsModel("Solar Inverter", "piece", 28000.0, TradeType.SOLAR_INSTALLER),
        MaterialsModel("Solar Battery", "piece", 16500.0, TradeType.SOLAR_INSTALLER),
        MaterialsModel("Mounting Structure", "set", 4200.0, TradeType.SOLAR_INSTALLER),
        MaterialsModel("Solar Cable", "metre", 65.0, TradeType.SOLAR_INSTALLER),
        MaterialsModel("MC4 Connector", "pair", 55.0, TradeType.SOLAR_INSTALLER),
        MaterialsModel("Charge Controller", "piece", 3800.0, TradeType.SOLAR_INSTALLER),
        MaterialsModel("DC MCB", "piece", 480.0, TradeType.SOLAR_INSTALLER),
        MaterialsModel("Earthing Kit", "set", 950.0, TradeType.SOLAR_INSTALLER),
        MaterialsModel("SPD", "piece", 1200.0, TradeType.SOLAR_INSTALLER)
    )

    val aluminiumUpvc: List<MaterialsModel> = listOf(
        MaterialsModel("Aluminium Section", "metre", 240.0, TradeType.ALUMINIUM_UPVC),
        MaterialsModel("UPVC Profile", "metre", 180.0, TradeType.ALUMINIUM_UPVC),
        MaterialsModel("Glass Sheet", "square feet", 85.0, TradeType.ALUMINIUM_UPVC),
        MaterialsModel("Rubber Beading", "metre", 12.0, TradeType.ALUMINIUM_UPVC),
        MaterialsModel("Corner Joint", "piece", 35.0, TradeType.ALUMINIUM_UPVC),
        MaterialsModel("Screw Set", "box", 160.0, TradeType.ALUMINIUM_UPVC),
        MaterialsModel("Handle", "piece", 220.0, TradeType.ALUMINIUM_UPVC),
        MaterialsModel("Roller Wheel", "piece", 55.0, TradeType.ALUMINIUM_UPVC),
        MaterialsModel("Silicone Sealant", "tube", 140.0, TradeType.ALUMINIUM_UPVC),
        MaterialsModel("Lock Set", "set", 320.0, TradeType.ALUMINIUM_UPVC)
    )

    val civilContractor: List<MaterialsModel> = listOf(
        MaterialsModel("Cement", "bag", 420.0, TradeType.CIVIL_CONTRACTOR),
        MaterialsModel("Sand", "cft", 55.0, TradeType.CIVIL_CONTRACTOR),
        MaterialsModel("Aggregate", "cft", 60.0, TradeType.CIVIL_CONTRACTOR),
        MaterialsModel("Steel Rod 10mm", "kg", 72.0, TradeType.CIVIL_CONTRACTOR),
        MaterialsModel("Steel Rod 12mm", "kg", 74.0, TradeType.CIVIL_CONTRACTOR),
        MaterialsModel("Binding Wire", "kg", 95.0, TradeType.CIVIL_CONTRACTOR),
        MaterialsModel("Shuttering Plywood", "sheet", 1250.0, TradeType.CIVIL_CONTRACTOR),
        MaterialsModel("Nails", "kg", 120.0, TradeType.CIVIL_CONTRACTOR),
        MaterialsModel("Concrete Admixture", "litre", 280.0, TradeType.CIVIL_CONTRACTOR),
        MaterialsModel("Curing Compound", "litre", 320.0, TradeType.CIVIL_CONTRACTOR)
    )

    val boreWell: List<MaterialsModel> = listOf(
        MaterialsModel("PVC Bore Pipe", "metre", 125.0, TradeType.BORE_WELL),
        MaterialsModel("Submersible Cable", "metre", 85.0, TradeType.BORE_WELL),
        MaterialsModel("Bore Clamp", "piece", 45.0, TradeType.BORE_WELL),
        MaterialsModel("Foot Valve", "piece", 320.0, TradeType.BORE_WELL),
        MaterialsModel("Water Tank Float", "piece", 280.0, TradeType.BORE_WELL),
        MaterialsModel("Submersible Pump", "piece", 8500.0, TradeType.BORE_WELL),
        MaterialsModel("Pressure Gauge", "piece", 240.0, TradeType.BORE_WELL),
        MaterialsModel("GI Pipe", "metre", 180.0, TradeType.BORE_WELL),
        MaterialsModel("Cable Joint Kit", "set", 220.0, TradeType.BORE_WELL),
        MaterialsModel("Pump Starter Box", "piece", 1650.0, TradeType.BORE_WELL)
    )

    val gasLpgFitter: List<MaterialsModel> = listOf(
        MaterialsModel("Copper Gas Pipe", "metre", 240.0, TradeType.GAS_LPG_FITTER),
        MaterialsModel("LPG Hose", "metre", 85.0, TradeType.GAS_LPG_FITTER),
        MaterialsModel("Gas Regulator", "piece", 380.0, TradeType.GAS_LPG_FITTER),
        MaterialsModel("Gas Valve", "piece", 220.0, TradeType.GAS_LPG_FITTER),
        MaterialsModel("Clamp", "piece", 18.0, TradeType.GAS_LPG_FITTER),
        MaterialsModel("Teflon Tape", "roll", 25.0, TradeType.GAS_LPG_FITTER),
        MaterialsModel("Burner Nozzle", "piece", 55.0, TradeType.GAS_LPG_FITTER),
        MaterialsModel("LPG Connector", "piece", 140.0, TradeType.GAS_LPG_FITTER),
        MaterialsModel("Leak Detector Spray", "can", 260.0, TradeType.GAS_LPG_FITTER),
        MaterialsModel("Gas Wrench", "piece", 320.0, TradeType.GAS_LPG_FITTER)
    )

    val networkSupport: List<MaterialsModel> = listOf(
        MaterialsModel("Cat6 Cable", "metre", 22.0, TradeType.NETWORK_SUPPORT),
        MaterialsModel("RJ45 Connector", "piece", 12.0, TradeType.NETWORK_SUPPORT),
        MaterialsModel("Patch Cord", "piece", 95.0, TradeType.NETWORK_SUPPORT),
        MaterialsModel("Router", "piece", 2200.0, TradeType.NETWORK_SUPPORT),
        MaterialsModel("Switch 8 Port", "piece", 1650.0, TradeType.NETWORK_SUPPORT),
        MaterialsModel("Access Point", "piece", 2800.0, TradeType.NETWORK_SUPPORT),
        MaterialsModel("Crimping Tool", "piece", 450.0, TradeType.NETWORK_SUPPORT),
        MaterialsModel("LAN Tester", "piece", 780.0, TradeType.NETWORK_SUPPORT),
        MaterialsModel("Cable Tie", "pack", 60.0, TradeType.NETWORK_SUPPORT),
        MaterialsModel("Wall Jack", "piece", 85.0, TradeType.NETWORK_SUPPORT)
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