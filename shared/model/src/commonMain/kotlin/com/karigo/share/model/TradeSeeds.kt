package com.karigo.share.model

object TradeSeeds {

    val plumber: List<StarterMaterial> = listOf(
        StarterMaterial(name = "PVC Pipe 1/2\"", unit = "metre", price =  45.0, tradeType =  TradeType.PLUMBER),
        StarterMaterial(name = "PVC Pipe 3/4\"", unit = "metre", price =  70.0, tradeType =  TradeType.PLUMBER),
        StarterMaterial("CPVC Pipe 1/2\"", "metre", price = 85.0, tradeType =  TradeType.PLUMBER),
        StarterMaterial("Elbow 1/2\"", "piece", 12.0, TradeType.PLUMBER),
        StarterMaterial("Tee 1/2\"", "piece", 15.0, TradeType.PLUMBER),
        StarterMaterial("Coupling 1/2\"", "piece", 10.0, TradeType.PLUMBER),
        StarterMaterial("Ball Valve 1/2\"", "piece", 120.0, TradeType.PLUMBER),
        StarterMaterial("Bib Tap", "piece", 180.0, TradeType.PLUMBER),
        StarterMaterial("PVC Glue", "can", 95.0, TradeType.PLUMBER),
        StarterMaterial("PTFE Tape", "roll", 25.0, TradeType.PLUMBER)
    )

    val electrician: List<StarterMaterial> = listOf(
        StarterMaterial("Copper Wire 1.5 sqmm", "metre", 22.0, TradeType.ELECTRICIAN),
        StarterMaterial("Copper Wire 2.5 sqmm", "metre", 35.0, TradeType.ELECTRICIAN),
        StarterMaterial("Conduit Pipe 20mm", "metre", 18.0, TradeType.ELECTRICIAN),
        StarterMaterial("Conduit Bend", "piece", 8.0, TradeType.ELECTRICIAN),
        StarterMaterial("Switch 6A", "piece", 65.0, TradeType.ELECTRICIAN),
        StarterMaterial("Socket 6A", "piece", 75.0, TradeType.ELECTRICIAN),
        StarterMaterial("MCB 16A", "piece", 220.0, TradeType.ELECTRICIAN),
        StarterMaterial("Distribution Box", "piece", 650.0, TradeType.ELECTRICIAN),
        StarterMaterial("Ceiling Rose", "piece", 28.0, TradeType.ELECTRICIAN),
        StarterMaterial("Insulation Tape", "roll", 20.0, TradeType.ELECTRICIAN)
    )

    val carpenter: List<StarterMaterial> = listOf(
        StarterMaterial("Plywood Sheet 8x4", "sheet", 1450.0, TradeType.CARPENTER),
        StarterMaterial("Sunmica Sheet", "sheet", 950.0, TradeType.CARPENTER),
        StarterMaterial("Wood Screw Set", "box", 180.0, TradeType.CARPENTER),
        StarterMaterial("Nail Set", "kg", 120.0, TradeType.CARPENTER),
        StarterMaterial("Wood Glue", "bottle", 160.0, TradeType.CARPENTER),
        StarterMaterial("Hinge", "piece", 35.0, TradeType.CARPENTER),
        StarterMaterial("Drawer Slider", "pair", 280.0, TradeType.CARPENTER),
        StarterMaterial("Door Handle", "piece", 220.0, TradeType.CARPENTER),
        StarterMaterial("Wood Polish", "can", 240.0, TradeType.CARPENTER),
        StarterMaterial("Laminate Edge Tape", "roll", 55.0, TradeType.CARPENTER)
    )

    val painter: List<StarterMaterial> = listOf(
        StarterMaterial("Interior Emulsion Paint", "litre", 260.0, TradeType.PAINTER),
        StarterMaterial("Exterior Paint", "litre", 290.0, TradeType.PAINTER),
        StarterMaterial("Primer", "litre", 180.0, TradeType.PAINTER),
        StarterMaterial("Wall Putty", "kg", 45.0, TradeType.PAINTER),
        StarterMaterial("Paint Roller", "piece", 120.0, TradeType.PAINTER),
        StarterMaterial("Brush 2 inch", "piece", 60.0, TradeType.PAINTER),
        StarterMaterial("Masking Tape", "roll", 85.0, TradeType.PAINTER),
        StarterMaterial("Thinner", "litre", 140.0, TradeType.PAINTER),
        StarterMaterial("Paint Tray", "piece", 75.0, TradeType.PAINTER),
        StarterMaterial("Sandpaper Sheet", "piece", 12.0, TradeType.PAINTER)
    )

