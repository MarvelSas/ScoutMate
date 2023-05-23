import {ORGANIZATION_TYPE} from "../enums/ORGANIZATION_TYPE";

export class Organization{
  constructor(
    public id?: number,
    public name?: string,
    public organizationType?: ORGANIZATION_TYPE){}
    public image?:string

}
