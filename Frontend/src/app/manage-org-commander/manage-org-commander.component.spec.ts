import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageOrgCommanderComponent } from './manage-org-commander.component';

describe('ManageOrgCommanderComponent', () => {
  let component: ManageOrgCommanderComponent;
  let fixture: ComponentFixture<ManageOrgCommanderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageOrgCommanderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageOrgCommanderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
