package tk.marvelsas.engineeringProject.service;


import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tk.marvelsas.engineeringProject.ENUMS.ACTION_TYPE;
import tk.marvelsas.engineeringProject.ENUMS.ATTEMPT_STATUS;
import tk.marvelsas.engineeringProject.ENUMS.ORDER_TYPE;
import tk.marvelsas.engineeringProject.model.*;
import tk.marvelsas.engineeringProject.model.DTO.ActiveQueueLineDTO;
import tk.marvelsas.engineeringProject.model.DTO.OrderInputDTO;
import tk.marvelsas.engineeringProject.model.DTO.OrderListDTO;
import tk.marvelsas.engineeringProject.repository.ActionQueueLineRepository;
import tk.marvelsas.engineeringProject.repository.AppUserRepository;
import tk.marvelsas.engineeringProject.repository.ScoutOrderRepository;
import tk.marvelsas.engineeringProject.repository.OrganizationRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoutOrderService {




 private final AppUserRepository appUserRepository;
 private final ScoutOrderRepository orderRepository;
 private final OrganizationRepository organizationRepository;
 private final ActionQueueLineRepository actionQueueLineRepository;



 public boolean createOrder(Integer organizationId, OrderInputDTO order){


  Organization organization=organizationRepository.findById(organizationId).orElseThrow(()->new IllegalStateException("Tha organization don't exist"));
  AppUser creatorOrder=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException(
          "mainUser with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"
  ));


  Collection<ActionQueueLine> actionQueueLinesTemporary = order.getActiveQueueLineDTOList().stream().map(activeQueueLineDTO -> {
   ActionQueueLine actionQueueLine = actionQueueLineRepository.findById(activeQueueLineDTO.getId()).get();
   actionQueueLine.setArchived(true);
   return actionQueueLine;
  }).collect(Collectors.toList());

  ScoutOrder newOrder=new ScoutOrder(
          null,
          order.getPlace(),
          order.getQuote(),
          LocalDate.now(),
          order.getExceptions(),
          order.getOthers(),
          order.getOrder_type(),
          null,
          creatorOrder,
          organization,
          actionQueueLinesTemporary,
          order.getCustomTextScoutRankAndMeritBadge(),
          order.getCustomTextOrganization(),
          order.getCustomTextRole()
          );


  ScoutOrder scoutOrder=orderRepository.findLast(newOrder.getOrder_type().ordinal(),newOrder.getDate().getYear());
  if (scoutOrder==null){
   newOrder.setNumber(1);
  }else {
   newOrder.setNumber(scoutOrder.getNumber()+1);
  }

  orderRepository.save(newOrder);
  return true;

 }

 public List<OrderListDTO> getAllOrganizationOrders(Integer organizationId){

  Organization organization=organizationRepository.findById(organizationId).orElseThrow(()->new IllegalStateException("Tha organization don't exist"));
  return organization.getOrders().stream().map(order -> {
     return  new OrderListDTO(
             order.getId(),
             order.getOrder_type(),
             order.getNumber(),
             order.getDate(),
             order.getAppUserCreator().getName(),
             order.getAppUserCreator().getSurname()
             );
   }).collect(Collectors.toList());
  }



  public void export(HttpServletResponse response, int orderId) throws IOException {



  ScoutOrder order = orderRepository.findById(orderId).orElseThrow(()->new IllegalStateException("Tha order don't exist"));




   Document document = new Document(PageSize.A4);
   document.setMargins(60F,60F,10F,10F);
   PdfWriter.getInstance(document, response.getOutputStream());
   document.open();



  final String FONT = "resources/Fonts/times.ttf";


   BaseFont urName = BaseFont.createFont("D:\\Project\\Prywatne\\ScoutMate\\ScoutMate\\Backend\\src\\main\\resources\\Fonts\\arial.ttf", BaseFont.IDENTITY_H,BaseFont.EMBEDDED);



   //Logo
   Image jpg = Image.getInstance("D:\\Project\\Prywatne\\ScoutMate\\ScoutMate\\Backend\\src\\main\\resources\\Images\\orderPDFLogo\\orderImage.png");
   jpg.setAlignment(Image.ALIGN_CENTER);
   jpg.scalePercent(80);


   //First line
   Font firstParagraphFontName = new Font(urName, 14);

   Paragraph firstParagraph = new Paragraph("ZWIĄZEK HARCERSTWA RZECZYPOSPOLITEJ ", firstParagraphFontName);
   firstParagraph.setAlignment(Paragraph.ALIGN_CENTER);



   //Separate Line
   Font separateLinParagraphFontName = new Font(urName, 11);
   Paragraph separateLinParagraph = new Paragraph("-----------------------------------------------------------------------", separateLinParagraphFontName);
   separateLinParagraph.setAlignment(Paragraph.ALIGN_CENTER);


   //Organization name
   Font organizationParagraphFontName = new Font(urName, 14);
   Paragraph organizationParagraph = new Paragraph(order.getOrganization().getName()+" "+order.getOrganization().getOrganizationType().name(), organizationParagraphFontName);
   organizationParagraph.setAlignment(Paragraph.ALIGN_CENTER);


   //Place and date
   Font placeAndDateParagraphFontName = new Font(urName, 12);
   Paragraph placeAndDateParagraph = new Paragraph(order.getPlace()+", "+order.getDate(), placeAndDateParagraphFontName );
   placeAndDateParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
   placeAndDateParagraph.setSpacingBefore(40);
   placeAndDateParagraph.setSpacingAfter(40);


   //Type and numer and year
   String orderType="Brak numeru";

   if(order.getOrder_type().equals(ORDER_TYPE.NORMAL)){
    orderType="L";
   }

   if(order.getOrder_type().equals(ORDER_TYPE.SPECIAL)){
    orderType="S";
   }


   Font typeAndNumerAndYearParagraphFontName = new Font(urName, 11);
   Paragraph typeAndNumerAndYearParagraph = new Paragraph("Rozkaz "+
           orderType+" "+
           order.getNumber()+"/"+
           order.getDate().getYear(),
           typeAndNumerAndYearParagraphFontName );
   typeAndNumerAndYearParagraph.setAlignment(Paragraph.ALIGN_CENTER);



   //Quote
   Font quoteFontName = new Font(urName, 12);
   Paragraph quoteParagraph = new Paragraph(order.getQuote(), quoteFontName);
   quoteParagraph.setAlignment(Paragraph.ALIGN_LEFT);
   quoteParagraph.setSpacingBefore(30);
   quoteParagraph.setSpacingAfter(30);


   //Exceptions (Plain text)
   Font exceptionsPlainTextFontName = new Font(urName, 10);
   Paragraph exceptionsPlainTextParagraph = new Paragraph("1 Wyjątki z rozkazów władz zwierzchnich\n", exceptionsPlainTextFontName);
   exceptionsPlainTextParagraph.add(new Chunk(order.getExceptions()==null?"":order.getExceptions()));
   exceptionsPlainTextParagraph .setAlignment(Paragraph.ALIGN_LEFT);


   //ScoutRank and MeritBadge
   Font scoutRankAndMeritBadgeFontName = new Font(urName, 10);
   Paragraph scoutRankAndMeritBadgeParagraph = new Paragraph("2 Stopnie i sprawności\n", scoutRankAndMeritBadgeFontName);


   Integer scoutRankAndMeritBadgeLineCounter = 1;
   for (ActionQueueLine actionQueueLine: order.getActionQueueLines()) {


    //check status attempt close

      if (actionQueueLine.getTYPE().equals(ACTION_TYPE.GRANTING_MERITBADGE_AND_RANKS) && actionQueueLine.getScoutRank() == null && actionQueueLine.getScoutInstructorRank() == null) {
        if (actionQueueLine.getAttempt().getSTATUS().equals(ATTEMPT_STATUS.CLOSE_POSITIVELY)) {
         scoutRankAndMeritBadgeParagraph.add(
                 new Chunk(
                         "2." +
                                 scoutRankAndMeritBadgeLineCounter+
                         " Nadaje sprawność " +
                                 actionQueueLine.getMeritBadge().getName() +
                                 " " +
                                 (actionQueueLine.getAppUser().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAppUser().getScoutInstructorRankId().getShortcut() + ".") +
                                 actionQueueLine.getAppUser().getName() +
                                 " " +
                                 actionQueueLine.getAppUser().getSurname()+
                                 "\n"

                 ));
        }
       scoutRankAndMeritBadgeLineCounter ++;
      }//ScoutInstructorRank Report
     else if (actionQueueLine.getTYPE().equals(ACTION_TYPE.GRANTING_MERITBADGE_AND_RANKS) && actionQueueLine.getMeritBadge() == null && actionQueueLine.getScoutRank() == null) {
       if (actionQueueLine.getAttempt().getSTATUS().equals(ATTEMPT_STATUS.CLOSE_POSITIVELY)) {
        scoutRankAndMeritBadgeParagraph.add(
                new Chunk(
                        "2." +
                                scoutRankAndMeritBadgeLineCounter+
                                " Na wniosek komisji instrukorskiej zamykam z wynikiem pozytywnym próbę i przyznaję stopień " +
                                actionQueueLine.getScoutInstructorRank().getName() +
                                " " +
                                (actionQueueLine.getPreviousScoutInstructorRank() == null ? "dh." : actionQueueLine.getPreviousScoutInstructorRank().getShortcut() + ".") +
                                actionQueueLine.getAppUser().getName() +
                                " " +
                                actionQueueLine.getAppUser().getSurname()+
                                ", opiekun "+
                               (actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId().getShortcut()+ ".") +
                                actionQueueLine.getAttempt().getCreatorId().getName()+
                                " "+
                                actionQueueLine.getAttempt().getCreatorId().getSurname()+
                                "\n"


                ));
       } else if (actionQueueLine.getAttempt().getSTATUS().equals(ATTEMPT_STATUS.CLOSE_NEGATIVELY)){
        scoutRankAndMeritBadgeParagraph.add(
                new Chunk(
                        "2." +
                                scoutRankAndMeritBadgeLineCounter+
                                " Na wniosek komisji instrukorskiej zamykam z wynikiem negatywny, próbę na stopień " +
                                actionQueueLine.getScoutInstructorRank().getName() +
                                " " +
                                (actionQueueLine.getAppUser().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAppUser().getScoutInstructorRankId().getShortcut() + ".") +
                                actionQueueLine.getAppUser().getName() +
                                " " +
                                actionQueueLine.getAppUser().getSurname()+
                                ", opiekun "+
                                (actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId().getShortcut() + ".") +
                                actionQueueLine.getAttempt().getCreatorId().getName()+
                                " "+
                                actionQueueLine.getAttempt().getCreatorId().getSurname()+
                                "\n"


                ));

       }
       scoutRankAndMeritBadgeLineCounter ++;
      }
     //ScoutRank Report
      else if (actionQueueLine.getTYPE().equals(ACTION_TYPE.GRANTING_MERITBADGE_AND_RANKS) && actionQueueLine.getMeritBadge() == null && actionQueueLine.getScoutInstructorRank() == null) {
       if (actionQueueLine.getAttempt().getSTATUS().equals(ATTEMPT_STATUS.CLOSE_POSITIVELY)) {
        scoutRankAndMeritBadgeParagraph.add(
                new Chunk(
                        "2." +
                                scoutRankAndMeritBadgeLineCounter+
                                " Zamykam próbę i przyznaję stopień " +
                                actionQueueLine.getScoutRank().getName() +
                                " " +
                                (actionQueueLine.getAppUser().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAppUser().getScoutInstructorRankId().getShortcut()+ ".") +
                                actionQueueLine.getAppUser().getName() +
                                " " +
                                actionQueueLine.getAppUser().getSurname()+
                                ", opiekun "+
                                (actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId().getShortcut() + ".") +
                                actionQueueLine.getAttempt().getCreatorId().getName()+
                                " "+
                                actionQueueLine.getAttempt().getCreatorId().getSurname()+
                                "\n"


                ));
       } else if (actionQueueLine.getAttempt().getSTATUS().equals(ATTEMPT_STATUS.CLOSE_NEGATIVELY)){
        scoutRankAndMeritBadgeParagraph.add(
                new Chunk(
                        "2." +
                                scoutRankAndMeritBadgeLineCounter+
                                " Zamykam próbę na stopień " +
                                actionQueueLine.getScoutRank().getName() +
                                " z wynikiem negatywanym " +
                                (actionQueueLine.getAppUser().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAppUser().getScoutInstructorRankId().getShortcut() + ".") +
                                actionQueueLine.getAppUser().getName() +
                                " " +
                                actionQueueLine.getAppUser().getSurname()+
                                ", opiekun "+
                                (actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId().getShortcut() + ".") +
                                actionQueueLine.getAttempt().getCreatorId().getName()+
                                " "+
                                actionQueueLine.getAttempt().getCreatorId().getSurname()+
                                "\n"


                ));

       }
       scoutRankAndMeritBadgeLineCounter ++;
      }







   }

   //Add plain text to ScoutRank and MeritBadge
   scoutRankAndMeritBadgeParagraph.add(new Chunk(order.getCustomTextScoutRankAndMeritBadge()==null?"":order.getCustomTextScoutRankAndMeritBadge()));


   scoutRankAndMeritBadgeParagraph.setAlignment(Paragraph.ALIGN_LEFT);



   //Organization management
   Font organizationManagementFontName = new Font(urName, 10);
   Paragraph organizationManagementParagraph = new Paragraph("3 Jednostki\n", organizationManagementFontName);


   Integer organizationManagementLineCounter = 1;
   for (ActionQueueLine actionQueueLine: order.getActionQueueLines()) {


    //check status attempt close

    if (actionQueueLine.getTYPE().equals(ACTION_TYPE.CREATE_ORGANIZATION)) {

     organizationManagementParagraph.add(
              new Chunk(
                      "3." +
                              organizationManagementLineCounter+
                              " Powołuję do istnienia " +
                              actionQueueLine.getSubOrganization().getOrganizationType().name()+
                              " o nazwie \"" +
                              actionQueueLine.getSubOrganization().getName() +
                              "\"\n"
              ));

     organizationManagementLineCounter ++;
    }

   }

   //Add plain text to Organization
   organizationManagementParagraph.add(new Chunk(order.getCustomTextOrganization()==null?"":order.getCustomTextOrganization()));

   organizationManagementParagraph.setAlignment(Paragraph.ALIGN_LEFT);


   //Role
   Font roleFontName = new Font(urName, 10);
   Paragraph roleParagraph = new Paragraph("4 Mianowania i zwolnienia\n", roleFontName);


   Integer roleLineCounter = 1;
   for (ActionQueueLine actionQueueLine: order.getActionQueueLines()) {


    //check status attempt close

    if (actionQueueLine.getTYPE().equals(ACTION_TYPE.GRANTING_FUNCTION)) {

     roleParagraph.add(
             new Chunk(
                     "4." +
                             roleLineCounter+
                             " Mianuję na funkcję " +
                             actionQueueLine.getRoleName()+
                             " "+
                             (actionQueueLine.getAppUser().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAppUser().getScoutInstructorRankId().getShortcut() + ".") +
                             actionQueueLine.getAppUser().getName() +
                             " " +
                             actionQueueLine.getAppUser().getSurname()+
                             "\n"
             ));

     roleLineCounter ++;
    }

    if (actionQueueLine.getTYPE().equals(ACTION_TYPE.REVOKING_FUNCTION)) {

     roleParagraph.add(
             new Chunk(
                     "4." +
                             roleLineCounter+
                             " Zwalniam z funkcji  " +
                             actionQueueLine.getRoleName()+
                             " "+
                             (actionQueueLine.getAppUser().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAppUser().getScoutInstructorRankId().getShortcut() + ".") +
                             actionQueueLine.getAppUser().getName() +
                             " " +
                             actionQueueLine.getAppUser().getSurname()+
                             "\n"

             ));

     roleLineCounter ++;
    }



   }


   //Add plain text to Role
   roleParagraph.add(new Chunk(order.getCustomTextRole()==null?"":order.getCustomTextRole()));

   roleParagraph.setAlignment(Paragraph.ALIGN_LEFT);



   //Others (Plain text)
   Font othersFontName = new Font(urName, 10);
   Paragraph othersParagraph = new Paragraph("5 Inne\n", othersFontName);
   othersParagraph.add(new Chunk(order.getOthers()==null?"":order.getOthers()));
   othersParagraph.setAlignment(Paragraph.ALIGN_LEFT);


   //Czuwaj
   Font czuwajFontName = new Font(urName, 15);
   Paragraph czuwajParagraph = new Paragraph("Czuwaj!", czuwajFontName);
   czuwajParagraph.setAlignment(Paragraph.ALIGN_CENTER);
   czuwajParagraph.setSpacingBefore(20);
   czuwajParagraph.setSpacingAfter(20);

   //User sign

   Role creatorRole = null;
   String shortcatScoutRank="";

   for (Role role: order.getAppUserCreator().getRoles()) {
    if(role.getOrganizationRole().equals(order.getOrganization())){
     creatorRole = role;
    }
   }
   if (order.getAppUserCreator().getScoutRankId()!=null){
    if (order.getAppUserCreator().getScoutRankId().getName().equals("Harcerz Orli")){shortcatScoutRank="H0";}
    else if (order.getAppUserCreator().getScoutRankId().getName().equals("Harcerz Rzeczypospolitej")){shortcatScoutRank="HR";}
   }



   Font signFontName = new Font(urName, 10);
   Paragraph signParagraph = new Paragraph(
           (creatorRole == null ? "" : creatorRole.getName())+
                   " "+
                   order.getOrganization().getName() +
                   "\n "+
                   (order.getAppUserCreator().getScoutInstructorRankId() == null ? "dh." : order.getAppUserCreator().getScoutInstructorRankId().getShortcut() + ".") +
                   order.getAppUserCreator().getName()+
                   " "+
                   order.getAppUserCreator().getSurname()+
                   " "+
                   shortcatScoutRank
           , signFontName );

   signParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
   signParagraph.setSpacingBefore(20);
   signParagraph.setSpacingAfter(20);









   //Adding to document
   document.add(jpg);
   document.add(firstParagraph);
   document.add(separateLinParagraph);
   document.add(organizationParagraph);
   document.add(placeAndDateParagraph);
   document.add(typeAndNumerAndYearParagraph);
   document.add(quoteParagraph);
   document.add(exceptionsPlainTextParagraph);
   document.add(scoutRankAndMeritBadgeParagraph);
   document.add(organizationManagementParagraph);
   document.add(roleParagraph);
   document.add(othersParagraph);
   document.add(czuwajParagraph);
   document.add(signParagraph);


   document.close();
  }







 public void exportView(HttpServletResponse response, OrderInputDTO orderInputDTO, Integer organizationId) throws IOException {






  Organization organization = organizationRepository.findById(organizationId).orElseThrow(()->new IllegalStateException("the organization don't exist"));


  Document document = new Document(PageSize.A4);
  document.setMargins(60F,60F,10F,10F);
  PdfWriter.getInstance(document, response.getOutputStream());
  document.open();



  final String FONT = "resources/Fonts/times.ttf";


  BaseFont urName = BaseFont.createFont("D:\\Project\\Prywatne\\ScoutMate\\ScoutMate\\Backend\\src\\main\\resources\\Fonts\\arial.ttf", BaseFont.IDENTITY_H,BaseFont.EMBEDDED);



  //Logo
  Image jpg = Image.getInstance("D:\\Project\\Prywatne\\ScoutMate\\ScoutMate\\Backend\\src\\main\\resources\\Images\\orderPDFLogo\\orderImage.png");
  jpg.setAlignment(Image.ALIGN_CENTER);
  jpg.scalePercent(80);


  //First line
  Font firstParagraphFontName = new Font(urName, 14);

  Paragraph firstParagraph = new Paragraph("ZWIĄZEK HARCERSTWA RZECZYPOSPOLITEJ ", firstParagraphFontName);
  firstParagraph.setAlignment(Paragraph.ALIGN_CENTER);



  //Separate Line
  Font separateLinParagraphFontName = new Font(urName, 11);
  Paragraph separateLinParagraph = new Paragraph("-----------------------------------------------------------------------", separateLinParagraphFontName);
  separateLinParagraph.setAlignment(Paragraph.ALIGN_CENTER);


  //Organization name
  Font organizationParagraphFontName = new Font(urName, 14);
  Paragraph organizationParagraph = new Paragraph(organization.getName()+" "+organization.getOrganizationType().name(), organizationParagraphFontName);
  organizationParagraph.setAlignment(Paragraph.ALIGN_CENTER);


  //Place and date
  Font placeAndDateParagraphFontName = new Font(urName, 12);
  Paragraph placeAndDateParagraph = new Paragraph(orderInputDTO.getPlace()+", "+LocalDate.now(), placeAndDateParagraphFontName );
  placeAndDateParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
  placeAndDateParagraph.setSpacingBefore(40);
  placeAndDateParagraph.setSpacingAfter(40);


  //Type and numer and year
  String orderType="Brak numeru";

  if(orderInputDTO.getOrder_type().equals(ORDER_TYPE.NORMAL)){
   orderType="L";
  }

  if(orderInputDTO.getOrder_type().equals(ORDER_TYPE.SPECIAL)){
   orderType="S";
  }



  //Order Number

  Integer orderNumber = 0;

  ScoutOrder scoutOrder=orderRepository.findLast(orderInputDTO.getOrder_type().ordinal(),LocalDate.now().getYear());
  if (scoutOrder==null){
   orderNumber=1;
  }else {
   orderNumber=scoutOrder.getNumber()+1;
  }


  Font typeAndNumerAndYearParagraphFontName = new Font(urName, 11);
  Paragraph typeAndNumerAndYearParagraph = new Paragraph("Rozkaz "+
          orderType+" "+
          orderNumber+"/"+
          LocalDate.now().getYear(),
          typeAndNumerAndYearParagraphFontName );
  typeAndNumerAndYearParagraph.setAlignment(Paragraph.ALIGN_CENTER);



  //Quote
  Font quoteFontName = new Font(urName, 12);
  Paragraph quoteParagraph = new Paragraph(orderInputDTO.getQuote(), quoteFontName);
  quoteParagraph.setAlignment(Paragraph.ALIGN_LEFT);
  quoteParagraph.setSpacingBefore(30);
  quoteParagraph.setSpacingAfter(30);


  //Exceptions (Plain text)
  Font exceptionsPlainTextFontName = new Font(urName, 10);
  Paragraph exceptionsPlainTextParagraph = new Paragraph("1 Wyjątki z rozkazów władz zwierzchnich\n", exceptionsPlainTextFontName);
  exceptionsPlainTextParagraph.add(new Chunk(orderInputDTO.getExceptions()==null?"":orderInputDTO.getExceptions()));
  exceptionsPlainTextParagraph .setAlignment(Paragraph.ALIGN_LEFT);


  //ScoutRank and MeritBadge
  Font scoutRankAndMeritBadgeFontName = new Font(urName, 10);
  Paragraph scoutRankAndMeritBadgeParagraph = new Paragraph("2 Stopnie i sprawności\n", scoutRankAndMeritBadgeFontName);


  Integer scoutRankAndMeritBadgeLineCounter = 1;
  for (ActiveQueueLineDTO activeQueueLineDTO: orderInputDTO.getActiveQueueLineDTOList()) {

   ActionQueueLine actionQueueLine = actionQueueLineRepository.findById(activeQueueLineDTO.getId()).orElseThrow(()->new IllegalStateException("the actionLine don't exist"));

   //check status attempt close

   if (actionQueueLine.getTYPE().equals(ACTION_TYPE.GRANTING_MERITBADGE_AND_RANKS) && actionQueueLine.getScoutRank() == null && actionQueueLine.getScoutInstructorRank() == null) {
    if (actionQueueLine.getAttempt().getSTATUS().equals(ATTEMPT_STATUS.CLOSE_POSITIVELY)) {
     scoutRankAndMeritBadgeParagraph.add(
             new Chunk(
                     "2." +
                             scoutRankAndMeritBadgeLineCounter+
                             " Nadaje sprawność " +
                             actionQueueLine.getMeritBadge().getName() +
                             " " +
                             (actionQueueLine.getAppUser().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAppUser().getScoutInstructorRankId().getShortcut() + ".") +
                             actionQueueLine.getAppUser().getName() +
                             " " +
                             actionQueueLine.getAppUser().getSurname()+
                             "\n"

             ));
    }
    scoutRankAndMeritBadgeLineCounter ++;
   }//ScoutInstructorRank Report
   else if (actionQueueLine.getTYPE().equals(ACTION_TYPE.GRANTING_MERITBADGE_AND_RANKS) && actionQueueLine.getMeritBadge() == null && actionQueueLine.getScoutRank() == null) {
    if (actionQueueLine.getAttempt().getSTATUS().equals(ATTEMPT_STATUS.CLOSE_POSITIVELY)) {
     scoutRankAndMeritBadgeParagraph.add(
             new Chunk(
                     "2." +
                             scoutRankAndMeritBadgeLineCounter+
                             " Na wniosek komisji instrukorskiej zamykam z wynikiem pozytywnym próbę i przyznaję stopień " +
                             actionQueueLine.getScoutInstructorRank().getName() +
                             " " +
                             (actionQueueLine.getPreviousScoutInstructorRank() == null ? "dh." : actionQueueLine.getPreviousScoutInstructorRank().getShortcut() + ".") +
                             actionQueueLine.getAppUser().getName() +
                             " " +
                             actionQueueLine.getAppUser().getSurname()+
                             ", opiekun "+
                             (actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId().getShortcut()+ ".") +
                             actionQueueLine.getAttempt().getCreatorId().getName()+
                             " "+
                             actionQueueLine.getAttempt().getCreatorId().getSurname()+
                             "\n"


             ));
    } else if (actionQueueLine.getAttempt().getSTATUS().equals(ATTEMPT_STATUS.CLOSE_NEGATIVELY)){
     scoutRankAndMeritBadgeParagraph.add(
             new Chunk(
                     "2." +
                             scoutRankAndMeritBadgeLineCounter+
                             " Na wniosek komisji instrukorskiej zamykam z wynikiem negatywny, próbę na stopień " +
                             actionQueueLine.getScoutInstructorRank().getName() +
                             " " +
                             (actionQueueLine.getAppUser().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAppUser().getScoutInstructorRankId().getShortcut() + ".") +
                             actionQueueLine.getAppUser().getName() +
                             " " +
                             actionQueueLine.getAppUser().getSurname()+
                             ", opiekun "+
                             (actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId().getShortcut() + ".") +
                             actionQueueLine.getAttempt().getCreatorId().getName()+
                             " "+
                             actionQueueLine.getAttempt().getCreatorId().getSurname()+
                             "\n"


             ));

    }
    scoutRankAndMeritBadgeLineCounter ++;
   }
   //ScoutRank Report
   else if (actionQueueLine.getTYPE().equals(ACTION_TYPE.GRANTING_MERITBADGE_AND_RANKS) && actionQueueLine.getMeritBadge() == null && actionQueueLine.getScoutInstructorRank() == null) {
    if (actionQueueLine.getAttempt().getSTATUS().equals(ATTEMPT_STATUS.CLOSE_POSITIVELY)) {
     scoutRankAndMeritBadgeParagraph.add(
             new Chunk(
                     "2." +
                             scoutRankAndMeritBadgeLineCounter+
                             " Zamykam próbę i przyznaję stopień " +
                             actionQueueLine.getScoutRank().getName() +
                             " " +
                             (actionQueueLine.getAppUser().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAppUser().getScoutInstructorRankId().getShortcut()+ ".") +
                             actionQueueLine.getAppUser().getName() +
                             " " +
                             actionQueueLine.getAppUser().getSurname()+
                             ", opiekun "+
                             (actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId().getShortcut() + ".") +
                             actionQueueLine.getAttempt().getCreatorId().getName()+
                             " "+
                             actionQueueLine.getAttempt().getCreatorId().getSurname()+
                             "\n"


             ));
    } else if (actionQueueLine.getAttempt().getSTATUS().equals(ATTEMPT_STATUS.CLOSE_NEGATIVELY)){
     scoutRankAndMeritBadgeParagraph.add(
             new Chunk(
                     "2." +
                             scoutRankAndMeritBadgeLineCounter+
                             " Zamykam próbę na stopień " +
                             actionQueueLine.getScoutRank().getName() +
                             " z wynikiem negatywanym " +
                             (actionQueueLine.getAppUser().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAppUser().getScoutInstructorRankId().getShortcut() + ".") +
                             actionQueueLine.getAppUser().getName() +
                             " " +
                             actionQueueLine.getAppUser().getSurname()+
                             ", opiekun "+
                             (actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAttempt().getCreatorId().getScoutInstructorRankId().getShortcut() + ".") +
                             actionQueueLine.getAttempt().getCreatorId().getName()+
                             " "+
                             actionQueueLine.getAttempt().getCreatorId().getSurname()+
                             "\n"


             ));

    }
    scoutRankAndMeritBadgeLineCounter ++;
   }







  }

  //Add plain text to ScoutRank and MeritBadge
  scoutRankAndMeritBadgeParagraph.add(new Chunk(orderInputDTO.getCustomTextScoutRankAndMeritBadge()==null?"":orderInputDTO.getCustomTextScoutRankAndMeritBadge()));


  scoutRankAndMeritBadgeParagraph.setAlignment(Paragraph.ALIGN_LEFT);



  //Organization management
  Font organizationManagementFontName = new Font(urName, 10);
  Paragraph organizationManagementParagraph = new Paragraph("3 Jednostki\n", organizationManagementFontName);


  Integer organizationManagementLineCounter = 1;
  for (ActiveQueueLineDTO activeQueueLineDTO: orderInputDTO.getActiveQueueLineDTOList()) {

   ActionQueueLine actionQueueLine = actionQueueLineRepository.findById(activeQueueLineDTO.getId()).orElseThrow(()->new IllegalStateException("the actionLine don't exist"));

   //check status attempt close

   if (actionQueueLine.getTYPE().equals(ACTION_TYPE.CREATE_ORGANIZATION)) {

    organizationManagementParagraph.add(
            new Chunk(
                    "3." +
                            organizationManagementLineCounter+
                            " Powołuję do istnienia " +
                            actionQueueLine.getSubOrganization().getOrganizationType().name()+
                            " o nazwie \"" +
                            actionQueueLine.getSubOrganization().getName() +
                            "\"\n"
            ));

    organizationManagementLineCounter ++;
   }

  }



  //Add plain text to Organization
  organizationManagementParagraph.add(new Chunk(orderInputDTO.getCustomTextOrganization()==null?"":orderInputDTO.getCustomTextOrganization()));

  organizationManagementParagraph.setAlignment(Paragraph.ALIGN_LEFT);


  //Role
  Font roleFontName = new Font(urName, 10);
  Paragraph roleParagraph = new Paragraph("4 Mianowania i zwolnienia\n", roleFontName);


  Integer roleLineCounter = 1;
  for (ActiveQueueLineDTO activeQueueLineDTO: orderInputDTO.getActiveQueueLineDTOList()) {

   ActionQueueLine actionQueueLine = actionQueueLineRepository.findById(activeQueueLineDTO.getId()).orElseThrow(()->new IllegalStateException("the actionLine don't exist"));

   //check status attempt close

   if (actionQueueLine.getTYPE().equals(ACTION_TYPE.GRANTING_FUNCTION)) {

    roleParagraph.add(
            new Chunk(
                    "4." +
                            roleLineCounter+
                            " Mianuję na funkcję " +
                            actionQueueLine.getRoleName()+
                            " "+
                            (actionQueueLine.getAppUser().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAppUser().getScoutInstructorRankId().getShortcut() + ".") +
                            actionQueueLine.getAppUser().getName() +
                            " " +
                            actionQueueLine.getAppUser().getSurname()+
                            "\n"
            ));

    roleLineCounter ++;
   }

   if (actionQueueLine.getTYPE().equals(ACTION_TYPE.REVOKING_FUNCTION)) {

    roleParagraph.add(
            new Chunk(
                    "4." +
                            roleLineCounter+
                            " Zwalniam z funkcji  " +
                            actionQueueLine.getRoleName()+
                            " "+
                            (actionQueueLine.getAppUser().getScoutInstructorRankId() == null ? "dh." : actionQueueLine.getAppUser().getScoutInstructorRankId().getShortcut() + ".") +
                            actionQueueLine.getAppUser().getName() +
                            " " +
                            actionQueueLine.getAppUser().getSurname()+
                            "\n"

            ));

    roleLineCounter ++;
   }



  }

  //Add plain text to Role
  roleParagraph.add(new Chunk(orderInputDTO.getCustomTextRole()==null?"":orderInputDTO.getCustomTextRole()));

  roleParagraph.setAlignment(Paragraph.ALIGN_LEFT);



  //Others (Plain text)
  Font othersFontName = new Font(urName, 10);
  Paragraph othersParagraph = new Paragraph("5 Inne\n", othersFontName);
  othersParagraph.add(new Chunk(orderInputDTO.getOthers()==null?"":orderInputDTO.getOthers()));
  othersParagraph.setAlignment(Paragraph.ALIGN_LEFT);


  //Czuwaj
  Font czuwajFontName = new Font(urName, 15);
  Paragraph czuwajParagraph = new Paragraph("Czuwaj!", czuwajFontName);
  czuwajParagraph.setAlignment(Paragraph.ALIGN_CENTER);
  czuwajParagraph.setSpacingBefore(20);
  czuwajParagraph.setSpacingAfter(20);


  //Get creator

  AppUser creatorOrder=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException(
          "mainUser with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"
  ));

  //User sign

  Role creatorRole = null;
  String shortcatScoutRank="";

  for (Role role: creatorOrder.getRoles()) {
   if(role.getOrganizationRole().equals(organization)){
    creatorRole = role;
   }
  }
  if (creatorOrder.getScoutRankId()!=null){
   if (creatorOrder.getScoutRankId().getName().equals("Harcerz Orli")){shortcatScoutRank="H0";}
   else if (creatorOrder.getScoutRankId().getName().equals("Harcerz Rzeczypospolitej")){shortcatScoutRank="HR";}
  }



  Font signFontName = new Font(urName, 10);
  Paragraph signParagraph = new Paragraph(
          (creatorRole == null ? "" : creatorRole.getName())+
                  " "+
                  organization.getName() +
                  "\n "+
                  (creatorOrder.getScoutInstructorRankId() == null ? "dh." : creatorOrder.getScoutInstructorRankId().getShortcut() + ".") +
                  creatorOrder.getName()+
                  " "+
                  creatorOrder.getSurname()+
                  " "+
                  shortcatScoutRank
          , signFontName );

  signParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
  signParagraph.setSpacingBefore(20);
  signParagraph.setSpacingAfter(20);









  //Adding to document
  document.add(jpg);
  document.add(firstParagraph);
  document.add(separateLinParagraph);
  document.add(organizationParagraph);
  document.add(placeAndDateParagraph);
  document.add(typeAndNumerAndYearParagraph);
  document.add(quoteParagraph);
  document.add(exceptionsPlainTextParagraph);
  document.add(scoutRankAndMeritBadgeParagraph);
  document.add(organizationManagementParagraph);
  document.add(roleParagraph);
  document.add(othersParagraph);
  document.add(czuwajParagraph);
  document.add(signParagraph);


  document.close();
 }





























}
