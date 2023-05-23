import {Role} from "./Role";
import {MeritBadge} from "./MeritBadge";
import {ScoutRank} from "./ScoutRank";
import {ScoutInstructorRank} from "./ScoutInstructorRank";

export class AppUserProfileDetails {
  constructor(
    public email?: String,
    public name?: String,
    public surname?: String,
    public birthday?: Date,
    public nickName?:String,
    public roles?:Role[],
    public meritBadges?: MeritBadge,
    public scoutRank?:ScoutRank,
    public scoutInstructorRank?:ScoutInstructorRank,
    public photo?:String
    ){}

}