    val mason: List<StarterMaterial> = listOf(
        StarterMaterial("Cement", "bag", 420.0, TradeType.MASON),
        StarterMaterial("Sand", "cft", 55.0, TradeType.MASON),
        StarterMaterial("Bricks", "piece", 8.0, TradeType.MASON),
        StarterMaterial("Aggregate", "cft", 60.0, TradeType.MASON),
        StarterMaterial("Tile Adhesive", "bag", 380.0, TradeType.MASON),
        StarterMaterial("White Cement", "bag", 450.0, TradeType.MASON),
        StarterMaterial("Binding Wire", "kg", 95.0, TradeType.MASON),
        StarterMaterial("Rod 8mm", "kg", 68.0, TradeType.MASON),
        StarterMaterial("Pop Powder", "bag", 320.0, TradeType.MASON),
        StarterMaterial("Concrete Block", "piece", 45.0, TradeType.MASON)
    )

    val grill: List<StarterMaterial> = listOf(
        StarterMaterial("Mild Steel Pipe", "metre", 180.0, TradeType.GRILL),
        StarterMaterial("MS Square Tube", "metre", 210.0, TradeType.GRILL),
        StarterMaterial("Flat Bar", "metre", 95.0, TradeType.GRILL),
        StarterMaterial("Angle Iron", "metre", 140.0, TradeType.GRILL),
        StarterMaterial("Welding Rod", "kg", 220.0, TradeType.GRILL),
        StarterMaterial("Cutting Disc", "piece", 55.0, TradeType.GRILL),
        StarterMaterial("Grinding Disc", "piece", 65.0, TradeType.GRILL),
        StarterMaterial("Primer Paint", "litre", 190.0, TradeType.GRILL),
        StarterMaterial("Hinges Heavy Duty", "pair", 240.0, TradeType.GRILL),
        StarterMaterial("Lock Set", "set", 320.0, TradeType.GRILL)
    )

    val acTechnician: List<StarterMaterial> = listOf(
        StarterMaterial("Copper Pipe 1/4\"", "metre", 220.0, TradeType.AC_TECHNICIAN),
        StarterMaterial("Copper Pipe 3/8\"", "metre", 280.0, TradeType.AC_TECHNICIAN),
        StarterMaterial("AC Gas R32", "kg", 950.0, TradeType.AC_TECHNICIAN),
        StarterMaterial("AC Gas R22", "kg", 780.0, TradeType.AC_TECHNICIAN),
        StarterMaterial("Insulation Foam", "metre", 35.0, TradeType.AC_TECHNICIAN),
        StarterMaterial("Drain Pipe", "metre", 18.0, TradeType.AC_TECHNICIAN),
        StarterMaterial("AC Bracket", "pair", 650.0, TradeType.AC_TECHNICIAN),
        StarterMaterial("Copper Nut", "piece", 22.0, TradeType.AC_TECHNICIAN),
        StarterMaterial("Service Valve", "piece", 260.0, TradeType.AC_TECHNICIAN),
        StarterMaterial("Contact Cleaner", "can", 240.0, TradeType.AC_TECHNICIAN)
    )

