package core.data.demo

import core.model.entity.Stadium

object DemoStadium {
    val stadium = listOf(
        // 1. Major & National Stadiums
        Stadium(
            name = "King Baudouin Stadium",
            city = "Brussels",
            club = "Belgium National Team",
            capacity = 50093 // Official capacity varies slightly (e.g., 49,825 to 50,093)
        ),
        Stadium(
            name = "Stade Maurice Dufrasne", // Stade de Sclessin
            city = "Liège",
            club = "Standard Liège",
            capacity = 30023
        ),
        Stadium(
            name = "Jan Breydel Stadium",
            city = "Bruges",
            club = "Club Brugge & Cercle Brugge", // Shared by two major clubs
            capacity = 29062
        ),

        // 2. Jupiler Pro League (Top Tier) Stadiums
        Stadium(
            name = "Cegeka Arena", // Formerly Luminus Arena
            city = "Genk",
            club = "KRC Genk",
            capacity = 24956
        ),
        Stadium(
            name = "Lotto Park", // Formerly Constant Vanden Stock Stadium
            city = "Anderlecht (Brussels)",
            club = "RSC Anderlecht",
            capacity = 22500
        ),
        Stadium(
            name = "Planet Group Arena", // Formerly Ghelamco Arena
            city = "Ghent",
            club = "KAA Gent",
            capacity = 20175
        ),
        Stadium(
            name = "AFAS Stadion", // Achter de Kazerne
            city = "Mechelen",
            club = "KV Mechelen",
            capacity = 16672
        ),
        Stadium(
            name = "Bosuilstadion",
            city = "Antwerp",
            club = "Royal Antwerp F.C.",
            capacity = 16144
        ),
        Stadium(
            name = "Stade du Pays de Charleroi",
            city = "Charleroi",
            club = "R. Charleroi S.C.",
            capacity = 15113
        ),
        Stadium(
            name = "Stayen",
            city = "Sint-Truiden",
            club = "STVV (Sint-Truidense VV)",
            capacity = 14600
        ),
        Stadium(
            name = "Elindus Arena",
            city = "Waregem",
            club = "S.V. Zulte Waregem",
            capacity = 12698
        ),
        Stadium(
            name = "King Power at Den Dreef Stadium",
            city = "Heverlee (Leuven)",
            club = "OH Leuven",
            capacity = 10020
        ),
        Stadium(
            name = "Stade Joseph Marien",
            city = "Forest (Brussels)",
            club = "Union Saint-Gilloise",
            capacity = 9512
        ),
        Stadium(
            name = "Guldensporenstadion",
            city = "Kortrijk",
            club = "KV Kortrijk",
            capacity = 9399
        ),
        Stadium(
            name = "Kehrweg Stadion",
            city = "Eupen",
            club = "KAS Eupen",
            capacity = 8363
        ),
        Stadium(
            name = "Het Kuipje",
            city = "Westerlo",
            club = "KVC Westerlo",
            capacity = 8035
        ),

        // 3. Challenger Pro League (Second Tier) Stadiums & Other notable venues
        Stadium(
            name = "Herman Vanderpoortenstadion",
            city = "Lier",
            club = "Lierse Kempenzonen",
            capacity = 14538
        ),
        Stadium(
            name = "Olympisch Stadion",
            city = "Antwerp",
            club = "Beerschot",
            capacity = 12771
        ),
        Stadium(
            name = "Edmond Machtens Stadium",
            city = "Molenbeek (Brussels)",
            club = "RWD Molenbeek",
            capacity = 12266
        ),
        Stadium(
            name = "Daknamstadion",
            city = "Lokeren",
            club = "KSC Lokeren-Temse",
            capacity = 12739
        ),
        Stadium(
            name = "Freethiel Stadion",
            city = "Beveren",
            club = "Waasland-Beveren",
            capacity = 8190
        ),
        Stadium(
            name = "Soevereinstadion",
            city = "Lommel",
            club = "Lommel S.K.",
            capacity = 8000
        ),
        Stadium(
            name = "Stade du Pairay",
            city = "Seraing",
            club = "RFC Seraing",
            capacity = 8207
        ),
        Stadium(
            name = "Patrostadion",
            city = "Maasmechelen",
            club = "Patro Eisden Maasmechelen",
            capacity = 8143
        ),
        Stadium(
            name = "Van Roystadion",
            city = "Denderleeuw",
            club = "FCV Dender EH",
            capacity = 6429
        ),
        Stadium(
            name = "Easi Arena",
            city = "La Louvière",
            club = "RAAL La Louvière",
            capacity = 8050
        )
    )
}