import {ORDER_TYPE} from "../enums/ORDER_TYPE";
import {ActionQueueLine} from "./ActionQueueLine";

export class Order{


  constructor(

  public place?:string,
  public quote?:string,//Cytat
  public exceptions?:string,
  public others?:string,
  public order_type ?:ORDER_TYPE,
  public activeQueueLineDTOList?:ActionQueueLine[],
  public customTextScoutRankAndMeritBadge?:string,
  public customTextOrganization?:string,
  public customTextRole?:string
  ) {}




}