    val applianceRepair: List<StarterMaterial> = listOf(
        StarterMaterial("Thermostat", "piece", 220.0, TradeType.APPLIANCE_REPAIR),
        StarterMaterial("Capacitor 2.5uF", "piece", 85.0, TradeType.APPLIANCE_REPAIR),
        StarterMaterial("Capacitor 5uF", "piece", 95.0, TradeType.APPLIANCE_REPAIR),
        StarterMaterial("Capacitor 10uF", "piece", 110.0, TradeType.APPLIANCE_REPAIR),
        StarterMaterial("Relay", "piece", 140.0, TradeType.APPLIANCE_REPAIR),
        StarterMaterial("Fuse", "piece", 15.0, TradeType.APPLIANCE_REPAIR),
        StarterMaterial("Wire Harness", "set", 180.0, TradeType.APPLIANCE_REPAIR),
        StarterMaterial("PCB Cleaner", "can", 260.0, TradeType.APPLIANCE_REPAIR),
        StarterMaterial("Motor Belt", "piece", 160.0, TradeType.APPLIANCE_REPAIR),
        StarterMaterial("Bearing Set", "set", 240.0, TradeType.APPLIANCE_REPAIR)
    )

    val waterproofing: List<StarterMaterial> = listOf(
        StarterMaterial("Waterproof Coating", "kg", 180.0, TradeType.WATERPROOFING),
        StarterMaterial("Acrylic Sealant", "tube", 120.0, TradeType.WATERPROOFING),
        StarterMaterial("Bitumen Roll", "roll", 950.0, TradeType.WATERPROOFING),
        StarterMaterial("PU Sealant", "tube", 220.0, TradeType.WATERPROOFING),
        StarterMaterial("Bonding Agent", "litre", 260.0, TradeType.WATERPROOFING),
        StarterMaterial("Crack Filler", "kg", 140.0, TradeType.WATERPROOFING),
        StarterMaterial("Mesh Tape", "roll", 75.0, TradeType.WATERPROOFING),
        StarterMaterial("Primer", "litre", 190.0, TradeType.WATERPROOFING),
        StarterMaterial("Drainage Nozzle", "piece", 55.0, TradeType.WATERPROOFING),
        StarterMaterial("Membrane Sheet", "square feet", 18.0, TradeType.WATERPROOFING)
    )

    val pestControl: List<StarterMaterial> = listOf(
        StarterMaterial("Termiticide", "litre", 850.0, TradeType.PEST_CONTROL),
        StarterMaterial("Insecticide Spray", "litre", 420.0, TradeType.PEST_CONTROL),
        StarterMaterial("Rodent Bait", "piece", 35.0, TradeType.PEST_CONTROL),
        StarterMaterial("Gel Bait", "tube", 260.0, TradeType.PEST_CONTROL),
        StarterMaterial("Fogging Chemical", "litre", 780.0, TradeType.PEST_CONTROL),
        StarterMaterial("Hand Sprayer", "piece", 650.0, TradeType.PEST_CONTROL),
        StarterMaterial("Protective Gloves", "pair", 90.0, TradeType.PEST_CONTROL),
        StarterMaterial("Mask", "piece", 25.0, TradeType.PEST_CONTROL),
        StarterMaterial("Pest Trap", "piece", 120.0, TradeType.PEST_CONTROL),
        StarterMaterial("Bait Station", "piece", 180.0, TradeType.PEST_CONTROL)
    )

    val carBikeMechanic: List<StarterMaterial> = listOf(
        StarterMaterial("Engine Oil", "litre", 420.0, TradeType.CAR_BIKE_MECHANIC),
        StarterMaterial("Gear Oil", "litre", 380.0, TradeType.CAR_BIKE_MECHANIC),
        StarterMaterial("Air Filter", "piece", 160.0, TradeType.CAR_BIKE_MECHANIC),
        StarterMaterial("Oil Filter", "piece", 140.0, TradeType.CAR_BIKE_MECHANIC),
        StarterMaterial("Spark Plug", "piece", 120.0, TradeType.CAR_BIKE_MECHANIC),
        StarterMaterial("Brake Pad Set", "set", 650.0, TradeType.CAR_BIKE_MECHANIC),
        StarterMaterial("Chain Lube", "can", 180.0, TradeType.CAR_BIKE_MECHANIC),
        StarterMaterial("Battery Water", "bottle", 25.0, TradeType.CAR_BIKE_MECHANIC),
        StarterMaterial("Grease", "kg", 220.0, TradeType.CAR_BIKE_MECHANIC),
        StarterMaterial("Coolant", "litre", 240.0, TradeType.CAR_BIKE_MECHANIC)
    )

