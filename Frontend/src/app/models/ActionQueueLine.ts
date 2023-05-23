import {ACTION_TYPE} from "../enums/ACTION_TYPE";
import {AppUserDetails} from "./AppUserDetails";
import {Organization} from "./Organization";
import {MeritBadge} from "./MeritBadge";
import {ScoutRank} from "./ScoutRank";
import {ScoutInstructorRank} from "./ScoutInstructorRank";
import {Role} from "./Role";

export class ActionQueueLine {
  constructor(
    public id?: number,
    public action_type?: ACTION_TYPE,
    public archieved?: boolean,
    public appUserDetailsDTO?: AppUserDetails,
    public organization?: Organization,
    public meritBadge?:MeritBadge,
    public scoutRank?:ScoutRank,
    public scoutInstructorRank?:ScoutInstructorRank,
    public role?:Role,
    public subOrganization?:Organization
  ) {}
}
