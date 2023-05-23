import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageSuborgComponent } from './manage-suborg.component';

describe('ManageSuborgComponent', () => {
  let component: ManageSuborgComponent;
  let fixture: ComponentFixture<ManageSuborgComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageSuborgComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageSuborgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
