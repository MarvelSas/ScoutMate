import { Component, OnInit } from '@angular/core';
import {Attempt} from "../models/Attempt";
import {AttemptTask} from "../models/AttemptTask";
import {ScoutRank} from "../models/ScoutRank";
import {AttemptService} from "../services/attempt.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {CustomResponse} from "../models/CustomResponse";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-create-scoutattempt',
  templateUrl: './create-scoutattempt.component.html',
  styleUrls: ['./create-scoutattempt.component.css']
})
export class CreateScoutattemptComponent implements OnInit {

  public staticOrganization=0;
  public newAttempt:Attempt=new Attempt();
  public staticEmail:string="";
  public availableScoutRank:ScoutRank[]=[];
  public attemptTasks:AttemptTask[]=[];
  public showErrorValidationModalName = false;
  public showErrorValidationModalEmptyTaskList= false;


  constructor(public attemptService:AttemptService,private router: Router,private  route:ActivatedRoute) {

    this.route.paramMap.subscribe((params:ParamMap)=>{
      this.staticOrganization =parseInt(params.get('organizationId')!);
      this.staticEmail =params.get('emailScout')!;




      attemptService.getAllScoutRankAvailable(this.staticEmail).subscribe((customRespoce:CustomResponse)=>{

        this.availableScoutRank=customRespoce.data.getAllScoutRankAvailableForAppUserAttempt;

        this.newAttempt.scoutRankId=this.availableScoutRank.at(0);
      })
    })
  }

  ngOnInit(): void {
  }

  public getAllScoutRankAvailable():ScoutRank[]{
    return this.availableScoutRank;
  }


  public openPDF(){
    this.attemptService.getRulesPdf().subscribe((res)=>{
      let blob:Blob=res.body as Blob;
      let url = window.URL.createObjectURL(blob);
      window.open(url);
    })
  }

  public addTask(task:string){
    if(task!==""){
      let newTask:AttemptTask=new AttemptTask(task);
      this.attemptTasks.push(newTask);
    }
  }
  public getAttemptTasks():AttemptTask[]{
    return this.attemptTasks;
  }
  public removeAttemptTask(attemptTask?:AttemptTask){
    if (attemptTask!=null){
      let index = this.attemptTasks.indexOf(attemptTask, 0);
      if (index > -1) {
        this.attemptTasks.splice(index, 1);
      }
    }
  }

  submitAttempt(form: NgForm){
    if (this.attemptTasks.length === 0) {
      this.showErrorValidationModalEmptyTaskList = true;
    } else {

      if (form.valid){
        this.newAttempt.attemptTasks = this.attemptTasks;
        this.attemptService. createAttemptScoutRank(this.staticEmail, this.staticOrganization, this.newAttempt).subscribe(res => {


          if (res.data.addAttemptScoutRank){
            form.resetForm();
            this.attemptTasks=[];
            this.availableScoutRank=[];
            this.newAttempt=new Attempt();
            this.router.navigate(['badgesrankhome']);
          }
          console.log(res)
        });
      }
      else {
        this.showErrorValidationModalName=true;
      }
    }
  }
}
