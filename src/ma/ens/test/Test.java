package ma.ens.test;

import ma.ens.entities.Chambre;
import ma.ens.entities.Hotel;
import ma.ens.entities.TypeChambre;
import ma.ens.services.ChambreService;
import ma.ens.services.HotelService;

public class Test {
    
    public static void main(String[] args) {
        
        HotelService hs = new HotelService();
        ChambreService cs = new ChambreService();
        
        hs.create(new Hotel("Hôtel", "Marrakech"));
        
        cs.create(new Chambre(500.0, TypeChambre.SIMPLE, "Disponible", hs.findById(1)));
        cs.create(new Chambre(800.0, TypeChambre.DOUBLE, "Occupée", hs.findById(1)));
        cs.create(new Chambre(400.0, TypeChambre.SIMPLE, "Disponible", hs.findById(1)));
        
        System.out.println("TOUTES LES CHAMBRES");
        for (Chambre c : cs.findAll()) {
            System.out.println(c);
        }
        
        System.out.println("\nCHAMBRES DE L'HÔTEL ID=1");
        for (Chambre c : hs.findById(1).getChambres()) {
            System.out.println(c);
        }
        
        System.out.println("\nCHAMBRES DISPONIBLES <= 600 DH");
        for (Chambre c : cs.findAll()) {
            if ("Disponible".equalsIgnoreCase(c.getEtat()) && c.getPrix() <= 600.0) {
                System.out.println(c);
            }
        }
    }
}