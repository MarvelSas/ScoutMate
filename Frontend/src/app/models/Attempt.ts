import {ORDER_TYPE} from "../enums/ORDER_TYPE";
import {ActionQueueLine} from "./ActionQueueLine";
import {AttemptTask} from "./AttemptTask";
import {ScoutRank} from "./ScoutRank";
import {ScoutInstructorRank} from "./ScoutInstructorRank";
import {MeritBadge} from "./MeritBadge";
import {AppUserDetails} from "./AppUserDetails";
import {Organization} from "./Organization";
import {ATTEMPT_STATUS} from "../enums/ATTEMPT_STATUS";
import {ATTEMPT_TYPE} from "../enums/ATTEMPT_TYPE";

export class Attempt{


  constructor(

    public id?:number,
    public name?:string,

    public creatorId?:AppUserDetails,
    public applicantId?:AppUserDetails,
    public organization?:Organization,


    public attemptTasks?:AttemptTask[],
    public scoutRankId?: ScoutRank,
    public scoutInstructorRankId?:ScoutInstructorRank,
    public meritBadgeId?:MeritBadge,
    public TYPE?:ATTEMPT_TYPE,
    public STATUS?:ATTEMPT_STATUS

  ) {}




}
