import { Component, OnInit } from '@angular/core';
import {Attempt} from "../models/Attempt";
import {ScoutInstructorRank} from "../models/ScoutInstructorRank";
import {AttemptTask} from "../models/AttemptTask";
import {MeritBadge} from "../models/MeritBadge";
import {CustomResponse} from "../models/CustomResponse";
import {AttemptService} from "../services/attempt.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-create-meritbadgeattempt',
  templateUrl: './create-meritbadgeattempt.component.html',
  styleUrls: ['./create-meritbadgeattempt.component.css']
})
export class CreateMeritbadgeattemptComponent implements OnInit {

  public staticOrganization=0;
  public newAttempt:Attempt=new Attempt();
  public staticEmail:string="";
  public availableMeritBadges:MeritBadge[]=[];
  public attemptTasks:AttemptTask[]=[];
  public showErrorValidationModalName = false;
  public showErrorValidationModalEmptyTaskList= false;



  constructor(public attemptService:AttemptService,private router: Router,private  route:ActivatedRoute) {


    this.route.paramMap.subscribe((params:ParamMap)=>{
      this.staticOrganization =parseInt(params.get('organizationId')!);
      this.staticEmail =params.get('emailScout')!;


      attemptService.getAllMeritBadgesAvailable(this.staticEmail).subscribe((customRespoce:CustomResponse)=>{
        this.availableMeritBadges=customRespoce.data.getAllMeritBadgeAvailableForAppUserAttempt;
        //For selector in html
        this.newAttempt.meritBadgeId= this.availableMeritBadges.at(0)
        console.log(this.availableMeritBadges)
      })

    })


  }

  ngOnInit(): void {
  }


  public getAllMeritBadgesAvailableAvailableForAppUserAttempt():MeritBadge[]{
    return this.availableMeritBadges;
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
      console.log(this.attemptTasks)
    }
  }

  public getAttemptTasks():AttemptTask[]{
    return this.attemptTasks;
  }

  public removeAttemptTask(attemptTask?:AttemptTask) {
    if (attemptTask != null) {
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
        console.log("New------")
        console.log(this.newAttempt.meritBadgeId)

        this.newAttempt.attemptTasks = this.attemptTasks;


        this.attemptService.createAttemptMeritBadge(this.staticEmail, this.staticOrganization, this.newAttempt).subscribe(res => {


          if (res.data.addAttemptMeritbadge){
            form.resetForm();

            this.attemptTasks=[];
            this.availableMeritBadges=[];
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