    val cctvSecurity: List<StarterMaterial> = listOf(
        StarterMaterial("CCTV Camera", "piece", 1850.0, TradeType.CCTV_SECURITY),
        StarterMaterial("DVR", "piece", 4200.0, TradeType.CCTV_SECURITY),
        StarterMaterial("NVR", "piece", 6500.0, TradeType.CCTV_SECURITY),
        StarterMaterial("BNC Connector", "piece", 18.0, TradeType.CCTV_SECURITY),
        StarterMaterial("Power Adapter", "piece", 220.0, TradeType.CCTV_SECURITY),
        StarterMaterial("Cat6 Cable", "metre", 22.0, TradeType.CCTV_SECURITY),
        StarterMaterial("Hard Disk", "piece", 3200.0, TradeType.CCTV_SECURITY),
        StarterMaterial("Power Supply Box", "piece", 950.0, TradeType.CCTV_SECURITY),
        StarterMaterial("Junction Box", "piece", 65.0, TradeType.CCTV_SECURITY),
        StarterMaterial("IR Bulb", "piece", 140.0, TradeType.CCTV_SECURITY)
    )

    val solarInstaller: List<StarterMaterial> = listOf(
        StarterMaterial("Solar Panel 550W", "piece", 13500.0, TradeType.SOLAR_INSTALLER),
        StarterMaterial("Solar Inverter", "piece", 28000.0, TradeType.SOLAR_INSTALLER),
        StarterMaterial("Solar Battery", "piece", 16500.0, TradeType.SOLAR_INSTALLER),
        StarterMaterial("Mounting Structure", "set", 4200.0, TradeType.SOLAR_INSTALLER),
        StarterMaterial("Solar Cable", "metre", 65.0, TradeType.SOLAR_INSTALLER),
        StarterMaterial("MC4 Connector", "pair", 55.0, TradeType.SOLAR_INSTALLER),
        StarterMaterial("Charge Controller", "piece", 3800.0, TradeType.SOLAR_INSTALLER),
        StarterMaterial("DC MCB", "piece", 480.0, TradeType.SOLAR_INSTALLER),
        StarterMaterial("Earthing Kit", "set", 950.0, TradeType.SOLAR_INSTALLER),
        StarterMaterial("SPD", "piece", 1200.0, TradeType.SOLAR_INSTALLER)
    )

    val aluminiumUpvc: List<StarterMaterial> = listOf(
        StarterMaterial("Aluminium Section", "metre", 240.0, TradeType.ALUMINIUM_UPVC),
        StarterMaterial("UPVC Profile", "metre", 180.0, TradeType.ALUMINIUM_UPVC),
        StarterMaterial("Glass Sheet", "square feet", 85.0, TradeType.ALUMINIUM_UPVC),
        StarterMaterial("Rubber Beading", "metre", 12.0, TradeType.ALUMINIUM_UPVC),
        StarterMaterial("Corner Joint", "piece", 35.0, TradeType.ALUMINIUM_UPVC),
        StarterMaterial("Screw Set", "box", 160.0, TradeType.ALUMINIUM_UPVC),
        StarterMaterial("Handle", "piece", 220.0, TradeType.ALUMINIUM_UPVC),
        StarterMaterial("Roller Wheel", "piece", 55.0, TradeType.ALUMINIUM_UPVC),
        StarterMaterial("Silicone Sealant", "tube", 140.0, TradeType.ALUMINIUM_UPVC),
        StarterMaterial("Lock Set", "set", 320.0, TradeType.ALUMINIUM_UPVC)
    )

    val civilContractor: List<StarterMaterial> = listOf(
        StarterMaterial("Cement", "bag", 420.0, TradeType.CIVIL_CONTRACTOR),
        StarterMaterial("Sand", "cft", 55.0, TradeType.CIVIL_CONTRACTOR),
        StarterMaterial("Aggregate", "cft", 60.0, TradeType.CIVIL_CONTRACTOR),
        StarterMaterial("Steel Rod 10mm", "kg", 72.0, TradeType.CIVIL_CONTRACTOR),
        StarterMaterial("Steel Rod 12mm", "kg", 74.0, TradeType.CIVIL_CONTRACTOR),
        StarterMaterial("Binding Wire", "kg", 95.0, TradeType.CIVIL_CONTRACTOR),
        StarterMaterial("Shuttering Plywood", "sheet", 1250.0, TradeType.CIVIL_CONTRACTOR),
        StarterMaterial("Nails", "kg", 120.0, TradeType.CIVIL_CONTRACTOR),
        StarterMaterial("Concrete Admixture", "litre", 280.0, TradeType.CIVIL_CONTRACTOR),
        StarterMaterial("Curing Compound", "litre", 320.0, TradeType.CIVIL_CONTRACTOR)
    )

