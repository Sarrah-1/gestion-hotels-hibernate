package ma.ens.test;

import java.util.Scanner;
import ma.ens.entities.Chambre;
import ma.ens.entities.Hotel;
import ma.ens.entities.TypeChambre;
import ma.ens.services.ChambreService;
import ma.ens.services.HotelService;

public class Test {

    public static void main(String[] args) {

        HotelService hs = new HotelService();
        ChambreService cs = new ChambreService();
        Scanner scanner = new Scanner(System.in);

        // =============================================================
        // 1. INSERTION DES 4 HÔTELS DU MAROC ET DE LEURS CHAMBRES
        // =============================================================
        Hotel h1 = new Hotel("La Mamounia", "Marrakech");
        Hotel h2 = new Hotel("Royal Mansour", "Casablanca");
        Hotel h3 = new Hotel("Hotel Sofitel", "Rabat");
        Hotel h4 = new Hotel("Palais Jamai", "Fes");

        hs.create(h1);
        hs.create(h2);
        hs.create(h3);
        hs.create(h4);

        // Chambres Hôtel 1 (La Mamounia - Marrakech)
        cs.create(new Chambre(500.0, TypeChambre.SIMPLE, "Disponible", h1));
        cs.create(new Chambre(800.0, TypeChambre.DOUBLE, "Occupée", h1));
        cs.create(new Chambre(450.0, TypeChambre.SIMPLE, "Disponible", h1));

        // Chambres Hôtel 2 (Royal Mansour - Casablanca)
        cs.create(new Chambre(350.0, TypeChambre.SIMPLE, "Disponible", h2));
        cs.create(new Chambre(900.0, TypeChambre.DOUBLE, "Occupée", h2));

        // Chambres Hôtel 3 (Hotel Sofitel - Rabat)
        cs.create(new Chambre(600.0, TypeChambre.DOUBLE, "Disponible", h3));
        cs.create(new Chambre(750.0, TypeChambre.DOUBLE, "Occupée", h3));

        // Chambres Hôtel 4 (Palais Jamai - Fes)
        cs.create(new Chambre(250.0, TypeChambre.SIMPLE, "Disponible", h4));
        cs.create(new Chambre(550.0, TypeChambre.DOUBLE, "Disponible", h4));

        // =============================================================
        // 2. TEST : TOUS LES HÔTELS EN BASE DE DONNÉES
        // =============================================================
        System.out.println("=== TOUS LES HÔTELS ===");
        for (Hotel h : hs.findAll()) {
            System.out.println("Hôtel ID=" + h.getId() + " | Nom: " + h.getNom() + " | Ville: " + h.getAdress());
        }

        // =============================================================
        // 3. RECHERCHE DYNAMIQUE 1 : PAR HÔTEL ET PAR ÉTAT
        // =============================================================
        System.out.println("\n=== RECHERCHE DES CHAMBRES PAR HÔTEL ET ÉTAT ===");
        System.out.print("Entrez l'ID de l'hôtel désiré (ex: 1, 2, 3 ou 4) : ");
        int hotelIdSaisi = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne

        System.out.print("Entrez l'état de la chambre (ex: Disponible / Occupée) : ");
        String etatHoteSaisi = scanner.nextLine();

        Hotel hotelChoisi = hs.findById(hotelIdSaisi);
        if (hotelChoisi != null) {
            System.out.println("\n--- CHAMBRES DE L'HÔTEL '" + hotelChoisi.getNom() + "' (ID=" + hotelIdSaisi + ") AVEC ÉTAT = '" + etatHoteSaisi + "' ---");
            boolean trouve = false;
            for (Chambre c : hotelChoisi.getChambres()) {
                if (etatHoteSaisi.equalsIgnoreCase(c.getEtat())) {
                    System.out.println(c);
                    trouve = true;
                }
            }
            if (!trouve) {
                System.out.println("Aucune chambre avec cet état pour cet hôtel.");
            }
        } else {
            System.out.println("Hôtel non trouvé !");
        }

        // =============================================================
        // 4. RECHERCHE DYNAMIQUE 2 : PAR ÉTAT ET PRIX MAXIMUM
        // =============================================================
        System.out.println("\n=== RECHERCHE PAR ÉTAT ET PRIX MAX ===");
        System.out.print("Saisir l'état de la chambre (ex: Disponible) : ");
        String etatPrixSaisi = scanner.nextLine();

        System.out.print("Saisir le prix maximum (ex: 600) : ");
        double prixMaxSaisi = scanner.nextDouble();

        System.out.println("\n--- RÉSULTAT POUR ÉTAT = '" + etatPrixSaisi + "' ET PRIX <= " + prixMaxSaisi + " DH ---");
        boolean trouveFiltre = false;
        for (Chambre c : cs.findAll()) {
            if (etatPrixSaisi.equalsIgnoreCase(c.getEtat()) && c.getPrix() <= prixMaxSaisi) {
                System.out.println(c);
                trouveFiltre = true;
            }
        }
        if (!trouveFiltre) {
            System.out.println("Aucune chambre ne correspond à ces critères de prix et état.");
        }

        scanner.close();
    }
}