package DarkDev.services;

import DarkDev.controllers.PanierController;
import DarkDev.models.Produit;
import DarkDev.test.MainFX;
import java.awt.Desktop;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDMMType1Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;


public class GeneratePDF {
    GProduit gp = new GProduit();
    G_ligneCommande glc = new G_ligneCommande();



    public void generateCommandePDF(String path, String currency) throws IOException, SQLException {

        List<Produit> produitList = glc.ListeProduits();
        double sum = produitList.stream().mapToDouble(e->e.getPrix()).sum();
        String somme = "";

        if(currency.equals("DT")){
            somme = sum+10+" DT";
        } else if (currency.equals("Euro €")) {
            somme = (sum+10)*0.3+" Euro €";
        }else if (currency.equals("Dollar $")){
            somme = (sum+10)*0.32+" Dollar $";
        }

        if (!produitList.isEmpty()) {
            ObservableList<Produit> produitObervableListe = FXCollections.observableArrayList(produitList);

            // Define styling constants
            final float MARGIN = 25;
            final float COLUMN1_WIDTH = 150;

            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4); // Use A4 page size
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Header information (replace with your details)
            String companyName = "SwapNShare";
            String headerText = companyName +"- Order Details";

            float headerHeight = 80;

            // Draw header (assuming you have a logo image)

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
            contentStream.newLineAtOffset(MARGIN, page.getMediaBox().getHeight() - MARGIN);
            contentStream.showText(headerText);

            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16); // Use a smaller font for footer
            float footerY = MARGIN; // Adjust Y position for footer content

            // Display custom message on the center (replace "Your Custom Message" with your desired text)
            String footerMessage = "Thank you for your trust !";
            PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD); // Choose your desired font
            float fontSize = 16; // Set the font size

            // Calculate the width of the text
            float messageWidth = fontSize;
            contentStream.newLineAtOffset((page.getMediaBox().getWidth() - messageWidth)/3 , 195);
            contentStream.showText(footerMessage);

            contentStream.endText();


            float currentY = page.getMediaBox().getHeight() - MARGIN - headerHeight - 20; // Start below header

            // Loop through products
            float column2Width = page.getMediaBox().getWidth() - MARGIN - COLUMN1_WIDTH - MARGIN;
            for (Produit item : produitObervableListe) {
                // Image
                PDImageXObject image = PDImageXObject.createFromFile(item.getPhoto(), document);

                // Draw the image at specified position
                contentStream.drawImage(image, 20, currentY-40, 90, 68);

                // Text section
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                contentStream.newLineAtOffset(MARGIN + COLUMN1_WIDTH + MARGIN, currentY);
                contentStream.showText(item.getTitreProduit());

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(0, -12); // Move down for price
                if(currency.equals("DT")){
                    contentStream.showText(item.getPrix() + " "+currency);
                } else if (currency.equals("Euro €")) {
                    contentStream.showText(item.getPrix()*0.3 + " " + currency);
                }else if (currency.equals("Dollar $")){
                    contentStream.showText(item.getPrix()*0.32 + " " +currency);
                }


                contentStream.endText();

                // Update current position for next product
                currentY -= 80; // Adjust row height based on your content



            }
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
            float fontSize1 = 16; // Set the font size

            // Calculate the width of the text
            float messageWidth1 = fontSize1;
            String sumMessage = "Your Total : " + somme;
            contentStream.newLineAtOffset((page.getMediaBox().getWidth() - messageWidth1)/2 , 310);
            contentStream.showText(sumMessage);
            contentStream.endText();

            contentStream.close();

            document.save(path);
            document.close();


        }
    }

}