import { Component, OnInit } from '@angular/core';
import {AttemptService} from "../services/attempt.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {ScoutInstructorRank} from "../models/ScoutInstructorRank";
import {CustomResponse} from "../models/CustomResponse";
import {Attempt} from "../models/Attempt";
import {AttemptTask} from "../models/AttemptTask";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-create-instructorattempt',
  templateUrl: './create-instructorattempt.component.html',
  styleUrls: ['./create-instructorattempt.component.css']
})
export class CreateInstructorattemptComponent implements OnInit {

  public staticOrganization=0;
  public newAttempt:Attempt=new Attempt();
  public staticEmail:string="";
  public availableScoutInstructorRank:ScoutInstructorRank[]=[];
  public attemptTasks:AttemptTask[]=[];
  public showErrorValidationModalName = false;
  public showErrorValidationModalEmptyTaskList= false;

  constructor( public attemptService:AttemptService,private router: Router,private  route:ActivatedRoute) {


    this.route.paramMap.subscribe((params:ParamMap)=>{
      this.staticOrganization =parseInt(params.get('organizationId')!);
      this.staticEmail =params.get('emailScout')!;



    attemptService.getAllScoutInstructorRankAvailableForAppUserAttempt(this.staticEmail).subscribe((customRespoce:CustomResponse)=>{
      this.availableScoutInstructorRank=customRespoce.data.getAllScoutRankAvailableForAppUserAttempt;
      //For selector in html
      this.newAttempt.scoutInstructorRankId = this.availableScoutInstructorRank.at(0)
    })
  })
  }

  ngOnInit(): void {



  }




  public getAllScoutInstructorRankAvailableForAppUserAttempt():ScoutInstructorRank[]{
    return this.availableScoutInstructorRank;
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

  submitAttempt(form: NgForm) {

    //Check if any task is added to task list
    if (this.attemptTasks.length === 0) {
      this.showErrorValidationModalEmptyTaskList = true;
    } else {

      if (form.valid){

        this.newAttempt.attemptTasks = this.attemptTasks;


        this.attemptService.createAttemptScoutInstructorRank(this.staticEmail, this.staticOrganization, this.newAttempt).subscribe(res => {


          if (res.data.addAttemptScoutInstructorRank){
            form.resetForm();

            this.attemptTasks=[];
            this.availableScoutInstructorRank=[];
            this.newAttempt=new Attempt();
            this.router.navigate(['badgesrankhome']);

        }
        });

      }
      else {
        this.showErrorValidationModalName=true;
      }
    }
  }



  }
