import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageOrgMembersComponent } from './manage-org-members.component';

describe('ManageOrgMembersComponent', () => {
  let component: ManageOrgMembersComponent;
  let fixture: ComponentFixture<ManageOrgMembersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageOrgMembersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageOrgMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
