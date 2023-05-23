import {ScoutRank} from "./ScoutRank";
import {ScoutInstructorRank} from "./ScoutInstructorRank";

export class AppUsersDetailsAttempt {
  constructor(
    public email?: String,
    public name?: String,
    public surname?: String,
    public scoutRank?:ScoutRank,
    public scoutInstructorRank?:ScoutInstructorRank
  ){}


}
