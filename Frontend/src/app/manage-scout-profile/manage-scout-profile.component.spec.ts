import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageScoutProfileComponent } from './manage-scout-profile.component';

describe('ManageScoutProfileComponent', () => {
  let component: ManageScoutProfileComponent;
  let fixture: ComponentFixture<ManageScoutProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageScoutProfileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageScoutProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
