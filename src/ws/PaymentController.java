package ws;

import dtos.PaymentDTO;
import dtos.ReceiptDTO;
import ejbs.PaymentBean;
import entities.Payment;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/payments")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})


public class PaymentController {

    @EJB
    private PaymentBean paymentBean;

    public static PaymentDTO toDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO(
                payment.getId(),
                payment.getPurchase().getId(),
                payment.getState().getId()
        );
        if(payment.getReceipt() != null){
            paymentDTO.setReceiptId(payment.getReceipt().getId());
        }
        return paymentDTO;

}
    public static List<PaymentDTO> toDTOs(Collection<Payment> payments) {
        return payments.stream().map(PaymentController::toDTO).collect(Collectors.toList());
    }


    @GET
    @Path("/")
    public List<PaymentDTO> all() {
        try {
            return toDTOs(paymentBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_CLASSES", e);
        }
    }

    @GET
    @Path("{id}")
    public Response getAdministratorDetails(@PathParam("id") int id) throws Exception {
        Payment payment = paymentBean.find(id);
        try{
            return Response.status(Response.Status.OK).entity(toDTO(payment)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_PARTNERS", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewPayment (PaymentDTO paymentDTO) throws Exception {
        paymentBean.create(paymentDTO.getStateId(),paymentDTO.getPurchaseId());
        try{
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_Payment", e);
        }
    }

    @GET
    @Path("{id}/receipt/")
    public Response getDocuments(@PathParam("id") int id) throws MyEntityNotFoundException {
        Payment payment = paymentBean.find(id);
        if (payment == null) {
            throw new MyEntityNotFoundException("Receipt with id " + id + " not found.");
        }
        if(payment.getReceipt() == null){
            return Response.status(Response.Status.OK)
                    .entity(null)
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(ReceiptController.toDTO(payment.getReceipt()))
                .build();
        //return ReceiptController.toDTO(payment.getReceipt());
    }

    @GET
    @Path("{id}/hasReceipt/")
    public Response hasDocuments(@PathParam("id")int id)
            throws MyEntityNotFoundException {
        Payment payment = paymentBean.find(id);
        if (payment == null) {
            throw new MyEntityNotFoundException("Receipt with id " + id + " not found.");
        }

        return Response.status(Response.Status.OK)
                .entity(new Boolean(!payment.getReceipt().equals(null)))
                .build();
    }
    @PUT
    @Path("/{id}")
    public Response updatePayment (PaymentDTO paymentDTO) throws Exception {
        Payment payment = paymentBean.update(paymentDTO.getId(),paymentDTO.getStateId());
        try{
            return Response.status(Response.Status.CREATED).entity(toDTO(payment)).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATING_PAyemnt", e);
        }
    }

}