    val boreWell: List<StarterMaterial> = listOf(
        StarterMaterial("PVC Bore Pipe", "metre", 125.0, TradeType.BORE_WELL),
        StarterMaterial("Submersible Cable", "metre", 85.0, TradeType.BORE_WELL),
        StarterMaterial("Bore Clamp", "piece", 45.0, TradeType.BORE_WELL),
        StarterMaterial("Foot Valve", "piece", 320.0, TradeType.BORE_WELL),
        StarterMaterial("Water Tank Float", "piece", 280.0, TradeType.BORE_WELL),
        StarterMaterial("Submersible Pump", "piece", 8500.0, TradeType.BORE_WELL),
        StarterMaterial("Pressure Gauge", "piece", 240.0, TradeType.BORE_WELL),
        StarterMaterial("GI Pipe", "metre", 180.0, TradeType.BORE_WELL),
        StarterMaterial("Cable Joint Kit", "set", 220.0, TradeType.BORE_WELL),
        StarterMaterial("Pump Starter Box", "piece", 1650.0, TradeType.BORE_WELL)
    )

    val gasLpgFitter: List<StarterMaterial> = listOf(
        StarterMaterial("Copper Gas Pipe", "metre", 240.0, TradeType.GAS_LPG_FITTER),
        StarterMaterial("LPG Hose", "metre", 85.0, TradeType.GAS_LPG_FITTER),
        StarterMaterial("Gas Regulator", "piece", 380.0, TradeType.GAS_LPG_FITTER),
        StarterMaterial("Gas Valve", "piece", 220.0, TradeType.GAS_LPG_FITTER),
        StarterMaterial("Clamp", "piece", 18.0, TradeType.GAS_LPG_FITTER),
        StarterMaterial("Teflon Tape", "roll", 25.0, TradeType.GAS_LPG_FITTER),
        StarterMaterial("Burner Nozzle", "piece", 55.0, TradeType.GAS_LPG_FITTER),
        StarterMaterial("LPG Connector", "piece", 140.0, TradeType.GAS_LPG_FITTER),
        StarterMaterial("Leak Detector Spray", "can", 260.0, TradeType.GAS_LPG_FITTER),
        StarterMaterial("Gas Wrench", "piece", 320.0, TradeType.GAS_LPG_FITTER)
    )

    val networkSupport: List<StarterMaterial> = listOf(
        StarterMaterial("Cat6 Cable", "metre", 22.0, TradeType.NETWORK_SUPPORT),
        StarterMaterial("RJ45 Connector", "piece", 12.0, TradeType.NETWORK_SUPPORT),
        StarterMaterial("Patch Cord", "piece", 95.0, TradeType.NETWORK_SUPPORT),
        StarterMaterial("Router", "piece", 2200.0, TradeType.NETWORK_SUPPORT),
        StarterMaterial("Switch 8 Port", "piece", 1650.0, TradeType.NETWORK_SUPPORT),
        StarterMaterial("Access Point", "piece", 2800.0, TradeType.NETWORK_SUPPORT),
        StarterMaterial("Crimping Tool", "piece", 450.0, TradeType.NETWORK_SUPPORT),
        StarterMaterial("LAN Tester", "piece", 780.0, TradeType.NETWORK_SUPPORT),
        StarterMaterial("Cable Tie", "pack", 60.0, TradeType.NETWORK_SUPPORT),
        StarterMaterial("Wall Jack", "piece", 85.0, TradeType.NETWORK_SUPPORT)
    )

    val seeds: Map<TradeType, List<StarterMaterial>> = mapOf(
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