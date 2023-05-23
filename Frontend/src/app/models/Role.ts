import {Organization} from "./Organization";

export class Role{
  constructor(
    public id:number,
    public name:string,
    public organizationRole:Organization
  ) {}



}
