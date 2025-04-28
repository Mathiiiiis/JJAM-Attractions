package view;

import dao.ReservationDAO;
import model.Client;
import model.Parc;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
import java.util.List;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class PDFGenerator {

    public static void generateFacturePDF(Client client, List<TicketPanel> billets, List<Parc> parcsChoisis, double totalFinal) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
            contentStream.newLineAtOffset(220, 770);
            contentStream.showText("FACTURE");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(50, 730);

            if (client != null) {
                contentStream.showText("Client: " + client.getNom());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Email: " + (client.getEmail() != null ? client.getEmail() : "Non renseigné"));
            } else {
                contentStream.showText("Client: Invité");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Email: Non renseigné");
            }

            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("------------------------------");

            double y = 660;
            double totalGeneral = 0;

            for (TicketPanel billet : billets) {
                double prixBillet = calculerPrixBillet(billet, parcsChoisis);

                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Billet : " + billet.getNom() + " - " + String.format("%.2f", prixBillet) + " €");
                totalGeneral += prixBillet;

                y -= 20;
                if (y < 100) {
                    contentStream.endText();
                    contentStream.close();

                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(50, 750);
                    y = 750;
                }
            }

            contentStream.newLineAtOffset(0, -30);
            contentStream.showText("------------------------------");
            contentStream.newLineAtOffset(0, -20);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.showText("TOTAL : " + String.format("%.2f", totalGeneral) + " €");

            contentStream.endText();
            contentStream.close();

            String fileName = "facture_client.pdf";
            document.save(fileName);
            Desktop.getDesktop().open(new File(fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateBilletsPDF(Client client, List<TicketPanel> billets, List<Parc> parcsChoisis) {
        try (PDDocument document = new PDDocument()) {
            for (TicketPanel billet : billets) {
                PDPage page = new PDPage(PDRectangle.A6);
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(80, 400);
                contentStream.showText(" Ticket d'Entrée");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(30, 350);
                contentStream.showText("Nom : " + billet.getNom());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Profil : " + billet.getProfil());

                for (Parc parc : parcsChoisis) {
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Parc : " + parc.getNom());
                }

                contentStream.endText();

                BufferedImage barcode = generateBarcodeImage(billet.getNom() + "-" + System.currentTimeMillis());
                if (barcode != null) {
                    PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, imageToByteArray(barcode), "barcode");
                    contentStream.drawImage(pdImage, 100, 150, 150, 50);
                }

                contentStream.close();
            }

            String fileName = "billets_client.pdf";
            document.save(fileName);
            Desktop.getDesktop().open(new File(fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double calculerPrixBillet(TicketPanel billet, List<Parc> parcsChoisis) {
        double prixTotalParcs = 0;
        for (Parc parc : parcsChoisis) {
            prixTotalParcs += parc.getPrixEntree();
        }

        int nombreParcs = parcsChoisis.size();
        double reductionMultiParcs = 0;
        if (nombreParcs == 2) reductionMultiParcs = 0.10;
        if (nombreParcs == 3) reductionMultiParcs = 0.30;
        if (nombreParcs == 4) reductionMultiParcs = 0.40;

        double prixApresMulti = prixTotalParcs * (1 - reductionMultiParcs);

        double reductionProfil = 0;
        if (billet.getProfil().equalsIgnoreCase("ENFANT")) {
            reductionProfil = 0.5;
        } else if (billet.getProfil().equalsIgnoreCase("SENIOR")) {
            reductionProfil = 0.3;
        }

        double prixApresProfil = prixApresMulti * (1 - reductionProfil);

        boolean fastPass = billet.isFastPassSelected();
        double prixApresFastPass = prixApresProfil;
        if (fastPass) {
            prixApresFastPass += prixApresProfil * 0.6;
        }

        double prixAvantFidelite = prixApresFastPass;

        if (billet.getClient() != null) {
            ReservationDAO reservationDAO = new ReservationDAO();
            int nbCommandes = reservationDAO.getNombreCommandesByClientId(billet.getClient().getId()) + 1;

            if (nbCommandes >= 1 && nbCommandes < 5) prixAvantFidelite *= 0.95;
            else if (nbCommandes >= 5 && nbCommandes < 10) prixAvantFidelite *= 0.90;
            else if (nbCommandes >= 10) prixAvantFidelite *= 0.85;

            String abo = billet.getClient().getAbonnement();
            if (abo != null) {
                if (abo.equals("VIP")) {
                    prixAvantFidelite *= 0.5;
                } else if (abo.equals("PASS_ANNUEL")) {
                    prixAvantFidelite = 0;
                } else if (abo.equals("SUPER_VITESSE") && billet.isFastPassSelected()) {
                    prixAvantFidelite -= prixApresProfil * 0.6;
                }
            }
        }

        return prixAvantFidelite;
    }

    private static BufferedImage generateBarcodeImage(String text) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, 300, 100);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] imageToByteArray(BufferedImage image) throws IOException {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
