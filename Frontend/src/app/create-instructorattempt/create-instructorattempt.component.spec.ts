import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateInstructorattemptComponent } from './create-instructorattempt.component';

describe('CreateInstructorattemptComponent', () => {
  let component: CreateInstructorattemptComponent;
  let fixture: ComponentFixture<CreateInstructorattemptComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateInstructorattemptComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateInstructorattemptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
