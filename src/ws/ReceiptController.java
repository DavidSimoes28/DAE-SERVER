package ws;

import dtos.ReceiptDTO;
import ejbs.PaymentBean;
import ejbs.PurchaseBean;
import ejbs.ReceiptBean;
import entities.Payment;
import entities.Purchase;
import entities.Receipt;
import exceptions.MyEntityNotFoundException;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/file")
public class ReceiptController {

    @EJB
    PurchaseBean purchaseBean;
    @EJB
    PaymentBean paymentBean;
    @EJB
    ReceiptBean receiptBean;

    @GET
    @Path("{id}/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFileWithGet(@PathParam("id") Integer receiptId) {
        Receipt receipt = receiptBean.findReceipt(receiptId);
        File fileDownload = new File(receipt.getFilename());

        ResponseBuilder response = Response.ok(receipt.getFilename());
        response.header("Content-Disposition", "attachment;filename=" + fileDownload.getName());
        return response.build();
    }

    @POST
    @Path("{purchaseId}/payment/{paymentId}/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@PathParam("purchaseId") int purchaseId,@PathParam("paymentId") int paymentId, MultipartFormDataInput input) throws Exception {
        Purchase purchase = purchaseBean.find(purchaseId);
        if (purchase == null) {
            throw new MyEntityNotFoundException("Receipt with id " + purchaseId + " not found.");
        }
        Payment payment = paymentBean.find(paymentId);
        if (payment == null) {
            throw new MyEntityNotFoundException("Receipt with id " + paymentId + " not found.");
        }

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

        // Get file data to save
        List<InputPart> inputParts = uploadForm.get("file");

        for (InputPart inputPart : inputParts) {
            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                String fileName = getFileName(header);

                // convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                String path = System.getProperty("user.home") + File.separator + "uploads";
                File customDir = new File(path);

                if (!customDir.exists()) {
                    customDir.mkdir();
                }
                fileName = customDir.getCanonicalPath() + File.separator + fileName;
                writeFile(bytes, fileName);

                receiptBean.create(purchaseId, paymentId, path, fileName);

                return Response.status(200).entity("Uploaded file name : " + fileName).build();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ReceiptDTO toDTO(Receipt receipt) {
        return new ReceiptDTO(
                receipt.getId(),
                receipt.getFilepath(),
                receipt.getFilename());
    }

    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {

            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    // Utility method
    private void writeFile(byte[] content, String filename) throws IOException {
        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
        System.out.println("Written: " + filename);
    }
}
