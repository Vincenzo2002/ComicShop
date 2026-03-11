package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.bean.Product;
import model.dao.ProductDAO;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/*Servlet per aggiornare i dettagli di un prodotto.
  Gestisce l'upload di file e la validazione dei parametri del prodotto.
 */
@WebServlet("/admin/updateproduct")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 50)
public class UpdateProductServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private String savePath;
    private final String IMG_DIR = "IMG_DIR";

    @Override
    //Inizializza il servlet e crea la directory per il salvataggio delle immagini se non esiste.
    public void init() throws ServletException {
        // Ottiene il percorso assoluto della directory per i file caricati
        savePath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + IMG_DIR;
        // Crea la directory se non esiste
        File imgSaveDir = new File(savePath);
        if (!imgSaveDir.exists()) imgSaveDir.mkdirs();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Recupera i parametri dalla request
        String nameProduct = req.getParameter("nameProduct");
        String quantityStr = req.getParameter("quantity");
        String description = req.getParameter("description");
        String category = req.getParameter("category");
        String priceStr = req.getParameter("price");

        // Variabile per accumulare messaggi di errore
        StringBuilder errorMsg = new StringBuilder();
        boolean isValid = true;

        // Validazione del nome del prodotto
        if (nameProduct == null || nameProduct.trim().isEmpty()) {
            isValid = false;
            errorMsg.append("Nome prodotto obbligatorio");
        }

        // Validazione della quantità
        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity < 0) {
                isValid = false;
                errorMsg.append("La quantita non puo essere negativa");
            }
        } catch (NumberFormatException e) {
            isValid = false;
            errorMsg.append("La quantita deve essere un numero");
        }

        // Validazione della descrizione
        if (description == null || description.trim().isEmpty()) {
            isValid = false;
            errorMsg.append("Descrizione obbligatoria");
        }

        // Validazione della categoria
        if (category == null || category.trim().isEmpty()) {
            isValid = false;
            errorMsg.append("Categoria obbligatoria");
        }

        // Validazione del prezzo
        double price = 0;
        try {
            price = Double.parseDouble(priceStr);
            if (price < 0) {
                isValid = false;
                errorMsg.append("Il prezzo non puo essere negativo");
            }
        } catch (NumberFormatException e) {
            isValid = false;
            errorMsg.append("Il prezzo deve essere un numero");
        }

        // Se la validazione fallisce, reindirizza alla pagina di errore
        if (!isValid) {
            req.setAttribute("errorMsg", errorMsg.toString());
            req.getRequestDispatcher("/pages/error/errorPage.jsp").forward(req, resp);
            return;
        }

        // Prosegue con l'aggiornamento del prodotto se la validazione ha successo
        ArrayList<String> fileNames = uploadFiles(req);
        Product product = new Product();
        product.setName(nameProduct);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product.setUrlImage(fileNames.size() == 0 ? null : fileNames.get(0));

        // Se è presente un parametro "id", imposta l'ID del prodotto
        if(req.getParameter("id") != null){
            product.setIdProduct(Integer.parseInt(req.getParameter("id")));
        }

        try {
            // Salva o aggiorna il prodotto nel database
            new ProductDAO().doSave(product);
            // Reindirizza alla pagina dei prodotti
            resp.sendRedirect(getServletContext().getContextPath() + "/admin/products");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Gestisce l'upload dei file caricati nella richiesta HTTP.
    private ArrayList<String> uploadFiles(HttpServletRequest req) {
        ArrayList<String> uploadedFileNames = new ArrayList<>(); // Lista per memorizzare i nomi dei file caricati
        try {
            if (req.getParts().size() < 0 || req.getParts() == null) {
                return uploadedFileNames;
            }
            // Itera su tutte le parti della richiesta
            for (Part p : req.getParts()) {
                // Estrae il nome del file
                String fileName = extractFileName(p);

                // Verifica se il nome del file è valido (non vuoto e non null)
                if (fileName.trim() != "" && fileName != null) {
                    // Salva il file nella directory di destinazione
                    p.write(savePath + File.separator + fileName);

                    // Aggiunge il percorso relativo del file caricato alla lista
                    uploadedFileNames.add("/uploads/" + IMG_DIR + "/" + fileName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return uploadedFileNames; // Restituisce la lista dei file caricati
    }

    private String extractFileName(Part part) {
        // Ottiene l'intestazione "content-disposition" della parte di upload
        String contentDisp = part.getHeader("content-disposition");

        // Divide l'intestazione in base ai punti e virgola
        String[] items = contentDisp.split(";");

        // Itera su ciascun elemento dell'intestazione
        for (String s : items) {
            // Verifica se l'elemento inizia con "filename" e non è un campo vuoto
            if (s.trim().startsWith("filename") && !s.trim().equals("filename=\"\"")) {
                // Estrae il nome del file dall'intestazione, rimuovendo gli spazi e i caratteri di escape
                // Aggiunge un timestamp al nome del file per evitare conflitti di nomi
                return new Date().getTime() + "_" + s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        // Restituisce una stringa vuota se non è stato trovato un nome di file valido
        return "";
    }
}

