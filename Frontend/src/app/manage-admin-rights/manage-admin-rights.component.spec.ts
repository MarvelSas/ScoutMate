import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageAdminRightsComponent } from './manage-admin-rights.component';

describe('ManageAdminRightsComponent', () => {
  let component: ManageAdminRightsComponent;
  let fixture: ComponentFixture<ManageAdminRightsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageAdminRightsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageAdminRightsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
