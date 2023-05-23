package tk.marvelsas.engineeringProject.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.marvelsas.engineeringProject.ENUMS.ATTEMPT_TYPE;
import tk.marvelsas.engineeringProject.model.Attempt;
import tk.marvelsas.engineeringProject.model.DTO.OrderInputDTO;
import tk.marvelsas.engineeringProject.model.Response;
import tk.marvelsas.engineeringProject.model.ScoutOrder;
import tk.marvelsas.engineeringProject.service.OrganizationService;
import tk.marvelsas.engineeringProject.service.ScoutOrderService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;

@RestController
@RequestMapping("api/v1/Order")
@AllArgsConstructor
public class OrdersConttroller {

    private final ScoutOrderService scoutOrderService;


    @PostMapping("/addOrder")
    public ResponseEntity<Response> addOrder(@Valid @NotBlank @RequestParam Integer organizationId, @RequestBody OrderInputDTO order){
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addOrder",scoutOrderService.createOrder(organizationId,order)))
                            .message("Order created correctly")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addOrder", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }


    @GetMapping("/getOrganizationOrders")
    public ResponseEntity<Response>getOrganizationOrders(@Valid @NotBlank @RequestParam Integer organizationId){
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getOrganizationOrders",scoutOrderService.getAllOrganizationOrders(organizationId)))
                            .message("Returned all Organization Orders")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getOrganizationOrders", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }



    }


    @GetMapping("/pdf/generate")
    public void generatePDF(HttpServletResponse response, @Valid @NotBlank @RequestParam Integer orderId) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.scoutOrderService.export(response,orderId);
    }


    @PostMapping("/view/pdf/generate")
    public void viewGeneratePDF(HttpServletResponse response, @Valid @NotBlank @RequestParam Integer organizationId, @RequestBody OrderInputDTO order) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.scoutOrderService.exportView(response,order,organizationId);
    }









}
